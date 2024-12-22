package com.flowna.app.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_preferences")
@Schema(description = "User entity")
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String learning_type;
    @Column
    private String complexity_level;
    @Column
    private String focus_areas;
    @Column
    private String explanation_depth;
    @Column
    private String content_formating;
    @Column
    private Boolean email_notification;
    @Column
    private String review_types;
    @Column
    private String language_prefence;
    @Column
    private String study_times;
    @Column
    private String study_days;


    @JsonIgnore // Prevents serialization of user in preferences
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;









}
