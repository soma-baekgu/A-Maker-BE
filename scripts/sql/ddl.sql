create table chat
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6)                                                    null,
    updated_at   datetime(6)                                                    null,
    chat_room_id bigint                                                         not null,
    chat_type    enum ('FILE', 'GENERAL', 'IMAGE', 'REACTION', 'REPLY', 'TASK') null,
    content      text                                                           not null,
    user_id      varchar(255)                                                   not null
);

create index idx_chat__chat_room_id
    on chat (chat_room_id);

create index idx_chat__user_id
    on chat (user_id);

create table chat_room
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)                null,
    updated_at     datetime(6)                null,
    chat_room_type enum ('CUSTOM', 'DEFAULT') not null,
    last_chat_id   bigint                     null,
    name           varchar(255)               not null,
    workspace_id   bigint                     not null
);

create index idx_chat_room__workspace_id
    on chat_room (workspace_id);



create table chat_room_user
(
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  null,
    updated_at        datetime(6)  null,
    chat_room_id      bigint       not null,
    last_read_chat_id bigint       null,
    user_id           varchar(255) not null
);

create index idx_chat_room_user__chat_room_id
    on chat_room_user (chat_room_id);

create index idx_chat_room_user__user_id_chat_room_id
    on chat_room_user (user_id, chat_room_id);

create table event
(
    event_type              varchar(31)  not null,
    id                      bigint       not null
        primary key,
    created_at              datetime(6)  null,
    updated_at              datetime(6)  null,
    dead_line               datetime(6)  not null,
    event_title             varchar(255) not null,
    notification_interval   int          not null,
    notification_start_time datetime(6)  not null
);

create table event_assigned_user
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    updated_at  datetime(6)  null,
    event_id    bigint       not null,
    is_finished bit          not null,
    user_id     varchar(255) not null
);

create index idx_event_assigned_user__event_id__user_id
    on event_assigned_user (event_id, user_id);

create table notification
(
    notification_type varchar(31)  not null,
    id                bigint auto_increment
        primary key,
    created_at        datetime(6)  null,
    updated_at        datetime(6)  null,
    content           varchar(255) null,
    title             varchar(255) not null,
    user_id           varchar(255) not null
);

create index idx_notification__user_id
    on notification (user_id);

create table event_notification
(
    event_id bigint not null,
    id       bigint not null
        primary key,
    constraint FK3h54vfg4iyrq4n58rphha13su
        foreign key (id) references notification (id)
);

create index idx_event_notification__event_id
    on event_notification (event_id);

create table reply_comment
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)  null,
    updated_at datetime(6)  null,
    content    varchar(255) not null,
    event_id   bigint       not null,
    user_id    varchar(255) not null
);

create index idx_reply_comment__event_id
    on reply_comment (event_id);

create table reply_event
(
    event_details varchar(255) not null,
    id            bigint       not null
        primary key,
    constraint FK96naaixsajjfdo28q9sx3spsj
        foreign key (id) references event (id)
);

create table user_device
(
    id           bigint auto_increment
        primary key,
    created_at   datetime(6)             null,
    updated_at   datetime(6)             null,
    device       enum ('ANDROID', 'IOS') not null,
    device_token varchar(255)            not null,
    user_id      varchar(255)            not null,
    constraint UKm7th1gkgig3biayqgkd0sl4y0
        unique (device_token)
);

create index idx_user_device__user_id
    on user_device (user_id);

create table users
(
    id         varchar(255)                      not null
        primary key,
    created_at datetime(6)                       null,
    updated_at datetime(6)                       null,
    email      varchar(255)                      not null,
    name       varchar(255)                      not null,
    picture    varchar(255)                      not null,
    user_role  enum ('ADMIN', 'MANAGER', 'USER') not null,
    constraint UK6dotkott2kjsp8vw4d0m25fb7
        unique (email)
);

create table workspace
(
    id               bigint auto_increment
        primary key,
    created_at       datetime(6)                  null,
    updated_at       datetime(6)                  null,
    belonging_number int                          not null,
    name             varchar(255)                 not null,
    thumbnail        varchar(255)                 not null,
    workspace_plan   enum ('BASIC', 'ENTERPRISE') null
);

create table workspace_user
(
    id             bigint auto_increment
        primary key,
    created_at     datetime(6)                null,
    updated_at     datetime(6)                null,
    status         enum ('ACTIVE', 'PENDING') not null,
    user_id        varchar(255)               not null,
    workspace_id   bigint                     not null,
    workspace_role enum ('LEADER', 'MEMBER')  not null
);

create index idx_workspace_user__workspace_id
    on workspace_user (user_id, workspace_id);