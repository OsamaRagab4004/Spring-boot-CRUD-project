package com.flowna.app.Common.Services;


import com.flowna.app.Chat.Chat;
import com.flowna.app.Chat.ChatRepository;
import com.flowna.app.Course.v1.Course;
import com.flowna.app.Course.v1.CourseRepository;
import com.flowna.app.Flashcard.v1.Flashcard;
import com.flowna.app.Flashcard.v1.FlashcardRepository;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroup;
import com.flowna.app.FlashcardsGroup.v1.FlashcardsGroupRespository;
import com.flowna.app.LectureContent.v1.LectureContent;
import com.flowna.app.LectureContent.v1.LectureContentRepository;
import com.flowna.app.Quiz.v1.Question.Question;
import com.flowna.app.Quiz.v1.Question.QuestionRepository;
import com.flowna.app.Quiz.v1.Quiz.Quiz;
import com.flowna.app.Quiz.v1.Quiz.QuizRepository;
import com.flowna.app.Report.v1.Report;
import com.flowna.app.Report.v1.ReportRepository;
import com.flowna.app.ScrumBoard.board.Scrumboard;
import com.flowna.app.ScrumBoard.board.ScrumboardRepository;
import com.flowna.app.deck.v1.Deck;
import com.flowna.app.deck.v1.DeckRepository;
import com.flowna.app.lecture.v1.Lecture;
import com.flowna.app.lecture.v1.LectureRepository;
import com.flowna.app.page.v1.Page;
import com.flowna.app.page.v1.PageRepository;
import com.flowna.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Component("CheckOwner")
public class CheckOwner {
    final
    CourseRepository courseRepository;
    final
    DeckRepository deckRepository;
    final
    UserService userService;
    final
    LectureRepository lectureRepository;
    final
    LectureContentRepository lectureContentRepository;
    final
    PageRepository pageRepository;
    final
    FlashcardsGroupRespository flashcardsGroupRespository;
    final
    FlashcardRepository flashcardRepository;
    final
    QuizRepository quizRepository;
    final QuestionRepository questionRepository;
    final ReportRepository reportRepository;
    final ScrumboardRepository scrumboardRepository;
    final ChatRepository chatRepository;
    private static final Logger logger = Logger.getLogger(CheckOwner.class.getName());


    @Autowired
    public CheckOwner(CourseRepository courseRepository, DeckRepository deckRepository, UserService userService,
                      LectureRepository lectureRepository, LectureContentRepository lectureContentRepository,
                      PageRepository pageRepository, FlashcardsGroupRespository flashcardsGroupRespository,
                      FlashcardRepository flashcardRepository, QuizRepository quizRepository,
                      QuestionRepository questionRepository, ReportRepository reportRepository,
                      ScrumboardRepository scrumboardRepository, ChatRepository chatRepository) {
        this.courseRepository = courseRepository;
        this.deckRepository = deckRepository;
        this.userService = userService;
        this.lectureRepository = lectureRepository;
        this.lectureContentRepository = lectureContentRepository;
        this.pageRepository = pageRepository;
        this.flashcardsGroupRespository = flashcardsGroupRespository;
        this.flashcardRepository = flashcardRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.reportRepository = reportRepository;
        this.scrumboardRepository = scrumboardRepository;
        this.chatRepository = chatRepository;
    }


    // User -> Deck
    public boolean isUserOwnerOfDeck(int userId, int deckId) {
        List<Deck> userDecks = deckRepository.getDecksByUserId(userId);
        Optional<Deck> matchingDeck = userDecks.stream().filter(
                deck -> deck.getId().equals(deckId)
        ).findFirst();
        if(matchingDeck.isEmpty()) return false;
        return true;
    }

    // Deck -> Course
    public boolean isCourseOwnedByDeck(int deckId,int courseId) {
        Optional<Deck> checkDeck = deckRepository.findDeckById(deckId);
        if(checkDeck.isPresent()) {
            Set<Course> deckCourses = checkDeck.get().getCourses();
            Optional<Course> matchingCourse = deckCourses.stream().filter(
                    course ->  course.getId().equals(courseId)
            ).findFirst();
            if(matchingCourse.isEmpty()) return false;
            return true;

        } else {
            throw new IllegalStateException("Deck is not exists");
        }

    }


