package com.flowna.app.ScrumBoard.ScrumboardLabel;


import com.flowna.app.ScrumBoard.ScrumboardCard.ScrumboardCard;
import com.flowna.app.ScrumBoard.ScrumboardCard.ScrumboardCardRepository;
import com.flowna.app.ScrumBoard.board.Scrumboard;
import com.flowna.app.ScrumBoard.board.ScrumboardRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ScrumboardLabelService {
    final
    ScrumboardLabelRepository scrumboardLabelRepository;
    final
    ScrumboardCardRepository scrumboardCardRepository;
    final
    ScrumboardRepository scrumboardRepository;

    public ScrumboardLabelService(ScrumboardLabelRepository scrumboardLabelRepository, ScrumboardCardRepository scrumboardCardRepository, ScrumboardRepository scrumboardRepository) {
        this.scrumboardLabelRepository = scrumboardLabelRepository;
        this.scrumboardCardRepository = scrumboardCardRepository;
        this.scrumboardRepository = scrumboardRepository;
    }




    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #scrumboardLabelRequest.scrumboardId)")
    public ScrumboardLabel createWithoutCard(int userId, ScrumboardLabelRequest scrumboardLabelRequest) {
        Scrumboard scrumboard = scrumboardRepository.findById(scrumboardLabelRequest.getScrumboardId()).orElseThrow(
                () -> new IllegalArgumentException("Scrumboard not found")
        );

        ScrumboardLabel scrumboardLabel = ScrumboardLabel.builder()
                .name(scrumboardLabelRequest.getName())
                .scrumboard(scrumboard)
                .build();
        return scrumboardLabelRepository.save(scrumboardLabel);
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #scrumboardLabelRequest.scrumboardId)")
    public ScrumboardLabel update(int userId, ScrumboardLabelRequest scrumboardLabelRequest) {
        ScrumboardLabel scrumboardLabel = scrumboardLabelRepository.findById(scrumboardLabelRequest.getId()).orElseThrow(
                () -> new IllegalArgumentException("Scrumboard label not found")
        );
        if(!scrumboardLabelRequest.getName().isEmpty()) scrumboardLabel.setName(scrumboardLabelRequest.getName());
        return scrumboardLabelRepository.save(scrumboardLabel);
    }









}