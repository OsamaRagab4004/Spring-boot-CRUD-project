package com.flowna.app.Tracking;

import com.flowna.app.Course.v1.Course;
import com.flowna.app.Course.v1.CourseRepository;
import com.flowna.app.Tracking.tag.Tag;
import com.flowna.app.Tracking.tag.TagRepository;
import com.flowna.app.Tracking.tag.TagRequest;
import com.flowna.app.Tracking.tag.TagService;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class TrackingService {
    final
    TrackingRepository trackingRepository;
    final
    TagRepository tagRepository;
    final
    UserRepository userRepository;
    final
    CourseRepository courseRepository;
    final
    LectureRepository lectureRepository;
    final TagService tagService;

    public TrackingService(TrackingRepository trackingRepository, TagRepository tagRepository,
                           UserRepository userRepository, CourseRepository courseRepository,
                           LectureRepository lectureRepository, TagService tagService) {
        this.trackingRepository = trackingRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
        this.tagService = tagService;
    }




    @PreAuthorize( "@CheckOwner.isUserOwnerOfDeck(#userId, #request.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#request.deckId, #request.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#request.lectureId, #request.courseId)")
    public Tracking create(TrackingRequest request, int userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(
                () -> new IllegalArgumentException("Course not found")
        );
        Lecture lecture = lectureRepository.findById(request.getLectureId()).orElseThrow(
                () -> new IllegalArgumentException("Lecture not found")
        );

        Tracking tracking = Tracking.builder()
                .course(course)
                .lecture(lecture)
                .user(user)
                .typ(request.getTyp())
                .build();
        // Handle multiple tag IDs using int[]
        if (request.getTagIds() != null && request.getTagIds().length > 0) {
            for (int tagId : request.getTagIds()) {
                Tag tag = tagRepository.findById(tagId).orElseThrow(
                        () -> new IllegalArgumentException("Tag not found with id: " + tagId)
                );
                tracking.getTags().add(tag);
            }
        }
        trackingRepository.save(tracking);
        return tracking;

    }



    // this function is secured by userId
    public Tracking completeSession(LocalDateTime completedAt, int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Tracking incompleteSession = user.getTrackings().stream()
                .filter(session -> !session.isCompleted())
                .findFirst().orElseThrow(
                        () -> new IllegalArgumentException("No incomplete session found")
                );
        incompleteSession.setCompletedAt(completedAt);
        incompleteSession.setCompleted(true);
        trackingRepository.save(incompleteSession);
        return incompleteSession;
    }

    public Tracking getUncompletedSession(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return user.getTrackings().stream().filter(
                session -> !session.isCompleted()
        ).findFirst().orElseThrow(
                () -> new IllegalArgumentException("No incomplete session found")
        );
    }

    public Set<Tracking> getTrackingSet(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        return user.getTrackings();
    }

}
