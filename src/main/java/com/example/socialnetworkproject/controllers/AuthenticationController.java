package com.example.socialnetworkproject.controllers;

import com.example.socialnetworkproject.exception.WrongInformationException;
import com.example.socialnetworkproject.models.entities.DTO.request.PasswordResetRequest;
import com.example.socialnetworkproject.models.entities.DTO.request.LoginRequest;
import com.example.socialnetworkproject.models.entities.DTO.request.SignUpRequest;
import com.example.socialnetworkproject.models.entities.DTO.respond.LoginRespond;
import com.example.socialnetworkproject.models.entities.DTO.respond.SuccessMessage;
import com.example.socialnetworkproject.models.entities.document.UserDocument;
import com.example.socialnetworkproject.services.AuthenticationService;
import com.example.socialnetworkproject.solr.SolrUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private SolrUserRepository solrUserRepository;


    @PostMapping(value = "/signup")
    @ResponseBody
    public ResponseEntity<SuccessMessage> signUp(@RequestBody SignUpRequest request){
        Optional<UserDocument> userOptional = solrUserRepository.getUserDocumentByEmail(request.getEmail());

        if(!userOptional.isEmpty()){
            throw new WrongInformationException("The email  " + request.getEmail() +" already exists !!!");
        }

        authenticationService.register(request);
        return  ResponseEntity.ok(new SuccessMessage("User account has been registered!"));
    }

    @PostMapping(value = "/login")
    public LoginRespond login(@Valid @RequestBody LoginRequest request){
        return authenticationService.login(request);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetRequest request){
        authenticationService.resetPassword(request);
        return ResponseEntity.ok().body(new SuccessMessage("Password reset email sent "));
    }

    @PostMapping(value = "/deleteSolr")
    @Transactional
    public ResponseEntity<?> deleteSolr(){
        solrUserRepository.deleteAll();
        return ResponseEntity.ok().body(new SuccessMessage("Solr delete !!!"));
    }
}
