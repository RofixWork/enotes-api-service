package com.rofix.enotes_service.security.service;

import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.repository.UserRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> {
            LoggerUtils.createLog(Level.WARN, UserDetailsServiceImpl.class.getName(), "loadUserByUsername", "Invalid User!!!");
            return new UsernameNotFoundException("Invalid User!!!");
        });
        return new UserDetailsImpl(user);
    }
}
