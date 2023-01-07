package com.service.impl;

import com.dto.UserContactDto;
import com.entity.UserContactEntity;
import com.exception.UserContactNotFoundException;
import com.repository.UserContactRepository;
import com.service.UserContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserContactServiceImpl implements UserContactService {

    private UserContactRepository userContactRepository;

    @Override
    public UserContactEntity save(UserContactDto userContactDto) {
        final UserContactEntity toDb = UserContactEntity.builder()
            .phone(userContactDto.getPhone())
            .build();
        return userContactRepository.save(toDb);
    }

    @Override
    public void update(Long userId, UserContactDto userContactDto) {
        final UserContactEntity userContactEntity = userContactRepository.findByUserId(userId)
            .orElseThrow(UserContactNotFoundException::new);

        userContactEntity.setPhone(userContactDto.getPhone());
        userContactRepository.save(userContactEntity);
    }

    @Override
    public void delete(Long userId) {
        userContactRepository.deleteByUserId(userId);
    }
}
