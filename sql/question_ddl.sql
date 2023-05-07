drop table question;

CREATE TABLE IF NOT EXISTS `question`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `text` VARCHAR(200) NOT NULL,
    `grade` VARCHAR(10) NOT null,
    `page` VARCHAR(40) NOT null,
    `learned` TINYINT(1) NOT NULL DEFAULT 0,
    `cdate` DATETIME NOT NULL DEFAULT NOW(),
    `edate` DATETIME NOT NULL DEFAULT NOW(),
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
