package com.example.socialnetworkproject.controllers;

import com.example.socialnetworkproject.exception.EmailExistException;
import com.example.socialnetworkproject.models.entities.DTO.SignUpRequest;
import com.example.socialnetworkproject.models.entities.DTO.SuccessMessage;
import com.example.socialnetworkproject.services.AuthenticationService;
import com.example.socialnetworkproject.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private Validator validator;

    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<SuccessMessage> signUp(@RequestBody SignUpRequest request){
        if(validator.isEmailExist(request.getEmail())){
            throw new EmailExistException(request.getEmail());
        }
        authenticationService.register(request);
        return  ResponseEntity.ok(new SuccessMessage("User account has been registered!"));
    }
}
