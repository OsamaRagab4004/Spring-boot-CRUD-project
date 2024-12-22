package com.flowna.app.ScrumBoard.ScrumboardList;


import com.flowna.app.ScrumBoard.board.Scrumboard;
import com.flowna.app.ScrumBoard.board.ScrumboardRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScrumboardListService {
    final
    ScrumboardRepository scrumboardRepository;
    final
    ScrumboardListRepository scrumboardListRepository;

    public ScrumboardListService(ScrumboardRepository scrumboardRepository,
                                 ScrumboardListRepository scrumboardListRepository) {
        this.scrumboardRepository = scrumboardRepository;
        this.scrumboardListRepository = scrumboardListRepository;
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #request.boardId)")
    public ScrumboardListDTO2 create(int userId, ScrumboardListRequest request) {
        Scrumboard board = scrumboardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalStateException("Board not found"));
        ScrumboardList scrumboardList = ScrumboardList.builder()
                .title(request.getTitle())
                .scrumboard(board)
                .build();
        scrumboardListRepository.save(scrumboardList);
        return mapToDTO2(scrumboardList);
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)")
    public Set<ScrumboardListDTO2> getLists(int userId, int boardId) {
        Scrumboard board = scrumboardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("Board not found"));
        return board.getScrumboardLists().stream().map(this::mapToDTO2).collect(Collectors.toSet());
    }

    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)")
    public ScrumboardListDTO2 getListById(int userId, int boardId, int listId) {
        ScrumboardList scrumboardList = scrumboardListRepository.findById(listId)
                .orElseThrow(() -> new IllegalStateException("List not found"));
        return mapToDTO2(scrumboardList);
    }

    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #request.boardId)")
    public ScrumboardListDTO2 update(int userId, ScrumboardListRequest request) {
        ScrumboardList scrumboardList = scrumboardListRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException("List not found"));
        scrumboardList.setTitle(request.getTitle());
        ScrumboardList scrumboardList1 =  scrumboardListRepository.save(scrumboardList);
        return  mapToDTO2(scrumboardList1);
    }

    @Transactional
    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)")
    public void deleteListById(int listId, int userId,int boardId) {
        // Check if the list exists
        ScrumboardList list = scrumboardListRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("ScrumboardList not found"));

        // Delete the list (associated cards are deleted automatically)
        scrumboardListRepository.delete(list);
    }

    private ScrumboardListDTO2 mapToDTO2(ScrumboardList scrumboardList) {
        return new ScrumboardListDTO2(
                scrumboardList.getId(),
                scrumboardList.getScrumboard().getId(),
                scrumboardList.getTitle()
        );

    }






}