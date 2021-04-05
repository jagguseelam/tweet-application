CREATE TABLE IF NOT EXISTS `tweet_app`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(32) NOT NULL,
  `last_name` VARCHAR(32) NULL,
  `gender` VARCHAR(45) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `user_created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_logged_in` TINYINT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);
  
CREATE TABLE IF NOT EXISTS `tweet_app`.`tweets` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tweet` VARCHAR(255) NOT NULL,
  `tweets_user_id` INT NOT NULL,
  PRIMARY KEY (`id`, `tweets_user_id`),
  INDEX `fk_tweets_user_idx` (`tweets_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_tweets_user`
    FOREIGN KEY (`tweets_user_id`)
    REFERENCES `tweet_app`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `tweet_app`.`login_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `login_time` DATETIME NULL,
  `logout_time` DATETIME NULL,
  `login_history_user_id` INT NOT NULL,
  PRIMARY KEY (`id`, `login_history_user_id`),
  INDEX `fk_login_history_user1_idx` (`login_history_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_login_history_user1`
    FOREIGN KEY (`login_history_user_id`)
    REFERENCES `tweet_app`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;