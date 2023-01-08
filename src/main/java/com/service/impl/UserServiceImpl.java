package com.service.impl;

import com.dto.UserContactDto;
import com.dto.UserDto;
import com.dto.UserSearchDto;
import com.entity.UserContactEntity;
import com.entity.UserEntity;
import com.exception.UserNotFoundException;
import com.repository.UserRepository;
import com.service.UserContactService;
import com.service.UserService;
import com.specification.UserSpecification;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.constant.CacheConstants.USER_DETAIL_CACHE;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserContactService userContactService;

    @Override
    @Transactional
    public Long save(UserDto userDto) {
        final UserContactEntity userContact = saveUserContact(userDto.getPhone());

        final UserEntity user = UserEntity.builder()
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .userContact(userContact)
            .build();

        return userRepository.save(user).getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = USER_DETAIL_CACHE, key = "#userDto.id", allEntries = true)
    public void update(UserDto userDto) {
        updateUserContact(userDto.getId(), userDto.getPhone());

        final UserEntity userEntity = userRepository.findById(userDto.getId())
            .orElseThrow(UserNotFoundException::new);

        if (checkFirstNameOrLastNameChanged(userEntity, userDto)) {
            userRepository.save(userEntity);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = USER_DETAIL_CACHE, key = "#userId", allEntries = true)
    public void delete(Long userId) {
        deleteUserContact(userId);
        userRepository.deleteById(userId);
    }

    @Override
    @Cacheable(value = USER_DETAIL_CACHE, key = "#userId")
    public UserDto detail(Long userId) {
        final UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        return convertToUserDto(userEntity);
    }

    @Override
    public Page<UserDto> search(UserSearchDto dto) {
        final Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        final Pageable pageRequest = PageRequest.of(dto.getPage(), dto.getLimit(), sort);
        final Specification<UserEntity> specification = UserSpecification.findAllBy(dto);

        return userRepository.findAll(specification, pageRequest).map(this::convertToUserDto);
    }

    private UserContactEntity saveUserContact(String phone) {
        final UserContactDto userContactDto = UserContactDto.builder()
            .phone(phone)
            .build();
        return userContactService.save(userContactDto);
    }

    private void updateUserContact(Long userId, String phone) {
        final UserContactDto userContactDto = UserContactDto.builder()
            .phone(phone)
            .build();

        userContactService.update(userId, userContactDto);
    }

    private boolean checkFirstNameOrLastNameChanged(UserEntity userEntity, UserDto userDto) {
        return !userDto.getFirstName().equals(userEntity.getFirstName()) ||
               !userDto.getLastName().equals(userEntity.getLastName());
    }

    private void deleteUserContact(Long userId) {
        userContactService.delete(userId);
    }

    private UserDto convertToUserDto(UserEntity userEntity) {
        return UserDto.builder()
            .id(userEntity.getId())
            .firstName(userEntity.getFirstName())
            .lastName(userEntity.getLastName())
            .phone(userEntity.getUserContact().getPhone())
            .build();
    }
}
