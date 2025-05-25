create table if not exists roles (
    id serial primary key,
    name varchar(20) not null
);

insert into roles (name) values ('USER') on conflict do nothing;
insert into roles (name) values ('ADMIN') on conflict do nothing;
insert into roles (name) values ('AUTHOR') on conflict do nothing;