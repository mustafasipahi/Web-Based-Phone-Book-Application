package com.service;

import com.dto.UserContactDto;
import com.entity.UserContactEntity;

public interface UserContactService {

    UserContactEntity save(UserContactDto userContactDto);
    void update(Long userId, UserContactDto userContactDto);
    void delete(Long userId);
}
