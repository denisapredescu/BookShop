-- MySQL Workbench Forward Engineering
-- -----------------------------------------------------
-- Schema bookshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table bookshop.authorities
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS authorities (
                                           id INT NOT NULL,
                                           authority VARCHAR(50) NOT NULL,
                                           user_id INT NULL DEFAULT NULL,
                                           PRIMARY KEY (id))
;


-- -----------------------------------------------------
-- Table bookshop.users
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
      id INT NOT NULL AUTO_INCREMENT,
      enabled BOOLEAN NOT NULL,
    password VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    authority_id INT NULL DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (authority_id)
    REFERENCES authorities (id))
   ;



-- -----------------------------------------------------
-- Table bookshop.authorities_users
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table bookshop.authors
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS authors (
                                                    id INT NOT NULL,
                                                    first_name VARCHAR(255) NULL DEFAULT NULL,
    last_name VARCHAR(255) NULL DEFAULT NULL,
    nationality VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (id))
    ;


-- -----------------------------------------------------
-- Table bookshop.authors_seq
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table bookshop.baskets
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS baskets (
                                                    id INT NOT NULL,
                                                    cost DOUBLE NULL DEFAULT NULL,
                                                    sent BOOLEAN NULL DEFAULT NULL,
    user_id INT NULL DEFAULT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (user_id)
    REFERENCES users (id))
    ;


-- -----------------------------------------------------
-- Table bookshop.baskets_seq
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table bookshop.books
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS books (
                                                  id INT NOT NULL,
                                                  is_deleted BOOLEAN NULL DEFAULT NULL,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    series_name VARCHAR(255) NULL DEFAULT NULL,
    volume INT NULL DEFAULT NULL,
    `year` INT NULL DEFAULT NULL,
    author_id INT NULL DEFAULT NULL,
    year_date INT NULL DEFAULT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (author_id)
    REFERENCES authors (id))
    ;


-- -----------------------------------------------------
-- Table bookshop.book_baskets
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS book_baskets (
                                                         id INT NOT NULL,
                                                         copies INT NULL DEFAULT NULL,
                                                         price DOUBLE NULL DEFAULT NULL,
                                                         basket_id INT NULL DEFAULT NULL,
                                                         book_id INT NULL DEFAULT NULL,
                                                         PRIMARY KEY (id),

    FOREIGN KEY (basket_id)
    REFERENCES baskets(id),

    FOREIGN KEY (book_id)
    REFERENCES books (id))
    ;


-- -----------------------------------------------------
-- Table bookshop.book_baskets_seq
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table bookshop.categories
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS categories (
                                                       id INT NOT NULL,
                                                       name VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (id))
   ;


-- -----------------------------------------------------
-- Table bookshop.books_book_categories
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS books_book_categories (
                                                                  book_id INT NOT NULL,
                                                                  book_categories_id INT NOT NULL,

    FOREIGN KEY (book_id)
    REFERENCES books (id),

    FOREIGN KEY (book_categories_id)
    REFERENCES categories (id))
   ;


-- -----------------------------------------------------
-- Table bookshop.books_seq
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table bookshop.cupons
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS cupons (
                                                   id INT NOT NULL,
                                                   discount DOUBLE NULL DEFAULT NULL,
                                                   user_id INT NULL DEFAULT NULL,
                                                   PRIMARY KEY (id),

    FOREIGN KEY (user_id)
    REFERENCES users (id))
    ;

