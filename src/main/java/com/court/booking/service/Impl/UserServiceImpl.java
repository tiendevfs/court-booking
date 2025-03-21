package com.court.booking.service.Impl;

import com.court.booking.Model.DTO.UserRequest;
import com.court.booking.Model.Role;
import com.court.booking.Model.User;
import com.court.booking.repository.RoleRepository;
import com.court.booking.repository.UserRepository;
import com.court.booking.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Người dùng không tồn tại"));
    }

    @Override
    public Page<User> findAll(Optional<String> page) {
        int offsetPage = 0;
        try{
            offsetPage = Integer.parseInt(page.get()) -1 ;
        }catch(Exception e){
            log.error("Lỗi sai url chuyển về trang 1 user");
        }

        Pageable pageable = PageRequest.of(offsetPage, 5);
        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Người dùng không tồn tại"));
    }

    @Override
    public boolean create(UserRequest userRequest) {
        if(this.existsByUsername(userRequest.getUsername())){
            return false;
        }
        Role role = roleRepository.findByCode("USER");
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(new BCryptPasswordEncoder().encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .fullname(userRequest.getFullname())
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
        return true;
    }
}
