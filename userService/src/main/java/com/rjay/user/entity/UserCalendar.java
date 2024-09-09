package com.rjay.user.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Calendar")
@Data
public class UserCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer calendarId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer eventId;

    @Column(nullable = false)
    private Boolean status = false;

}
