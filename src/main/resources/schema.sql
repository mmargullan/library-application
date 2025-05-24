create table if not exists roles (
    id serial primary key,
    name varchar(20) not null
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id integer not null,
    CONSTRAINT fk_role
    FOREIGN KEY(role_id)
    REFERENCES roles(id)
);