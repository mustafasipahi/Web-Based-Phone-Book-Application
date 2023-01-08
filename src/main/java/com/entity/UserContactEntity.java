package com.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "user_contact", uniqueConstraints = {@UniqueConstraint(columnNames = {"phone"})})
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserContactEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(mappedBy = "userContact")
    private UserEntity user;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;
}
