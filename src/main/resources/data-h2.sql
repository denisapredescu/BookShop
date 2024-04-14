insert into authorities(id, authority)
values (1, 'ROLE_USER');
insert into authorities(id, authority)
values (2, 'ROLE_ADMIN');

insert into users(id, username, email, password, first_name, last_name, enabled, authority_id)
values (1, 'user', 'user@yahoo.com', 'password', 'user_firstname', 'user_lastname', true, 1);

insert into users(id, username, email, password, first_name, last_name, enabled, authority_id)
values (2, 'admin', 'admin@yahoo.com', 'password', 'admin_firstname', 'admin_lastname', true, 2);

insert into users(id, username, email, password, first_name, last_name, enabled, authority_id)
values (3, 'ana12', 'ana@yahoo.com', 'password', 'ana', 'paraschiv', true, 1);

insert into users(id, username, email, password, first_name, last_name, enabled, authority_id)
values (4, 'anda12' ,'anda@yahoo.com', 'password', 'anda', 'paraschiva', true, 1);

insert into users(id, username, email, password, first_name, last_name, enabled, authority_id)
values (5, 'mara12' ,'mara@yahoo.com', 'password', 'mara',' santila', true, 1);

insert into categories(id, name) values (1, 'romantic');
insert into categories(id, name) values (2, 'fiction');

insert into authors(id, first_name, last_name, nationality)
values (1, 'author1_firstname', 'author1_lastname', 'nationality1');

insert into authors(id, first_name, last_name, nationality)
values (2, 'author2_firstname', 'author2_lastname', 'nationality1');

insert into authors(id, first_name, last_name, nationality)
values (3, 'author3_firstname', 'author3_lastname', 'nationality22');

insert into baskets(id, sent, cost, user_id)
values (1, false, 0, 1);

insert into baskets(id, sent, cost, user_id)
values (2, false, 22.5, 3);

insert into baskets(id, sent, cost, user_id)
values (3, true, 60.99, 1);

insert into books(id, is_deleted, name, price, author_id)
values (1, false, 'book1', 11.25, 1);

insert into books(id, is_deleted, name, price, author_id)
values (2, false, 'book2', 20.33, 2);

insert into  book_baskets(id, copies, price, basket_id, book_id)
values (1, 2, 22.5, 2, 1);

insert into  book_baskets(id, copies, price, basket_id, book_id)
values (2, 3, 60.99, 3, 2);

insert into coupons(id, discount, user_id)
values (1, 10, 1);

insert into books_book_categories(book_id, book_categories_id)
values (1, 1);

insert into books_book_categories(book_id, book_categories_id)
values (2, 1);