package com.cocktail.dao;

import java.util.List;
import java.util.Optional;

import com.cocktail.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

// import com.cocktail.model.user.User;

public interface UserDao extends JpaRepository<User, String> {
	Optional<User> getUserByEmail(String email);

	// User findUserByEmailAndPassword(String email, String password);

	Optional<User> findByUid(int uid);

	List<User> findByComments(int cmid);

	User findByNickname(String nickname);
}
