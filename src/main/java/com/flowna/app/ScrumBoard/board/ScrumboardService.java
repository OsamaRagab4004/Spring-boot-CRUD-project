package com.flowna.app.ScrumBoard.board;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardList;
import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardListDTO;
@Service
public class ScrumboardService {
    final
    ScrumboardRepository scrumboardRepository;
    final
    UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public ScrumboardService(ScrumboardRepository scrumboardRepository, UserRepository userRepository) {
        this.scrumboardRepository = scrumboardRepository;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
    }


    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)")
    public ScrumboardDTO getScrumboardById(int userId, int boardId) {
        Scrumboard board = scrumboardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("Board not found"));

        return mapToDTO(board);
    }

    private ScrumboardDTO mapToDTO(Scrumboard board) {
        ScrumboardDTO dto = new ScrumboardDTO();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setDescription(board.getDescription());
        dto.setIcon(board.getIcon());
        dto.setLastActivity(board.getLastActivity());

        // Parse settings JSON to SettingsDTO
        SettingsDTO settingsDTO = parseSettings(board.getSettings());
        dto.setSettings(settingsDTO);

        // Map members to null
        dto.setMembers(null);

        // Map lists
        List<ScrumboardListDTO> listDTOs = board.getScrumboardLists().stream()
                .map(this::mapListToDTO)
                .collect(Collectors.toList());
        dto.setLists(listDTOs);

        return dto;
    }

    private ScrumboardListDTO mapListToDTO(ScrumboardList list) {
        ScrumboardListDTO dto = new ScrumboardListDTO();
        dto.setId(list.getId());

        // Map cards to a list of IDs
        List<String> cardIds = list.getScrumboardCards().stream()
                .map(card -> card.getId().toString())
                .collect(Collectors.toList());
        dto.setCards(cardIds);

        return dto;
    }

    private SettingsDTO parseSettings(String settingsJson) {
        try {
            return objectMapper.readValue(settingsJson, SettingsDTO.class);
        } catch (Exception e) {
            // Handle parsing exception
            e.printStackTrace();
            return new SettingsDTO(); // Return default settings if parsing fails
        }
    }





    public Scrumboard create(int userId, ScrumboardRequest request) {
        String defaultSettings = "{\"subscribed\": true, \"cardCoverImages\": false}";
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Scrumboard scrumboard = Scrumboard.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .lastActivity(request.getLastActivity())
                .settings(defaultSettings)
                .userSet(new HashSet<>())
                .build();
        scrumboard.getUserSet().add(user);
        if (user.getScrumboards() == null) {
            user.setScrumboards(new HashSet<>());
        }
        user.getScrumboards().add(scrumboard);
        scrumboardRepository.save(scrumboard);
        userRepository.save(user);
        return scrumboard;
    }





    //check if board is owned by user
    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #boardId)")
    public Scrumboard getBoardById(int userId, int boardId) {
       Scrumboard scrumboard = scrumboardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        return scrumboard;

    }


    public Set<ScrumboardDTO> getBoardsByUserId(int userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
        Set<Scrumboard> scrumboards = user.getScrumboards();

        // Map each Scrumboard to its DTO
        return scrumboards.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toSet());
    }


    // update title
    @PreAuthorize("@CheckOwner.isBoardOwnedByUser(#userId, #request.id)")
    public Scrumboard update(int userId, ScrumboardRequest request) {

        Scrumboard scrumboard = scrumboardRepository.findById(request.getId()).orElseThrow(
                () -> new IllegalArgumentException("Board not found")
        );
        scrumboard.setTitle(request.getTitle());
        return scrumboardRepository.save(scrumboard);



    }


    /**
     * Still need to add Social functions like adding member or sharing a board
     * */




}
