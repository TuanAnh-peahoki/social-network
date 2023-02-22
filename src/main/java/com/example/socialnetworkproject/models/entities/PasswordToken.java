package com.example.socialnetworkproject.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "password_token",indexes = {@Index(columnList = "token",name = "index_token")  })
@NoArgsConstructor
@AllArgsConstructor
public class PasswordToken {
    private static final int EXPIRATION = 24*60;

    @Id
    @Column
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID tokenId;

    @Column
    private String token;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

}
