--liquibase formatted sql
--changeset mustafa_sipahi:12-01-2023 12:00
create table if not exists phone_book_app.user_contact
(
    id                 bigint auto_increment
    primary key,
    phone              varchar(256) not null,
    last_modified_date date         null,
    created_date       date         null,
    constraint phone
    unique (phone)
);

create table if not exists phone_book_app.user
(
    id                 bigint auto_increment
    primary key,
    first_name         varchar(256) null,
    last_name          varchar(256) null,
    user_contact_id    bigint       null,
    last_modified_date datetime     null,
    created_date       datetime     null,
    constraint fk_user_contact
    foreign key (user_contact_id) references phone_book_app.user_contact (id)
);