package com.specification;

import com.dto.UserSearchDto;
import com.entity.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSpecification {

    public static Specification<UserEntity> findAllBy(UserSearchDto dto) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(dto.getId())) {
                predicates.add(builder.equal(root.get("id"), dto.getId()));
            }

            if (Objects.nonNull(dto.getFirstName())) {
                predicates.add(builder.equal(root.get("firstName"), dto.getFirstName()));
            }

            if (Objects.nonNull(dto.getLastName())) {
                predicates.add(builder.equal(root.get("lastName"), dto.getLastName()));
            }

            if (Objects.nonNull(dto.getPhone())) {
                predicates.add(builder.equal(root.get("userContact").get("phone"), dto.getPhone()));
            }

            if (Objects.nonNull(dto.getStartDate())) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), dto.getStartDate()));
            }

            if (Objects.nonNull(dto.getEndDate())) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"), dto.getEndDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
