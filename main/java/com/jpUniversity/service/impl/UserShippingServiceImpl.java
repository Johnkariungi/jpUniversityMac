package com.jpUniversity.service.impl;

import com.jpUniversity.domain.UserShipping;
import com.jpUniversity.repository.UserShippingRepository;
import com.jpUniversity.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    public UserShipping findById(Long id) {
        return userShippingRepository.findOne(id);
    }

    public void removeById(Long id) {
         userShippingRepository.delete(id);
    }

}
