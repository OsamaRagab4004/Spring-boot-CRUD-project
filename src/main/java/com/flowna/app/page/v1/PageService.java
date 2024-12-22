package com.flowna.app.page.v1;


import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Service
public class PageService {
    final
    PageRepository pageRepository;
    final LectureRepository lectureRepository;

    public PageService(PageRepository pageRepository, LectureRepository lectureRepository) {
        this.pageRepository = pageRepository;
        this.lectureRepository = lectureRepository;
    }

    /**
     * Check if the user is owner in LectureService
     * */
    public Set<Page> getPagesByLectureId(int lectureId) {
        Set<Page> pages = pageRepository.getPagesByLectureId(lectureId);
        return pages;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #pageRequest.deckId)" +
                    "&& @CheckOwner.isCourseOwnedByDeck(#pageRequest.deckId,#pageRequest.courseId)" +
                     "&& @CheckOwner.isLectureOwnedByCourse(#pageRequest.courseId, #pageRequest.lectureId)")
    public Page create(PageRequest pageRequest, int userId) {
        Optional<Lecture> lecture =  lectureRepository.findById(pageRequest.getLectureId());
        if(lecture.isEmpty()) throw new IllegalStateException("Lecture is not found!");
        Page page = Page.builder()
                .title(pageRequest.getTitle())
                .type(pageRequest.getType())
                .pageType(pageRequest.getType())
                .jsonText(pageRequest.getJsonText())
                .youtubeUrl(pageRequest.getYoutubeUrl())
                .mindmapText(pageRequest.getMindmapText())
                .lecture(lecture.get())
                .build();
        pageRepository.save(page);
        return page;
    }

    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #pageRequest.deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#pageRequest.deckId,#pageRequest.courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#pageRequest.courseId, #pageRequest.lectureId)" +
            "&& @CheckOwner.isPageOwnedByLecture(#pageRequest.lectureId,#pageRequest.id)")
    public Page update(PageRequest pageRequest, int userId) {
        Optional<Page> exisitedPage = pageRepository.findById(pageRequest.getId());
        if(exisitedPage.isEmpty()) throw new IllegalStateException("Page is not exist");
        Page page = exisitedPage.get();
        if(!pageRequest.getTitle().isEmpty()) page.setTitle(pageRequest.getTitle());
        if(!pageRequest.getJsonText().isEmpty()) page.setJsonText(pageRequest.getJsonText());
        if(!pageRequest.getMindmapText().isEmpty()) page.setMindmapText(pageRequest.getMindmapText());
        pageRepository.save(page);
        return page;
    }


    @PreAuthorize("@CheckOwner.isUserOwnerOfDeck(#userId, #deckId)" +
            "&& @CheckOwner.isCourseOwnedByDeck(#deckId,#courseId)" +
            "&& @CheckOwner.isLectureOwnedByCourse(#courseId, #lectureId)" +
            "&& @CheckOwner.isPageOwnedByLecture(#lectureId,#pageId)")
    public Page get(int deckId, int courseId, int lectureId, int pageId, int userId) {
        Optional<Page> optionalPage = pageRepository.findById(pageId);
        if(optionalPage.isEmpty()) throw new IllegalStateException("Page is not found !");
        Page page = optionalPage.get();
        return page;
    }


    /**
     * This function for internal usage in the server
     * it's used to create page using AI
     * If it's not used, remove the function
     * */
    public Page generatePage(PageRequest pageRequest) {
        Page page = Page.builder()
                .title(pageRequest.getTitle())
                .jsonText(pageRequest.getJsonText())
                .youtubeUrl(pageRequest.getYoutubeUrl())
                .mindmapText(pageRequest.getMindmapText())
                .build();
        pageRepository.save(page);
        return page;
    }

}
