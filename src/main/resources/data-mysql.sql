insert into authorities(id, authority)
values (1,'ROLE_USER');

insert into authorities(id, authority)
values (2,'ROLE_ADMIN');

insert into users(username,email,password,first_name,last_name,enabled,authority_id)
values ('user12','user12@yahoo.com','pass','lara','sorana',true,1);

insert into users(username,email,password,first_name,last_name,enabled,authority_id)
values ('admin12','admin12@yahoo.com','pass','sina','mia',true,2);

insert into categories(id,name) values (1,'romantic');
insert into categories(id,name) values (2,'fictiune');

