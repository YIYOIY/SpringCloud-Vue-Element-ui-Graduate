CREATE DATABASE if not EXISTS yoi;
USE yoi;

CREATE TABLE if not EXISTS`t_admin` (
                                        `admin_id` INT  AUTO_INCREMENT PRIMARY KEY ,
                                        `admin_name` VARCHAR ( 20 ),
    `admin_password` VARCHAR ( 25 ) NOT NULL DEFAULT ( 111111 )
    );

CREATE TABLE if not EXISTS `t_user` (
                                        `user_id` INT AUTO_INCREMENT PRIMARY KEY,
                                        `user_name` VARCHAR ( 20 ),
    `user_password` VARCHAR ( 25 ) NOT NULL DEFAULT ( 111111 ),
    user_sex ENUM("男","女","保密") DEFAULT("保密") NOT NULL,
    user_birth DATE DEFAULT (CURRENT_DATE),
    `user_address` VARCHAR ( 100 )  NULL,
    `user_phone` VARCHAR ( 11 )  NULL
    );

CREATE TABLE if not EXISTS `t_category` (
                                            `series_id` INT AUTO_INCREMENT  PRIMARY KEY ,
                                            `series_name` VARCHAR ( 10 ) NOT NULL UNIQUE
    );

CREATE TABLE if not EXISTS `t_book` (
                                        `book_id` INT  AUTO_INCREMENT PRIMARY KEY,
                                        `book_name` VARCHAR ( 40 ) NOT NULL,
    book_info TEXT NULL,
    `book_author` VARCHAR ( 25 )  NULL,
    `book_factory` VARCHAR ( 30 )  NULL,
    `book_addDate` DATE DEFAULT (CURRENT_DATE()),
    `book_price` DECIMAL(6,2) NOT NULL DEFAULT (100),
    `book_num` DOUBLE NOT NULL DEFAULT ( 100 ),
    `book_picture` VARCHAR ( 300 ) NULL,
    book_detail TEXT NULL,
    series_id INT, FOREIGN KEY (series_id) REFERENCES t_category(series_id) ON UPDATE CASCADE ON DELETE RESTRICT
    );

CREATE TABLE if not EXISTS `t_order` (
                                         `order_id` INT AUTO_INCREMENT PRIMARY KEY,
                                         `book_id` INT ,
                                         `user_id` INT ,
                                         `order_time` DATE DEFAULT (CURRENT_DATE()),
    order_status enum('已购买','未购买') DEFAULT('未购买'),
    `book_num` INT DEFAULT(1),
    FOREIGN KEY ( `book_id` ) REFERENCES `t_book` ( `book_id` ) ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY ( `user_id` ) REFERENCES `t_user` ( `user_id` ) ON UPDATE CASCADE ON DELETE RESTRICT
    );
