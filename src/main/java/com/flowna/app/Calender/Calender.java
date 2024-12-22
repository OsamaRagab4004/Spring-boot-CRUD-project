package com.flowna.app.Calender;


import com.flowna.app.Calender.Label.CalenderLaberl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "calender")
public class Calender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column(name = "\"desc\"")
    private String desc;
    @Column
    private boolean allDay;
    @Column
    private String start;
    @Column(name = "\"end\"")
    private String end;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calender_label_id")
    private CalenderLaberl calenderLaberl;


}
