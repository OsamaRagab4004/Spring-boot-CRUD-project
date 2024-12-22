package com.flowna.app.lecture.v1;


import com.flowna.app.Course.v1.Course;
import com.flowna.app.Course.v1.CourseRepository;
import com.flowna.app.Course.v1.CourseRequest;
import com.flowna.app.Course.v1.CourseService;
import com.flowna.app.deck.v1.DeckRepository;
import com.flowna.app.page.v1.Page;
import com.flowna.app.page.v1.PageService;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import com.flowna.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LectureService {

    LectureRepository lectureRepository;

    UserRepository userRepository;

    CourseRepository courseRepository;

    DeckRepository deckRepository;

    UserService userService;

    CourseService courseService;

    PageService pageService;


    @Autowired
    public LectureService(LectureRepository lectureRepository, UserRepository userRepository, CourseRepository courseRepository, DeckRepository deckRepository,
                          UserService userService, CourseService courseService, PageService pageService) {
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.deckRepository = deckRepository;
        this.userService = userService;
        this.courseService = courseService;
        this.pageService = pageService;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#lectureRequest.deckId) " +
            "&& @CheckOwner.isCourseOwnedByDeck(#lectureRequest.deckId,#lectureRequest.courseId)")
    public Lecture createLecture( LectureRequest lectureRequest, int userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException("User not found"));
    Course lectureCourse = courseRepository.findById(lectureRequest.getCourseId()).orElseThrow(
            ()-> new IllegalStateException("Course is not found !"));
        Lecture lecture = Lecture.builder()
                .name(lectureRequest.getName())
                .course(lectureCourse)
                .build();
        lectureRepository.save(lecture);
        return lecture;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#lectureRequest.deckId) " +
            "&& @CheckOwner.isCourseOwnedByDeck(lectureRequest.deckId,lectureRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#lectureRequest.courseId,#lectureRequest.id)")
    public Lecture updateLecture(LectureRequest lectureRequest, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
            Lecture updatedLecture = lectureRepository.findById(lectureRequest.getId()).orElseThrow(
                    ()-> new IllegalStateException("Lecture is not found !"));
            updatedLecture.setName(lectureRequest.getName());
            lectureRepository.save(updatedLecture);
            return updatedLecture;
}


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId,#deckId) " +
            "&& @CheckOwner.isCourseOwnedByDeck(#deckId,#courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#courseId,#lectureId)")
    public Set<Page> getAllPagesForLecture(int deckId,int courseId,int lectureId, int userId) {
        Set<Page> pages = pageService.getPagesByLectureId(lectureId);
        return pages;
    }






}
