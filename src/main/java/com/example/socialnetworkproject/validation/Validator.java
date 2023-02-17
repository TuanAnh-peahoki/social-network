package com.example.socialnetworkproject.validation;


import com.example.socialnetworkproject.exception.EmailExistException;
import com.example.socialnetworkproject.models.entities.DTO.SignUpRequest;

import java.util.Optional;

import com.example.socialnetworkproject.models.entities.Users;
import com.example.socialnetworkproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Validator{
    @Autowired
    private UserRepository userRepository;

    public boolean isEmailExist(String email){
        Optional<Users> optional = userRepository.findByEmail(email);

        if(optional.isEmpty()){
            return false;
        }
        return true;
    }
}
