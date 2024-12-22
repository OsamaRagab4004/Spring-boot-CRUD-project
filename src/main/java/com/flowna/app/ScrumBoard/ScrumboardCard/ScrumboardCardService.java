package com.flowna.app.ScrumBoard.ScrumboardCard;

import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardList;
import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScrumboardCardService {
        final
        ScrumboardListRepository scrumboardListRepository;
        final
        ScrumboardCardRepository scrumboardCardRepository;

    public ScrumboardCardService(ScrumboardListRepository scrumboardListRepository,
                                 ScrumboardCardRepository scrumboardCardRepository) {
        this.scrumboardListRepository = scrumboardListRepository;
        this.scrumboardCardRepository = scrumboardCardRepository;
    }


    /**
     * Set label in the card <<<<<<<<
     * */
    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #request.boardId)" +
                    "&& @CheckOwner.isListOwnedByBoard(#request.listId, #request.boardId)")
    public ScrumboardCard create(int userId, ScrumboardCardRequest request) {
        ScrumboardList list = scrumboardListRepository.findById(request.getListId()).orElseThrow(
                () -> new IllegalArgumentException("List not found")
        );
        ScrumboardCard card = ScrumboardCard.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .priority(request.getPriority())
                .scrumboardList(list)
                .build();
        return scrumboardCardRepository.save(card);
    }

    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #request.boardId)"+
            "&& @CheckOwner.isListOwnedByBoard(#request.listId, #request.boardId)")
    public ScrumboardCard update(int userId, ScrumboardCardRequest request) {
        ScrumboardCard card = scrumboardCardRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("Card not found")
        );
        if(!request.getTitle().isEmpty()) card.setTitle(request.getTitle());
        if(!request.getDescription().isEmpty()) card.setDescription(request.getDescription());
        if(request.getDueDate() != 0) card.setDueDate(request.getDueDate());
        if(request.isCompleted()) card.setCompleted(request.isCompleted());
        if(request.getPriority() != 0) card.setPriority(request.getPriority());
        return scrumboardCardRepository.save(card);
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)"+
                    "&& @CheckOwner.isListOwnedByBoard(#listId, #boardId)")
    public Set<ScrumboardCard> getCardsByListId(int userId, int boardId, int listId) {
        ScrumboardList list = scrumboardListRepository.findById(listId).orElseThrow(
                () -> new IllegalArgumentException("List not found")
        );
        return list.getScrumboardCards();
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)"+
            "&& @CheckOwner.isListOwnedByBoard(#listId, #boardId)")
    public ScrumboardCard getCardById(int userId, int boardId, int listId, int cardId) {
        return scrumboardCardRepository.findById(cardId).orElseThrow(
                () -> new IllegalArgumentException("Card not found")
        );
    }






}
