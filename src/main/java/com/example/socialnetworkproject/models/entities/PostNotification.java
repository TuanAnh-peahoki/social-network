package com.example.socialnetworkproject.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "post_notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostNotification extends Notifications {

    @OneToOne
    @JoinColumn(name = "notification_id")
    private Notifications notifications;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts posts;
}
