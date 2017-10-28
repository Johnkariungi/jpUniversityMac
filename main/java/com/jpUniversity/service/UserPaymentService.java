package com.jpUniversity.service;

import com.jpUniversity.domain.UserPayment;

public interface UserPaymentService {
    UserPayment findById(Long id);
    void removeById(Long id);
}
