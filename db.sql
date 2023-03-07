create table hotel
(
    id    int auto_increment
        primary key,
    hotel varchar(45) not null
);

create table loyalty_program
(
    step         int default 1   not null,
    discount     int default 0   null,
    max_discount int default 100 null,
    id           int default 1   not null
);

create table order_status
(
    id     int auto_increment
        primary key,
    status varchar(45) null
);

create table role
(
    id        int         not null
        primary key,
    role_name varchar(10) not null,
    constraint id_UNIQUE
        unique (id),
    constraint roleName_UNIQUE
        unique (role_name)
);

create table type
(
    id   int         not null
        primary key,
    type varchar(25) not null
);

create table tour
(
    id          int auto_increment
        primary key,
    title       varchar(100)         not null,
    persons     int                  not null,
    price       double               not null,
    hot         tinyint(1) default 0 not null,
    image       longblob             null,
    description varchar(1000)        null,
    type_id     int        default 1 not null,
    hotel_id    int        default 1 not null,
    constraint title_UNIQUE
        unique (title),
    constraint fk_tour_hotel
        foreign key (hotel_id) references hotel (id)
            on update cascade on delete cascade,
    constraint fk_tour_type1
        foreign key (type_id) references type (id)
            on update cascade on delete cascade
);

create index fk_tour_hotel_idx
    on tour (hotel_id);

create index fk_tour_type1_idx
    on tour (type_id);

create table user
(
    id                int auto_increment
        primary key,
    email             varchar(120)      not null,
    password          varchar(255)      not null,
    name              varchar(45)       not null,
    surname           varchar(45)       not null,
    avatar            mediumblob        null,
    discount          int               null,
    is_blocked        tinyint           null,
    is_email_verified tinyint default 0 null,
    verification_code varchar(255)      null,
    role_id           int     default 2 not null,
    constraint email_UNIQUE
        unique (email),
    constraint id_UNIQUE
        unique (id),
    constraint fk_user_role
        foreign key (role_id) references role (id)
            on update cascade on delete cascade
);

create table `order`
(
    id              int auto_increment
        primary key,
    order_status_id int    default 1 not null,
    user_id         int              not null,
    tour_id         int              not null,
    discount        int    default 0 null,
    total_cost      double default 0 null,
    date            date             not null,
    constraint fk_order_order_status1
        foreign key (order_status_id) references order_status (id)
            on update cascade on delete cascade,
    constraint fk_order_tour1
        foreign key (tour_id) references tour (id)
            on update cascade on delete cascade,
    constraint fk_order_user1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create index fk_order_order_status1_idx
    on `order` (order_status_id);

create index fk_order_tour1_idx
    on `order` (tour_id);

create index fk_order_user1_idx
    on `order` (user_id);

create index fk_user_role_idx
    on user (role_id);


INSERT INTO `role` (`id`, `role_name`) VALUES (1, 'ADMIN');
INSERT INTO `role` (`id`, `role_name`) VALUES (2, 'USER');

INSERT INTO `type` (`id`, `type`) VALUES (1, 'REST');
INSERT INTO `type` (`id`, `type`) VALUES (2, 'EXCURSION');
INSERT INTO `type` (`id`, `type`) VALUES (3, 'SHOPPING');

INSERT INTO `hotel` (`id`, `hotel`) VALUES (1, 'HOTEL');
INSERT INTO `hotel` (`id`, `hotel`) VALUES (2, 'HOSTEL');
INSERT INTO `hotel` (`id`, `hotel`) VALUES (3, 'MOTEL');

INSERT INTO `order_status` (`id`, `status`) VALUES (1, 'REGISTERED');
INSERT INTO `order_status` (`id`, `status`) VALUES (2, 'PAID');
INSERT INTO `order_status` (`id`, `status`) VALUES (3, 'CANCELED');
