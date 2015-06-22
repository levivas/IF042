# SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
# SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
# SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `mydb` ;
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`categories` ;

CREATE TABLE IF NOT EXISTS `mydb`.`categories` (
  `category_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`tests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tests` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tests` (
  `test_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` INT(10) UNSIGNED NOT NULL,
  `name` VARCHAR(70) NOT NULL,
  `grade` INT(10) UNSIGNED NOT NULL,
  `duration` INT(5) NOT NULL,
  `edited` TIMESTAMP NULL DEFAULT NULL,
  `created` TIMESTAMP NULL DEFAULT NULL,
  `created_by` VARCHAR(100) NULL DEFAULT NULL,
  `edited_by` VARCHAR(100) NULL DEFAULT NULL,
  `easy_questions` TINYINT(4) NOT NULL,
  `normal_questions` TINYINT(4) NOT NULL,
  `hard_questions` TINYINT(4) NOT NULL,
  PRIMARY KEY (`test_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_tests_categories1_idx` (`category_id` ASC),
  CONSTRAINT `fk_tests_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `mydb`.`categories` (`category_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`questions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`questions` ;

CREATE TABLE IF NOT EXISTS `mydb`.`questions` (
  `question_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `test_id` INT(10) UNSIGNED NOT NULL,
  `title` TEXT NOT NULL,
  `rank` TINYINT(4) NOT NULL,
  `type` TINYINT(4) NOT NULL,
  PRIMARY KEY (`question_id`),
  INDEX `fk_questions_tests_idx` (`test_id` ASC),
  CONSTRAINT `fk_questions_tests`
    FOREIGN KEY (`test_id`)
    REFERENCES `mydb`.`tests` (`test_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`answers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`answers` ;

CREATE TABLE IF NOT EXISTS `mydb`.`answers` (
  `answer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `is_true` TINYINT(1) NOT NULL,
  `question_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`answer_id`),
  UNIQUE INDEX `answer_id_UNIQUE` (`answer_id` ASC),
  INDEX `fk_answers_questions1_idx` (`question_id` ASC),
  CONSTRAINT `fk_answers_questions1`
    FOREIGN KEY (`question_id`)
    REFERENCES `mydb`.`questions` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`session_test`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`session_test` ;

CREATE TABLE IF NOT EXISTS `mydb`.`session_test` (
  `session_test_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `finish_time` TIMESTAMP NULL DEFAULT NULL,
  `test_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`session_test_id`),
  UNIQUE INDEX `session_test_id_UNIQUE` (`session_test_id` ASC),
  INDEX `fk_session_test_tests1_idx` (`test_id` ASC),
  CONSTRAINT `fk_session_test_tests1`
    FOREIGN KEY (`test_id`)
    REFERENCES `mydb`.`tests` (`test_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`roles` ;

CREATE TABLE IF NOT EXISTS `mydb`.`roles` (
  `role_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`groups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`groups` ;

CREATE TABLE IF NOT EXISTS `mydb`.`groups` (
  `group_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role_id` INT(10) UNSIGNED NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`group_id`),
  UNIQUE INDEX `name_UNIQUE_Gr` (`name` ASC),
  INDEX `fk_groups_roles1_idx` (`role_id` ASC),
  CONSTRAINT `fk_groups_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `mydb`.`roles` (`role_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `group_id` INT(10) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `middle_name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(65) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name` (`user_name` ASC),
  INDEX `fk_students_groups1_idx` (`group_id` ASC),
  CONSTRAINT `fk_students_groups1`
    FOREIGN KEY (`group_id`)
    REFERENCES `mydb`.`groups` (`group_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8;



-- -----------------------------------------------------
-- Table `mydb`.`session_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`session_question` ;

CREATE TABLE IF NOT EXISTS `mydb`.`session_question` (
  `session_question_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `session_test_id` INT(10) UNSIGNED NOT NULL,
  `question_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`session_question_id`),
  UNIQUE INDEX `session_question_id_UNIQUE` (`session_question_id` ASC),
  INDEX `fk_session_question_session_test1_idx` (`session_test_id` ASC),
  INDEX `fk_session_question_questions1_idx` (`question_id` ASC),
  CONSTRAINT `fk_session_question_questions1`
    FOREIGN KEY (`question_id`)
    REFERENCES `mydb`.`questions` (`question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE ,
  CONSTRAINT `fk_session_question_session_test1`
    FOREIGN KEY (`session_test_id`)
    REFERENCES `mydb`.`session_test` (`session_test_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`session_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`session_answer` ;

CREATE TABLE IF NOT EXISTS `mydb`.`session_answer` (
  `session_answer_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `session_question_id` INT(10) UNSIGNED NOT NULL,
  `answer_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`session_answer_id`),
  INDEX `fk_session_answer_session_question1_idx` (`session_question_id` ASC),
  INDEX `fk_session_answer_answers1_idx` (`answer_id` ASC),
  CONSTRAINT `fk_session_answer_answers1`
    FOREIGN KEY (`answer_id`)
    REFERENCES `mydb`.`answers` (`answer_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_session_answer_session_question1`
    FOREIGN KEY (`session_question_id`)
    REFERENCES `mydb`.`session_question` (`session_question_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`tests_results`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`tests_results` ;

CREATE TABLE IF NOT EXISTS `mydb`.`tests_results` (
  `test_result_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `test_id` INT(10) UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `mark_percents` INT(10) UNSIGNED NOT NULL,
  `pass_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_test_id` INT(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`test_result_id`),
  INDEX `fk_tests_results_tests1_idx` (`test_id` ASC),
  INDEX `fk_tests_results_students1_idx` (`user_id` ASC),
  INDEX `fk_tests_results_session_test1_idx` (`session_test_id` ASC),
  CONSTRAINT `fk_tests_results_session_test1`
    FOREIGN KEY (`session_test_id`)
    REFERENCES `mydb`.`session_test` (`session_test_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tests_results_students1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_tests_results_tests1`
    FOREIGN KEY (`test_id`)
    REFERENCES `mydb`.`tests` (`test_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`users_has_categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_has_categories` ;

CREATE TABLE IF NOT EXISTS `mydb`.`users_has_categories` (
  `users_user_id` INT(10) UNSIGNED NOT NULL,
  `categories_category_id` INT(10) UNSIGNED NOT NULL,
  INDEX `fk_users_has_categories_categories1_idx` (`categories_category_id` ASC),
  INDEX `fk_users_has_categories_users1_idx` (`users_user_id` ASC),
  CONSTRAINT `fk_users_has_categories_categories1`
    FOREIGN KEY (`categories_category_id`)
    REFERENCES `mydb`.`categories` (`category_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_users_has_categories_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `mydb`.`available_tests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`available_tests` ;

CREATE TABLE `mydb`.`available_tests` (
  `avaliable_test_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `test_id` INT(10) UNSIGNED NOT NULL,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `finish_time` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
  `session_test_id` INT(10) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`avaliable_test_id`),
  INDEX `fk_avaliable_tests_tests1_idx` (`test_id` ASC),
  INDEX `fk_avaliable_tests_students1_idx` (`user_id` ASC),
  INDEX `fk_available_tests_session_test1_idx` (`session_test_id` ASC),
  CONSTRAINT `fk_available_tests_session_test1`
    FOREIGN KEY (`session_test_id`)
    REFERENCES `mydb`.`session_test` (`session_test_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_avaliable_tests_students1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_avaliable_tests_tests1`
    FOREIGN KEY (`test_id`)
    REFERENCES `mydb`.`tests` (`test_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
