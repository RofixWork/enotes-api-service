package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.entity.Role;
import com.rofix.enotes_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final ModelMapper modelMapper;

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
}
