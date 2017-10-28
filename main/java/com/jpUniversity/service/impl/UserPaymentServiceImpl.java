package com.jpUniversity.service.impl;

import com.jpUniversity.domain.UserPayment;
import com.jpUniversity.repository.UserPaymentRepository;
import com.jpUniversity.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    public UserPayment findById(Long id) {
        return userPaymentRepository.findOne(id);
    }

    public void removeById(Long id) {
        userPaymentRepository.delete(id);
    }
}
