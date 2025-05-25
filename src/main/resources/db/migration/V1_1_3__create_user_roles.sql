create table if not exists user_roles (
    id serial primary key,
    user_id integer not null,
    role_id integer not null,
    constraint fk_user foreign key (user_id) references users(id),
    constraint fk_role foreign key (role_id) references roles(id)
);