package com.cocktail.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.meeting.UserMeeting;

public interface UserMeetingDao extends JpaRepository<UserMeeting, Long> {
	List<UserMeeting> findAllByUser_uid(int uid);
	UserMeeting findByUser_uidAndMeeting_mid(int uid, int mid);
}
