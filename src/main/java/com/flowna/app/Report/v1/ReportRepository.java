package com.flowna.app.Report.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {

List<Report> getReportsByUserId(int userId);
}
