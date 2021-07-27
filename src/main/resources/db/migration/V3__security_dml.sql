create table products_shop.users (
    id          bigserial,
    login       varchar(32) not null unique,
    password    varchar(64) not null,
    updated_at timestamp default now(),
    created_at timestamp default now(),
    primary key (id)
);

create table products_shop.roles (
     id      serial,
     name    varchar(32) not null,
     primary key (id)
);

create table products_shop.users_roles (
   users_id    bigint not null,
   roles_id    int not null,
   primary key (users_id, roles_id),
   foreign key (users_id) references users (id),
   foreign key (roles_id) references roles (id)
);