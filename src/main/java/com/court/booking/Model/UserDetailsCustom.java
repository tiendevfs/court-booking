package com.court.booking.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsCustom implements UserDetails, CredentialsContainer {
    private String username;
    private String password;
    private String avatar;
    private String fullname;
    private Set<GrantedAuthority> authorities;

    public UserDetails build(User user){
        authorities = user.getRoles().stream()
                .map(it -> new SimpleGrantedAuthority(it.getCode())).collect(Collectors.toSet());

        return UserDetailsCustom.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .authorities(authorities)
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void eraseCredentials() {
        password = null;
    }

}
