package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.meeting.Meeting;

public interface MeetingDao extends JpaRepository<Meeting, Integer>{
    Meeting findById(int mid);

    List<Meeting> findAllByAuthor(String author);
}
