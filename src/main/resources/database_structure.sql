CREATE DATABASE IF NOT EXISTS event_manager;
use event_manager;

DROP table user_validation;
CREATE table IF NOT EXISTS user_validation ( -- admin can delete user
id int auto_increment not null,
user_full_name varchar (50) not null, -- can see owner
email varchar (50),-- can see admin
phone varchar (50),-- can see admin
login_name varchar (50) not null UNIQUE, -- only for login
user_password varchar (50) not null,  -- only for login
primary key(id)
);