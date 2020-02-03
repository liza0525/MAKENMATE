package com.cocktail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cocktail.dao.UserDao;
import com.cocktail.exception.CocktailException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private UserDao userDao;
 
    public UserDetails loadUserByUsername(String userPk) {
        return userDao.getUserByEmail(userPk).orElseThrow(CocktailException::new);
    }
}