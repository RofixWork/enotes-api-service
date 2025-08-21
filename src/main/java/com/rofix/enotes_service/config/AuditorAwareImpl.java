package com.rofix.enotes_service.config;

import com.rofix.enotes_service.utils.AuthUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl  implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.of(AuthUtils.getLoggedInUser().getId());
    }
}
