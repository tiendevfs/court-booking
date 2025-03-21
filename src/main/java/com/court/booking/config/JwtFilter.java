package com.court.booking.config;

import com.court.booking.Model.UserDetailsCustom;
import com.court.booking.repository.InvalidTokenRepository;
import com.court.booking.service.Impl.UserDetailsServiceImpl;
import com.court.booking.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    UserDetailsServiceImpl userDetailsService;
    InvalidTokenRepository invalidTokenRepository;
    private final String[] PUBLIC_ENDPOINTS= {
            "/auth/**",
            "/assets/**",
            "/css/**",
            "/js/**",
            "/fonts/**",
            "/favicon.ico"
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{

            String jwt = this.getToken(request, true);
            // loại bỏ các endpoint public ko cần phải kiểm tra
            if(isPublicEndpoint(request.getRequestURI())){
                filterChain.doFilter(request,response);
                return;
            }
            // nếu token bị xóa khỏi cookie tạo token
            if(jwt.isEmpty()){
                throw new ExpiredJwtException(null, null, null);
            }

            // xác thực
            this.authenticate(jwt, request, response, filterChain);
        }catch (ExpiredJwtException e){
            // thực hiện refresh token mới cho user
            String newJwtToken = this.refreshToken(request,response, filterChain);
            if(!newJwtToken.isEmpty()){
                log.info("Tạo mới access token");
            }
            // Nếu rf không hợp lệ -> 401 trả về trang login
            try {
                authenticate(newJwtToken,request,response,filterChain);
            }catch (Exception e2){
                System.out.println(e2.getMessage());
                log.warn("Yêu cầu người dùng đăng nhập lại");
                filterChain.doFilter(request, response);
            }
            
        }catch (UnsupportedJwtException | MalformedJwtException | SignatureException e){
            log.warn("Yêu cầu người dùng đăng nhập lại");
            filterChain.doFilter(request, response);
        }
    }
    private String getToken(HttpServletRequest request, boolean isGetAccessToken){
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("REFRESH_TOKEN".equals(cookie.getName()) && !isGetAccessToken) {
                    return cookie.getValue();
                }else if("ACCESS_TOKEN".equals(cookie.getName()) && isGetAccessToken){
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    private void authenticate(String jwt,HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ExpiredJwtException {

        // giải mã, kiểm tra token
        Claims claims = JwtUtils.extractClaims(jwt);

        // load user từ db
        UserDetailsCustom userDetails =  (UserDetailsCustom) userDetailsService.loadUserByUsername(claims.getSubject());
        userDetails.eraseCredentials();

        // tạo đối tượng authentication và lưu vào context
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute("userProfile", userDetails);

        filterChain.doFilter(request,response);

        // xác thực thành công
        log.info("xác thực thành công");
    }

    private String refreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rf = this.getToken(request, false);
        if(!rf.isEmpty()){
            // kiểm tra rf
            Claims claims = JwtUtils.extractClaims(rf);

            if(invalidTokenRepository.existsById(claims.getId())){
                filterChain.doFilter(request, response);
                return "";
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
            String newJwtToken = JwtUtils.generateToken(userDetails, JwtUtils.EXPIRATION);

            JwtUtils.saveCookie("ACCESS_TOKEN",newJwtToken, 3000, response);
            return newJwtToken;
        }
        return "";
    }

    private boolean isPublicEndpoint(String requestURI) {
        // thực hiện so sánh endpoint public với uri có trong request gửi đi
        return Arrays.stream(PUBLIC_ENDPOINTS)
                .anyMatch(pattern -> requestURI.matches(pattern.replace("**", ".*")));
    }
}
