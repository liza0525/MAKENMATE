package com.cocktail.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktail.model.user.User;

// import com.cocktail.model.user.User;

public interface UserDao extends JpaRepository<User, String> {
	Optional<User> getUserByEmail(String email);

	// User findUserByEmailAndPassword(String email, String password);

	Optional<User> getUserByUid(int uid);

	// List<User> findByComments(int uid);

	User findByNickname(String nickname);
}
