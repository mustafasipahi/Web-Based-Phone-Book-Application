package com.service.impl;

import com.dto.UserContactDto;
import com.dto.UserDto;
import com.dto.UserSearchDto;
import com.entity.UserContactEntity;
import com.entity.UserEntity;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import com.service.UserContactService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static com.constant.ErrorCodes.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserContactService userContactService;

    @Test
    void shouldSave() {

        final UserDto userDto = UserDto.builder()
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserContactEntity userContact = UserContactEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        final UserEntity user = UserEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        when(userContactService.save(any(UserContactDto.class))).thenReturn(userContact);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        final Long response = userService.save(userDto);

        assertEquals(user.getId(), response);

        ArgumentCaptor<UserContactDto> userContactCaptor = ArgumentCaptor.forClass(UserContactDto.class);
        verify(userContactService).save(userContactCaptor.capture());
        final UserContactDto userContactCaptorValue = userContactCaptor.getValue();

        assertEquals(userDto.getPhone(), userContactCaptorValue.getPhone());

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        final UserEntity userCaptorValue = userCaptor.getValue();

        assertEquals(userDto.getFirstName(), userCaptorValue.getFirstName());
        assertEquals(userDto.getLastName(), userCaptorValue.getLastName());
        assertEquals(userContact.getId(), userCaptorValue.getUserContact().getId());
    }

    @Test
    void shouldNotUpdateWhenUserEntityNotFound() {

        final UserDto userDto = UserDto.builder()
            .id(RandomUtils.nextLong())
            .build();

        final UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.update(userDto)
        );
        assertEquals(USER_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void shouldNotUpdateWhenSameInfo() {

        final UserDto userDto = UserDto.builder()
            .id(RandomUtils.nextLong())
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .id(userDto.getId())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .build();

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userEntity));
        userService.update(userDto);

        ArgumentCaptor<UserContactDto> userContactCaptor = ArgumentCaptor.forClass(UserContactDto.class);
        verify(userContactService).update(eq(userEntity.getId()), userContactCaptor.capture());
        final UserContactDto userContactCaptorValue = userContactCaptor.getValue();

        assertEquals(userDto.getPhone(), userContactCaptorValue.getPhone());

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldUpdate() {

        final UserDto userDto = UserDto.builder()
            .id(RandomUtils.nextLong())
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .id(userDto.getId())
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .build();

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userEntity));
        userService.update(userDto);

        ArgumentCaptor<UserContactDto> userContactCaptor = ArgumentCaptor.forClass(UserContactDto.class);
        verify(userContactService).update(eq(userEntity.getId()), userContactCaptor.capture());
        final UserContactDto userContactCaptorValue = userContactCaptor.getValue();

        assertEquals(userDto.getPhone(), userContactCaptorValue.getPhone());

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        final UserEntity userCaptorValue = userCaptor.getValue();

        assertEquals(userEntity.getId(), userCaptorValue.getId());
        assertEquals(userDto.getFirstName(), userCaptorValue.getFirstName());
        assertEquals(userDto.getLastName(), userCaptorValue.getLastName());
    }

    @Test
    void shouldNotDeleteWhenUserEntityNotFound() {

        final Long userId = RandomUtils.nextLong();

        final UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.delete(userId)
        );
        assertEquals(USER_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void shouldDelete() {

        final UserContactEntity userContact = UserContactEntity.builder()
            .id(RandomUtils.nextLong())
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .id(RandomUtils.nextLong())
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .userContact(userContact)
            .build();

        when(userRepository.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
        userService.delete(userEntity.getId());
        verify(userContactService).delete(userContact.getId());
        verify(userRepository).deleteById(userEntity.getId());
    }

    @Test
    void shouldNotGetDetailWhenUserEntityNotFound() {
        final Long userId = RandomUtils.nextLong();

        final UserNotFoundException exception = assertThrows(
            UserNotFoundException.class,
            () -> userService.detail(userId)
        );
        assertEquals(USER_NOT_FOUND, exception.getCode());
        assertEquals(BAD_REQUEST, exception.getStatus());
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    void shouldGetDetail() {
        final Long userId = RandomUtils.nextLong();

        final UserContactEntity userContactEntity = UserContactEntity.builder()
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .id(userId)
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .userContact(userContactEntity)
            .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        final UserDto userDetail = userService.detail(userId);

        assertEquals(userId, userDetail.getId());
        assertEquals(userEntity.getFirstName(), userDetail.getFirstName());
        assertEquals(userEntity.getLastName(), userDetail.getLastName());
        assertEquals(userContactEntity.getPhone(), userDetail.getPhone());
    }

    @Test
    void shouldSearch() {

        final UserSearchDto searchDto = UserSearchDto.builder()
            .id(RandomUtils.nextLong())
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .page(0)
            .limit(10)
            .build();

        final UserContactEntity userContactEntity = UserContactEntity.builder()
            .phone("0546" + RandomStringUtils.randomNumeric(7))
            .build();

        final UserEntity userEntity = UserEntity.builder()
            .id(RandomUtils.nextLong())
            .firstName(RandomStringUtils.randomAlphanumeric(3))
            .lastName(RandomStringUtils.randomAlphanumeric(3))
            .userContact(userContactEntity)
            .build();

        Pageable pageable = PageRequest.of(0, 20);
        Page<UserEntity> page = new PageImpl<>(Collections.singletonList(userEntity), pageable, 1);

        @SuppressWarnings("unchecked")
        Specification<UserEntity> anySpecification = any(Specification.class);
        Pageable anyPageable = any(Pageable.class);

        when(userRepository.findAll(anySpecification, anyPageable)).thenReturn(page);
        final Page<UserDto> response = userService.search(searchDto);

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());

        final List<UserDto> contentResponse = response.getContent();
        assertEquals(1, contentResponse.size());
        assertEquals(userEntity.getId(), contentResponse.get(0).getId());
        assertEquals(userEntity.getFirstName(), contentResponse.get(0).getFirstName());
        assertEquals(userEntity.getLastName(), contentResponse.get(0).getLastName());
        assertEquals(userContactEntity.getPhone(), contentResponse.get(0).getPhone());
    }
}