package com.rofix.enotes_service.service;

import com.rofix.enotes_service.config.AppConstants;
import com.rofix.enotes_service.dto.request.EmailDetailsDTO;
import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import com.rofix.enotes_service.entity.Role;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.repository.RoleRepository;
import com.rofix.enotes_service.repository.UserRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailSendService emailSendService;

    @Override
    public String register(RegisterUserDTO registerUserDTO) {
        boolean checkUserInfo = userRepository.existsByEmailIgnoreCase(registerUserDTO.getEmail()) || userRepository.existsByMobileNoIgnoreCase(registerUserDTO.getMobileNo());
        if(checkUserInfo){
            throw new ConflictException("Registration failed. Please check your details and try again.");
        }

        Set<Role> userRoles = registerUserDTO.getRoles().stream().map(role -> roleRepository.findByNameIgnoreCase(role).orElseThrow(() -> {
            LoggerUtils.createLog(Level.WARN, AuthServiceImpl.class.getName(), "register", "Invalid Role Name");
            return new BadRequestException("Invalid Role!!!");
        })).collect(Collectors.toSet());

        User newUser = User.builder()
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .email(registerUserDTO.getEmail())
                .password(registerUserDTO.getPassword())
                .mobileNo(registerUserDTO.getMobileNo() != null ? registerUserDTO.getMobileNo() :null)
                .roles(userRoles)
                .build();

        User saveUser = userRepository.save(newUser);

        sendVerifyEmail(saveUser);

        return "User has been Registered Successfully...";
    }

    private void sendVerifyEmail(User saveUser) {
        String message = String.format(AppConstants.TEMPLATE_VERIFY_ACCOUNT, saveUser.getFirstName());
        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                .to(saveUser.getEmail())
                .title("Account Creating Confirmation")
                .subject("Account Created Success")
                .message(message)
                .build();

        emailSendService.send(emailDetailsDTO);
    }
}
