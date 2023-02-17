package com.example.socialnetworkproject.models.entities.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class UserDocument {

    @Field(value = "id")
    @Id
    private String userID;

    @Field(value = "user_name")
    private String userName;

    @Field(value = "email")
    private String email;

    @Field(value = "first_name")
    private String firstName;

    @Field(value = "last_name")
    private String lastName;

    @Field
    private String school;

    @Field
    private LocalDateTime birthday;

    @Field
    private String hobby;

}
