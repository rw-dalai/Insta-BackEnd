package com.example.insta.security.web;

import com.example.insta.domain.user.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {
  private static final String ROLE_PREFIX = "ROLE_";

  // our domain user
  private final User user;

  public SecurityUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //      return List.of(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));

    return this.user.getRole().stream()
        .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
        .toList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // return user.getAccount().isNonLocked();
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    //    return user.getAccount().isEnabled();
    //   TODO return true for testing only !
    return true;
  }
}
