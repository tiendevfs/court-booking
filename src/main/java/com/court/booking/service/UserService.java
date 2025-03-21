package com.court.booking.service;

import com.court.booking.Model.DTO.UserRequest;
import com.court.booking.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean existsByUsername(String username);

    boolean create(UserRequest userRequest);

    void saveUser(User user);

    User findByUsername(String username);

    Page<User> findAll(Optional<String> page);



    User findById(Long id);


}
