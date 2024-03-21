insert into users(username,password,enabled)
values ('user','pass',true);

insert into users(username,password,enabled)
values ('admin','pass',true);

insert into authorities(id, authority)
values (1,'ROLE_USER');

insert into authorities(id, authority)
values (2,'ROLE_ADMIN');