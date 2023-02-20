package com.example.socialnetworkproject.services;

import com.example.socialnetworkproject.models.entities.DTO.request.LoginRequest;
import com.example.socialnetworkproject.models.entities.DTO.request.SignUpRequest;

public interface AuthenticationService {
   void register(SignUpRequest request);

   String login(LoginRequest request);
}
