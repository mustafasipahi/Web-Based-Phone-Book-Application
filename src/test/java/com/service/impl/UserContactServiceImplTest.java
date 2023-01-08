package com.service.impl;

import com.dto.UserContactDto;
import com.entity.UserContactEntity;
import com.exception.UserContactNotFoundException;
import com.repository.UserContactRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.constant.ErrorCodes.USER_CONTACT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class UserContactServiceImplTest {

    @InjectMocks
    private UserContactServiceImpl userContactService;
    @Mock
    private UserContactRepository userContactRepository;

    @Test
    void shouldSave() {

        final UserContactDto userContactDto = UserContactDto.builder()
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserContactEntity userContact = UserContactEntity.builder()
            .id(RandomUtils.nextLong())
            .phone(userContactDto.getPhone())
            .build();

        when(userContactRepository.save(any(UserContactEntity.class))).thenReturn(userContact);
        UserContactEntity response = userContactService.save(userContactDto);

        assertEquals(userContact.getId(), response.getId());

        ArgumentCaptor<UserContactEntity> captor = ArgumentCaptor.forClass(UserContactEntity.class);
        verify(userContactRepository).save(captor.capture());
        final UserContactEntity captorValue = captor.getValue();

        assertEquals(userContactDto.getPhone(), captorValue.getPhone());
    }

    @Test
    void shouldNotUpdateWhenUserContactEntityNotFound() {

        final Long userId = RandomUtils.nextLong();

        final UserContactDto userContactDto = UserContactDto.builder()
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserContactNotFoundException exception = assertThrows(
            UserContactNotFoundException.class,
            () -> userContactService.update(userId, userContactDto)
        );
        assertEquals(USER_CONTACT_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("User Contact Not Found", exception.getMessage());
    }

    @Test
    void shouldUpdate() {

        final Long userId = RandomUtils.nextLong();

        final UserContactDto userContactDto = UserContactDto.builder()
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserContactEntity userContact = UserContactEntity.builder()
            .id(RandomUtils.nextLong())
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        when(userContactRepository.findByUserId(userId)).thenReturn(Optional.of(userContact));
        userContactService.update(userId, userContactDto);

        ArgumentCaptor<UserContactEntity> captor = ArgumentCaptor.forClass(UserContactEntity.class);
        verify(userContactRepository).save(captor.capture());
        final UserContactEntity captorValue = captor.getValue();

        assertEquals(userContact.getId(), captorValue.getId());
        assertEquals(userContactDto.getPhone(), captorValue.getPhone());
    }

    @Test
    void shouldDelete() {
        final Long userContactId = RandomUtils.nextLong();
        userContactService.delete(userContactId);
        verify(userContactRepository).deleteById(userContactId);
    }
}