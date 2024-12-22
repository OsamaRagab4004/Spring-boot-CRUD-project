package com.flowna.app.Notification;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String icon;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String time;
    @Column
    private boolean read;

    @PrePersist
    protected void onCreate() {
        this.read = false;
    }




}
