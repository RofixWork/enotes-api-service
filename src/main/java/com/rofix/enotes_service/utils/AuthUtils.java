package com.rofix.enotes_service.utils;

import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.security.service.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {
    public static Integer getCurrentUserId()
    {
        return 1;
    }

    public static User getLoggedInUser()
    {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getUser();
    }
}
