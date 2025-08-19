package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.entity.Role;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.repository.UserRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserResponseDTO getCurrentUser(User currentUser) {
        UserResponseDTO userResponseDTO = modelMapper.map(currentUser, UserResponseDTO.class);

        Set<String> userRoles = currentUser.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        userResponseDTO.setRoles(userRoles);
        userResponseDTO.setIsActive(currentUser.getStatus().getIsActive());
        return userResponseDTO;
    }

    @Override
    public String changePassword(User currentUser, ChangePasswordDTO changePasswordDTO) {
        String userOldPassword = currentUser.getPassword();

        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(), userOldPassword)){
            LoggerUtils.createLog(Level.WARN, UserServiceImpl.class.getName(), "changePassword", "Old Password Doesn't Match");
            throw new BadRequestException("Password Invalid!!! Please try again.");
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(currentUser);

        return "Your password was changed successfully. You can now use your new password to sign in.";
    }
}
