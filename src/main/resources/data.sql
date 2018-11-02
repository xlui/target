DROP TABLE IF EXISTS `t_user`;
DROP TABLE IF EXISTS `t_target`;
DROP TABLE IF EXISTS `t_record`;

CREATE TABLE `t_user` (
  uid      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nickname VARCHAR(128),
  gender   INT,
  birthday DATE,
  username VARCHAR(128),
  password VARCHAR(128)
)
  ENGINE InnoDB
  CHARACTER SET UTF8MB4;

CREATE TABLE `t_target` (
  tid         INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uid         INT NOT NULL,
  title       VARCHAR(128),
  description VARCHAR(128),
  startDate   DATE,
  endDate     DATE,
  punchStart  TIME,
  punchEnd    TIME,
  `repeat`    INT,
  INDEX i_target_user (uid)
)
  ENGINE InnoDB
  CHARACTER SET UTF8MB4;

CREATE TABLE `t_record` (
  rid           INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uid           INT NOT NULL,
  tid           INT NOT NULL,
  punchDateTime DATETIME,
  INDEX i_record_user (uid),
  INDEX i_record_target (tid)
)
  ENGINE InnoDB
  CHARACTER SET UTF8MB4;