    // Course -> Lecture
    public boolean isLectureOwnedByCourse(int courseId, int lectureId) {
        Optional<Lecture> lecture = lectureRepository.findById(courseId);
        if(lecture.isPresent() && lecture.get().getCourse().getId().equals(courseId)) {
            return true;
        } else {
            throw new IllegalStateException("Course is not found");
        }
    }
    // Lecture -> Page
    public boolean isPageOwnedByLecture(int lectureId, int pageId) {
        Optional<Page> page = pageRepository.findById(pageId);
        if(page.isPresent() && page.get().getLecture().getId().equals(lectureId)){
            return true;
        } else {
            throw new IllegalStateException("Page is not found or not belong to lecture !");
        }

    }

    // lecture -> quiz
    public boolean isQuizOwnedByLecture(int lectureId, int quizId) {
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if(quiz.isPresent() && quiz.get().getLecture().getId().equals(lectureId)){
            return true;
        } else {
            throw new IllegalStateException("Quiz is not found or not belong to lecture !");
        }

    }

    // Lecture -> FlashcardsGroup
    public  boolean isFlashcardsGroupOwnedByLecture(int lectureId, int flashcardsGroupId) {
        Optional<FlashcardsGroup> flashcardsGroup = flashcardsGroupRespository.findById(flashcardsGroupId);
        if(flashcardsGroup.isPresent()) {
            if(flashcardsGroup.get().getLecture().getId().equals(lectureId)) return true;
        } else {
            throw new IllegalStateException("Flashcards Group is not found");
        }
        return false;
    }

    // FlashcrdsGroup -> Flashcard
    public boolean isFlashcardOwnedByGroup(int groupId, int flashcardId) {
        Optional<Flashcard> flashcard = flashcardRepository.findById(flashcardId);
        if(flashcard.isPresent() && flashcard.get().getFlashcardsGroup().getId().equals(groupId)) return true;
        return false;
    }

    // Lecture -> LectureContent
    public boolean isLectureContentOwnedByLecture(int lectureId, int lectureContentId) {
        Optional<LectureContent> lectureContent = lectureContentRepository.findById(lectureContentId);
        if(lectureContent.isPresent() &&
            lectureContent.get().getLecture().getId().equals(lectureId)) {
            return true;
        }else {
            throw new IllegalStateException("LectureContent is not found");
        }

    }

    public boolean isQuestionOwnedByQuiz(int quizId, int  questionId) {
        Optional<Question> question = questionRepository.findById(questionId);
        if(question.isPresent() && question.get().getQuiz().getId().equals(quizId)){
            return true;
        } else {
            throw new IllegalStateException("Quiz is not found or not belong to lecture !");
        }

    }

    public boolean isReportOwnedByUser(int userId, int reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if(report.isPresent() && report.get().getUser().getId().equals(userId)){
            return true;
        } else {
            throw new IllegalStateException("Report is not found or not belong to user !");
        }

    }



    public boolean isBoardOwnedByUser(int userId, int boardId) {
        Scrumboard scrumboard = scrumboardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        if(scrumboard.getUserSet().stream().noneMatch(user1-> user1.getId()== userId)){
            throw new IllegalArgumentException("Board not found");
        }
        return true;
    }

    public boolean isListOwnedByBoard(int listId, int boardId) {
        Scrumboard scrumboard = scrumboardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        if(scrumboard.getScrumboardLists().stream().noneMatch(list -> list.getId() == listId)){
            throw new IllegalArgumentException("List not found");
        }
        return true;
    }



    public boolean isChatOwnedByUser(int chatId, int userId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            boolean isOwned = chat.get().getUsers().stream().anyMatch(user -> user.getId().intValue() == userId);
            if (isOwned) {
                return true;
            } else {
                throw new IllegalStateException("Chat is not found or not belong to user!");
            }
        } else {
            throw new IllegalStateException("Chat is not found or not belong to user!");
        }
    }


}

    //end of clas
