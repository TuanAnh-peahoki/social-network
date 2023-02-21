package com.example.socialnetworkproject.services.impl;

import com.example.socialnetworkproject.repositories.UserRepository;
import com.example.socialnetworkproject.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordResetServiceImpl implements PasswordResetService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public void resetPassword(String email) {
//        userRepository.findPasswordByUserId();
    }
}
