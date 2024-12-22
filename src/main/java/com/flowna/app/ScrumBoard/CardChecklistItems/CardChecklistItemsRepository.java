package com.flowna.app.ScrumBoard.CardChecklistItems;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface CardChecklistItemsRepository extends JpaRepository<CardChecklistItems, Integer> {
}
