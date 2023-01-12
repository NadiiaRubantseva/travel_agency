-- Schema creation

DROP SCHEMA IF EXISTS `db`;

CREATE SCHEMA IF NOT EXISTS `db`;

USE `db`;

CREATE TABLE IF NOT EXISTS `hotel` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hotel` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL,
  `role_name` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `roleName_UNIQUE` (`role_name` ASC) VISIBLE);

CREATE TABLE IF NOT EXISTS `type` (
  `id` INT NOT NULL,
  `type` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `db`.`tour` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `persons` INT NOT NULL,
  `price` DOUBLE NOT NULL,
  `hot` BOOLEAN NOT NULL DEFAULT '0',
  `discount` INT NOT NULL DEFAULT '0',
  `type_id` INT NOT NULL DEFAULT '1',
  `hotel_id` INT NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE,
  INDEX `fk_tour_hotel_idx` (`hotel_id` ASC) VISIBLE,
  INDEX `fk_tour_type1_idx` (`type_id` ASC) VISIBLE,
  CONSTRAINT `fk_tour_hotel`
    FOREIGN KEY (`hotel_id`)
    REFERENCES `hotel` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tour_type1`
    FOREIGN KEY (`type_id`)
    REFERENCES `type` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(120) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `role_id` INT NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS `user_has_tour` (
  `user_id` INT NOT NULL,
  `tour_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `tour_id`),
  INDEX `fk_user_has_tour_tour1_idx` (`tour_id` ASC) VISIBLE,
  INDEX `fk_user_has_tour_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_tour_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_tour_tour1`
    FOREIGN KEY (`tour_id`)
    REFERENCES `tour` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE IF NOT EXISTS `order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ordercol` VARCHAR(45) NULL,
  `order_status_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `tour_id` INT NOT NULL,
  `discount` INT NULL DEFAULT 0,
  `total_cost` DOUBLE NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_tour1_idx` (`tour_id` ASC) VISIBLE,
  INDEX `fk_order_order_status1_idx` (`order_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_tour1`
    FOREIGN KEY (`tour_id`)
    REFERENCES `tour` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_order_order_status1`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `order_status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

INSERT INTO `role` (`id`, `role_name`) VALUES (1, 'ADMIN');
INSERT INTO `role` (`id`, `role_name`) VALUES (2, 'USER');

INSERT INTO `type` (`id`, `type`) VALUES (1, 'REST');
INSERT INTO `type` (`id`, `type`) VALUES (2, 'EXCURSION');
INSERT INTO `type` (`id`, `type`) VALUES (3, 'SHOPPING');

INSERT INTO `hotel` (`id`, `hotel`) VALUES (1, 'HOTEL');
INSERT INTO `hotel` (`id`, `hotel`) VALUES (2, 'HOSTEL');
INSERT INTO `hotel` (`id`, `hotel`) VALUES (3, 'MOTEL');

INSERT INTO `order_status` (`id`, `status`) VALUES (1, 'REGISTERED');
INSERT INTO `order_status` (`id`, `status`) VALUES (2, 'PAID');
INSERT INTO `order_status` (`id`, `status`) VALUES (3, 'CANCELED');
