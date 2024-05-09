package com.example.fitness_tracker.data.entity;

import com.example.fitness_tracker.data.enums.Roles;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name="user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="salt")
    private String salt;

    @Column(name="password")
    private String password;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Roles useRole;

    @Column(name="sex")
    private String sex;

    @Column(name="height")
    private long height;

    @Column(name="weight")
    private long weight;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
