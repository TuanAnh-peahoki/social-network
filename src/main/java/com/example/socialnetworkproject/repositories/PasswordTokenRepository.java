package com.example.socialnetworkproject.repositories;

import com.example.socialnetworkproject.models.entities.PasswordToken;
import com.example.socialnetworkproject.models.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken,UUID> {

}
