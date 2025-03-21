package com.court.booking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class JwtUtils {
    public static String key = "1Vnio5sQsDXo1DLdkBbbtCXjzK4eGhpeVxiOX5bBy7c=\n";
    public static long EXPIRATION = 1000 * 60 * 5; // 5 phút
    public static long REFRESHABLE = 1000 * 60 * 60 * 24 * 7; // 7 ngày

    public static String generateToken(UserDetails userDetails, long expiration){
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }
    public static Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public static SecretKey getKey(){
         return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public static void saveCookie(String name, String value, int expiry, HttpServletResponse response){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true); // Không cho phép truy cập từ JavaScript
        cookie.setSecure(true); // Chỉ dùng với HTTPS
        cookie.setPath("/"); // Áp dụng cho toàn bộ trang web
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
}
