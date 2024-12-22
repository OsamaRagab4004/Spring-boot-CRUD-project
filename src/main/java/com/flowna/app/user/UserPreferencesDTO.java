package com.flowna.app.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Preferences Data Transfer Object")
public class UserPreferencesDTO {
    private String learning_type;
    private String complexity_level;
    private String focus_areas;
    private String explanation_depth;
    private String content_formating;
    private Boolean email_notification;
    private String language_prefence;
    private String study_time;
    private String study_days;


}