package com.jpUniversity.service;

import com.jpUniversity.domain.User;
import com.jpUniversity.domain.security.PasswordResetToken;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);
}
