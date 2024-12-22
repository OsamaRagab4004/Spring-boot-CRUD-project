package com.flowna.app.ScrumBoard.board;

import com.flowna.app.ScrumBoard.ScrumboardList.ScrumboardListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrumboardDTO {

    private Integer id;
    private String title;
    private String description;
    private String icon;
    private String lastActivity;
    private SettingsDTO settings;
    private List<String> members;
    private List<ScrumboardListDTO> lists;
}
