insert into authorities(id, authority)
values (1,'ROLE_USER');

insert into authorities(id, authority)
values (2,'ROLE_ADMIN');

insert into users(username, email, password, first_name, last_name, enabled, authority_id)
values ('user', 'user@yahoo.com', 'password', 'user_firstname', 'user_lastname', true, 1);

insert into users(username, email, password, first_name, last_name, enabled, authority_id)
values ('admin', 'admin@yahoo.com', 'password', 'admin_firstname', 'admin_lastname', true, 2);

insert into users(username, email, password, first_name, last_name, enabled, authority_id)
values ('ana12', 'ana@yahoo.com', 'password', 'ana', 'paraschiv', true, 1);

insert into users(username, email, password, first_name, last_name, enabled, authority_id)
values ('anda12' ,'anda@yahoo.com', 'password', 'anda', 'paraschiva', true, 1);

insert into users(username, email, password, first_name, last_name, enabled, authority_id)
values ('mara12' ,'mara@yahoo.com', 'password', 'mara',' santila', true, 1);

insert into categories(id, name) values (1, 'romantic');
insert into categories(id, name) values (2, 'fictiune');

