package com.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private Date startDate;
    private Date endDate;
    private int page = 0;
    private int limit = 10;
}
