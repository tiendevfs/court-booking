package com.court.booking.service.Impl;

import com.court.booking.Model.DTO.LoginRequest;
import com.court.booking.Model.DTO.RegisterRequest;
import com.court.booking.Model.InvalidToken;
import com.court.booking.Model.Role;
import com.court.booking.Model.User;
import com.court.booking.repository.InvalidTokenRepository;
import com.court.booking.repository.RoleRepository;
import com.court.booking.service.AuthService;
import com.court.booking.service.UserService;
import com.court.booking.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final InvalidTokenRepository invalidTokenRepository;
    @Override
    public boolean login(LoginRequest request, HttpServletResponse response) {
        String username = request.getUsername();
        if(!userService.existsByUsername(username)){
            return false;
        }
        // lấy ra user
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // kiểm tra pasword
        boolean isMatching = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());
        if(!isMatching){
            return false;
        }
        // tạo at và rt và lưu vào cookie
        String at = JwtUtils.generateToken(userDetails, JwtUtils.EXPIRATION);
        String rf = JwtUtils.generateToken(userDetails, JwtUtils.REFRESHABLE);
        JwtUtils.saveCookie("ACCESS_TOKEN", at, 60*20, response); // 20 phút
        JwtUtils.saveCookie("REFRESH_TOKEN", rf, 60*60*24*7, response); // 7 ngày

        return true;
    }

    @Override
    public boolean register(RegisterRequest request) {
        String username = request.getUsername();
        if(userService.existsByUsername(username)){
            return false;
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .fullname(request.getFullname())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByCode("USER")))
                .build();
        userService.saveUser(user);
        return true;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // lấy at và rf từ cookie
        Cookie[] cookies = request.getCookies();
        String rf = "";
        for(Cookie cookie : cookies){
            String name = cookie.getName();
            if(name.equals("REFRESH_TOKEN")){
                rf = cookie.getValue();
            }
            if(name.equals("ACCESS_TOKEN") || name.equals("REFRESH_TOKEN")){
                cookie.setValue(""); // Xóa giá trị cookie
                cookie.setMaxAge(0); // Đặt thời gian sống về 0 để xóa cookie
                cookie.setPath("/"); // Đảm bảo cookie bị xóa trên toàn bộ domain
                response.addCookie(cookie); // Cập nhật trình duyệt với cookie mới
            }
        }

        // chỉ lưu token còn hạn vào blacklist
        try{
            Claims claims = JwtUtils.extractClaims(rf);
            InvalidToken invalidToken = InvalidToken.builder()
                    .id(claims.getId())
                    .expiry(claims.getExpiration())
                    .build();
            invalidTokenRepository.save(invalidToken);
        }catch (Exception e){
            log.warn("Người dùng đang sử dụng token không hợp lệ để logout");
        }
    }
}
