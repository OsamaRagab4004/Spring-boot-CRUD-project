package com.flowna.app.page.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PageRepository extends JpaRepository<Page, Integer> {
    Set<Page> getPagesByLectureId(int id);
}
