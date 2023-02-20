package com.example.socialnetworkproject.controllers;

import com.example.socialnetworkproject.exception.WrongInformationException;
import com.example.socialnetworkproject.models.entities.CustomUserDetails;
import com.example.socialnetworkproject.models.entities.DTO.request.LoginRequest;
import com.example.socialnetworkproject.models.entities.DTO.request.SignUpRequest;
import com.example.socialnetworkproject.models.entities.DTO.respond.LoginRespond;
import com.example.socialnetworkproject.models.entities.DTO.respond.SuccessMessage;
import com.example.socialnetworkproject.models.entities.document.UserDocument;
import com.example.socialnetworkproject.security.JwtTokenProvider;
import com.example.socialnetworkproject.services.AuthenticationService;
import com.example.socialnetworkproject.solr.SolrUserRepository;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BaseHttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;

    @Resource
    private SolrUserRepository solrUserRepository;
    @Autowired
    private JwtTokenProvider  tokenProvider;


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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String hello = authenticationService.login(request);
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginRespond(jwt);
    }
}
