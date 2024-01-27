package com.example.insta.security.web;

import com.example.insta.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// @Service
@RequiredArgsConstructor
public class DaoUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  // username = email
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Optional functional solution
    return userRepository
        .findByEmail(username)
        .map(SecurityUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Optional imperative solution
    //        Optional<User> userOptional = userRepository.findByEmail(username);
    //        if (userOptional.isPresent())
    //            return new SecurityUser(userOptional.get());
    //        else
    //            throw new UsernameNotFoundException("User not found");

    //        Classic Solution
    //        User user = userRepository.findByEmail(username);
    //
    //        if (user != null)
    //            return new SecurityUser(user);
    //        else
    //            throw new UsernameNotFoundException("User not found");

  }
}
