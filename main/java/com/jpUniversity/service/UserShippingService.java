package com.jpUniversity.service;

import com.jpUniversity.domain.UserShipping;

public interface UserShippingService {
    UserShipping findById(Long id);
    void removeById(Long id);
}
