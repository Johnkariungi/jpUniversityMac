package com.jpUniversity.service;

import com.jpUniversity.domain.User;
import com.jpUniversity.domain.UserBilling;
import com.jpUniversity.domain.UserPayment;
import com.jpUniversity.domain.UserShipping;
import com.jpUniversity.domain.security.PasswordResetToken;
import com.jpUniversity.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);/*update billing from user service*/

    void updateUserShipping(UserShipping userShipping, User user);

    void setUserDefaultShipping(Long userShippingId, User user);

    void setUserDefaultPayment(Long userPaymentId, User user);
}
