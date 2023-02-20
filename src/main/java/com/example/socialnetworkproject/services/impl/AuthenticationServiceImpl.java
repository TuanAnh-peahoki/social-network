package com.example.socialnetworkproject.services.impl;

import com.example.socialnetworkproject.events.AddDocumentEvent;
import com.example.socialnetworkproject.models.entities.DTO.request.LoginRequest;
import com.example.socialnetworkproject.models.entities.DTO.request.SignUpRequest;
import com.example.socialnetworkproject.models.entities.Information;
import com.example.socialnetworkproject.models.entities.Users;
import com.example.socialnetworkproject.repositories.InformationRepository;
import com.example.socialnetworkproject.repositories.UserRepository;
import com.example.socialnetworkproject.services.AuthenticationService;
import com.example.socialnetworkproject.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    private InformationRepository informationRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private Validator validator;

    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuthenticationServiceImpl (UserRepository userRepository,InformationRepository informationRepository, BCryptPasswordEncoder passwordEncoder
                                    , Validator validator, ApplicationEventPublisher eventPublisher){
        this.userRepository = userRepository;
        this.informationRepository = informationRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void register(SignUpRequest request) {
        Users users = new Users();

        Information information = new Information();

        String passwordEncoded = passwordEncoder.encode(request.getPassword());

        request.setPassword(passwordEncoded);

        BeanUtils.copyProperties(request,users);

        BeanUtils.copyProperties(request,information);

        information.setUsers(users);

        userRepository.save(users);

        eventPublisher.publishEvent(new AddDocumentEvent(this,users));
    }

    @Override
    public String login(LoginRequest request){
        return "Hello";
    }
}
