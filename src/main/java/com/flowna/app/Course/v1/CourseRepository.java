package com.flowna.app.Course.v1;

import com.flowna.app.user.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
