-- MySQL Script generated by MySQL Workbench
-- Mon Dec 11 17:43:03 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema SA
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `SA` ;

-- -----------------------------------------------------
-- Schema SA
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SA` DEFAULT CHARACTER SET utf8 ;
USE `SA` ;

-- -----------------------------------------------------
-- Table `SA`.`TBL_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_member` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_member` (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `member_name` VARCHAR(50) NOT NULL,
  `member_account` VARCHAR(100) NOT NULL,
  `member_password` VARCHAR(45) NOT NULL,
  `member_phone` VARCHAR(10) NOT NULL,
  `member_group` VARCHAR(45) NOT NULL,
  `created_time` DATETIME NOT NULL,
  `identity` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_course` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_course` (
  `course_id` INT NOT NULL AUTO_INCREMENT,
  `course_name` VARCHAR(50) NOT NULL,
  `course_fee` INT NOT NULL,
  `course_time` DATETIME NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`course_id`, `member_id`),
  INDEX `member_course_fk_idx` (`member_id` ASC) ,
  CONSTRAINT `member_course_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_course_registration_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_course_registration_record` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_course_registration_record` (
  `course_registration_record_id` INT NOT NULL AUTO_INCREMENT,
  `member_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`course_registration_record_id`, `member_id`, `course_id`),
  INDEX `member_course_registratino_record_fk_idx` (`member_id` ASC) ,
  INDEX `course_id_course_registration_record_FK_idx` (`course_id` ASC) ,
  CONSTRAINT `member_course_registratino_record_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `course_id_course_registration_record_FK`
    FOREIGN KEY (`course_id`)
    REFERENCES `SA`.`TBL_course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_paid_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_paid_record` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_paid_record` (
  `paid_sequence` INT(16) NOT NULL,
  `paid_time` DATETIME NOT NULL,
  `paid_fee` INT NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`paid_sequence`, `member_id`),
  INDEX `member_paid_record_fk_idx` (`member_id` ASC) ,
  CONSTRAINT `member_paid_record_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_course_value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_course_value` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_course_value` (
  `member_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  `course_value` INT NOT NULL,
  PRIMARY KEY (`member_id`, `course_id`),
  INDEX `course_course_value_fk_idx` (`course_id` ASC) ,
  CONSTRAINT `course_course_value_fk`
    FOREIGN KEY (`course_id`)
    REFERENCES `SA`.`TBL_course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `member_course_value_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_instrument`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_instrument` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_instrument` (
  `instrument_id` INT NOT NULL AUTO_INCREMENT,
  `instrument_name` VARCHAR(50) NOT NULL,
  `instrument_quantity` INT NOT NULL,
  PRIMARY KEY (`instrument_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_borrow_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_borrow_record` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_borrow_record` (
  `borrow_record_id` INT NOT NULL AUTO_INCREMENT,
  `borrow_time` DATETIME NOT NULL,
  `return_time` DATETIME NOT NULL,
  `instrument_id` INT NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`borrow_record_id`, `member_id`, `instrument_id`),
  INDEX `member_borrow_record_fk_idx` (`member_id` ASC) ,
  INDEX `borrow_record_instrument_fk_idx` (`instrument_id` ASC) ,
  CONSTRAINT `member_borrow_record_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `borrow_record_instrument_fk`
    FOREIGN KEY (`instrument_id`)
    REFERENCES `SA`.`TBL_instrument` (`instrument_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_article` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_article` (
  `article_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `article_content` VARCHAR(1000) NOT NULL,
  `article_time` DATETIME NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`article_id`, `member_id`),
  INDEX `member_article_fk_idx` (`member_id` ASC) ,
  CONSTRAINT `member_article_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_message` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_message` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `message_content` VARCHAR(1000) NOT NULL,
  `message_time` DATETIME NOT NULL,
  `article_id` INT NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`message_id`, `member_id`, `article_id`),
  INDEX `member_message_fk_idx` (`member_id` ASC) ,
  INDEX `article_message_fk_idx` (`article_id` ASC) ,
  CONSTRAINT `member_message_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `article_message_fk`
    FOREIGN KEY (`article_id`)
    REFERENCES `SA`.`TBL_article` (`article_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SA`.`TBL_homework`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_homework` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_homework` (
  `homework_id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(2000) NOT NULL,
  `score` INT NOT NULL,
  `homework_time` DATETIME NOT NULL,
  `member_id` INT NOT NULL,
  `course_id` INT NOT NULL,
  PRIMARY KEY (`homework_id`, `course_id`, `member_id`),
  INDEX `idx_member_homework_member_fk` (`member_id` ASC),
  INDEX `idx_member_homework_course_fk` (`course_id` ASC),
  CONSTRAINT `fk_tbl_homework_member`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_homework_course`
    FOREIGN KEY (`course_id`)
    REFERENCES `SA`.`TBL_course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `SA`.`TBL_announcement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SA`.`TBL_announcement` ;

CREATE TABLE IF NOT EXISTS `SA`.`TBL_announcement` (
  `announcement_id` INT NOT NULL AUTO_INCREMENT,
  `announcement_content` VARCHAR(2000) NOT NULL,
  `announcement_time` DATETIME NOT NULL,
  `member_id` INT NOT NULL,
  PRIMARY KEY (`announcement_id`, `member_id`),
  INDEX `member_announcement_fk_idx` (`member_id` ASC) ,
  CONSTRAINT `member_announcement_fk`
    FOREIGN KEY (`member_id`)
    REFERENCES `SA`.`TBL_member` (`member_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



