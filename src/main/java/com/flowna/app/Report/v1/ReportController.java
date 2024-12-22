package com.flowna.app.Report.v1;


import com.flowna.app.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
        @RequestBody ReportRequest reportRequest,
        @AuthenticationPrincipal User userDetails
    ){
        Report report = reportService.update(userDetails.getId(),reportRequest);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getReportsByUserId(
        @AuthenticationPrincipal User userDetails
    ){
        return ResponseEntity.ok(reportService.getReportsByUserId(userDetails.getId()));
    }



}
