package com.example.socialnetworkproject.events;

import com.example.socialnetworkproject.models.entities.Users;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class SendEmailEvent extends ApplicationEvent {
    private String email;
    private Users user;
    public SendEmailEvent(Object object, String email, Users user){
        super(object);
        this.email = email;
        this.user = user;
    }
}
