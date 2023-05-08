drop table quiz_detail;
drop table quiz;
drop table question;

CREATE TABLE IF NOT EXISTS `question`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `text` VARCHAR(200) NOT NULL,
    `grade` VARCHAR(10) NOT null,
    `page` VARCHAR(40) NOT null,
    `learned` TINYINT(1) NOT NULL DEFAULT 0,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    `cdate` DATETIME NOT NULL DEFAULT NOW(),
    `edate` DATETIME NOT NULL DEFAULT NOW(),
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `quiz`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `cdate` DATETIME NOT NULL DEFAULT NOW(),
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `quiz_detail`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `quiz_id` INT UNSIGNED NOT NULL,
    `question_id` INT UNSIGNED NOT NULL,
    `spend_time` INT UNSIGNED NOT NULL,
    `cdate` DATETIME NOT NULL DEFAULT NOW(),
   PRIMARY KEY ( `id` ),
   CONSTRAINT fk_quiz_id FOREIGN KEY (quiz_id) REFERENCES quiz(id),
   CONSTRAINT fk_question_id FOREIGN KEY (question_id) REFERENCES question(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
--CREATE TABLE IF NOT EXISTS `quiz_detail`(
--    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
--    `quizId` INT UNSIGNED,
--    `questionId` INT UNSIGNED,
--    `spendTime` INT UNSIGNED,
--    `cdate` DATETIME NOT NULL DEFAULT NOW(),
--   PRIMARY KEY ( `id` )
--)ENGINE=InnoDB DEFAULT CHARSET=utf8;
