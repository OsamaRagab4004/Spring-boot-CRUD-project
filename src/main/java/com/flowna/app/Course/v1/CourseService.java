package com.flowna.app.Course.v1;
import com.flowna.app.deck.v1.Deck;
import com.flowna.app.deck.v1.DeckRepository;
import com.flowna.app.deck.v1.DeckRequest;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import com.flowna.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {


    CourseRepository courseRepository;
    DeckRepository deckRepository;
    UserService userService;
    LectureRepository lectureRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, DeckRepository deckRepository, UserService userService, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.deckRepository = deckRepository;
        this.userService = userService;
        this.lectureRepository=lectureRepository;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#courseRequest.deckId)")
    public void createCourse(CourseRequest courseRequest, int userId) {
        Optional<Deck> deckOptional = deckRepository.findDeckById(courseRequest.getDeckId());
            if(deckOptional.isPresent()) {
                Deck deck = deckOptional.get();
                if( !isCourseNameUnique(deck, courseRequest)) throw new IllegalStateException("Course name must be unique!");
                Course course = Course.builder()
                        .name(courseRequest.getName())
                        .build();
                deck.addNewCourse(course);
                courseRepository.save(course);
                deckRepository.save(deck);

        } else {
            throw new IllegalStateException("Deck  not found to add Course !!");
        }
    }



    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#courseRequest.deckId)")
    public void updateCourse(CourseRequest courseRequest, int userId) {
            Optional<Course> existedCourse = courseRepository.findById(courseRequest.getId());
            if (existedCourse.isPresent()) {
                Course updatedCourse = existedCourse.get();
                Optional<Deck> courseDeckOptional = deckRepository.findDeckById(courseRequest.getDeckId());
                if (courseDeckOptional.isEmpty()) throw new IllegalStateException("Deck is not found");
                Deck courseDeck = courseDeckOptional.get();
                if (!isCourseNameUnique(courseDeck, courseRequest))
                    throw new IllegalStateException("Course name must be unique");
                updatedCourse.setName(courseRequest.getName());
                courseRepository.save(updatedCourse);
            } else {
                throw new IllegalStateException("Course is not found");
            }

    }




    // User -> Deck -> Course -> Lectures
    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #deckId)" +
    "&& @CheckOwner.isCourseOwnedByDeck(#deckId,#courseId)")
    public Set<Lecture> getLecturesByCourseId(int userId, int courseId, int deckId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isEmpty()) throw new IllegalStateException("Course not found");
        Set<Lecture> lectures = lectureRepository.getLecturesByCourseId(courseId);
        return lectures;
    }



    /**
     * Refactor this function in common/services/checkOwner
     * */

    public boolean isCourseNameUnique(Deck deck, CourseRequest courseRequest) {
            Set<Course> deckCourses = deck.getCourses();
            Optional<Course> matchingCourse = deckCourses.stream().filter(
                    course -> course.getName().equals(courseRequest.getName())
            ).findFirst();
            if(matchingCourse.isPresent()) return false;

        return true;
    }




}
