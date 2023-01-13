--liquibase formatted sql

--changeset mustafa_sipahi:12-01-2023_12:00
CREATE TABLE user_contact
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    phone              VARCHAR(64) NOT NULL,
    last_modified_date DATE        NULL,
    created_date       DATE        NOT NULL,
    CONSTRAINT phone UNIQUE (phone)
);

CREATE TABLE user
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name         VARCHAR(256) NOT NULL,
    last_name          VARCHAR(256) NOT NULL,
    user_contact_id    BIGINT       NULL,
    last_modified_date DATETIME     NULL,
    created_date       DATETIME     NOT NULL,
    CONSTRAINT fk_user_contact FOREIGN KEY (user_contact_id) REFERENCES phone_book_app.user_contact (id)
);

--changeset mustafa_sipahi:13-01-2023_12:00
INSERT INTO user(first_name, last_name, user_contact_id, last_modified_date, created_date)
VALUES ('Mustafa', 'Sipahi', NULL, NULL, NOW());