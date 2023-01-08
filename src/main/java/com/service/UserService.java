package com.service;

import com.dto.UserDto;
import com.dto.UserSearchDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Long save(UserDto userDto);
    void update(UserDto userDto);
    void delete(Long userId);
    UserDto detail(Long userId);
    Page<UserDto> search(UserSearchDto dto);
}
