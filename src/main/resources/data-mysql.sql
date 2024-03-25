insert into users(username,email,password,first_name,last_name,enabled)
values ('user12','user12@yahoo.com','pass','lara','sorana',true);

insert into users(username,email,password,first_name,last_name,enabled)
values ('admin12','admin12@yahoo.com','pass','sina','mia',true);

insert into authorities(id, authority,user_id)
values (1,'ROLE_USER',1);

insert into authorities(id, authority,user_id)
values (2,'ROLE_ADMIN',2);