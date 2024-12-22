package com.flowna.app.Report.v1;


import com.flowna.app.user.User;
import com.flowna.app.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    final
    ReportRepository reportRepository;
    final UserRepository userRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Report create(ReportRequest reportRequest) {
        User user = userRepository.findById(reportRequest.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        Report report = Report.builder()
                .contentJson(reportRequest.getContentJson())
                .badge(reportRequest.getBadge())
                .start(reportRequest.getStart())
                .end(reportRequest.getEnd())
                .totalHours(reportRequest.getTotalHours())
                .user(user)
                .build();
         reportRepository.save(report);
         return report;
    }

    @PreAuthorize("@CheckOwner.isReportOwnedByUser(#userId,#reporRequest.id)")
    // update content of the Report from the user
    public Report update(int userId,ReportRequest reportRequest) {
        Report report = reportRepository.findById(reportRequest.getId()).orElseThrow(
                () -> new RuntimeException("Report not found")
        );

        report.setContentJson(reportRequest.getContentJson());
        reportRepository.save(report);
        return report;
    }



    public List<Report> getReportsByUserId(int userId) {
        return reportRepository.getReportsByUserId(userId);
    }

}
