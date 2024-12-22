package com.flowna.app.Report.v1;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {
    private int id;
    private int userId;
    private String contentJson;
    private String badge;
    private LocalDateTime start;
    private LocalDateTime end;
    private float totalHours;

}
