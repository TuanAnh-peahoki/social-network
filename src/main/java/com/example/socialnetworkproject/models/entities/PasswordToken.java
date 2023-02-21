package com.example.socialnetworkproject.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "password_token")
@NoArgsConstructor
@AllArgsConstructor
public class PasswordToken {
    private static final int EXPIRATION = 24*60;

    @Id
    @Column
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID tokenId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column
    private String token;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

}
