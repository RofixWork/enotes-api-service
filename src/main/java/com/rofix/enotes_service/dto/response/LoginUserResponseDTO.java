package com.rofix.enotes_service.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserResponseDTO {
    private UserDTO user;

    private String token;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDTO
    {
        private String fullName;
        private String email;
        private String mobileNo;
        private Set<String> roles;
    }
}
