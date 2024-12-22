package com.flowna.app.Tracking.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
}
