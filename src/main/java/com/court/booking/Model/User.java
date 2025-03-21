package com.court.booking.Model;

import com.court.booking.Enum.Gender;
import com.court.booking.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String fullname;

    private String email;

    private String phone;

    private String address;
    private Date birth;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String avatar;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
