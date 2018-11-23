DROP DATABASE IF EXISTS target;
CREATE DATABASE target CHARACTER SET utf8mb4;
USE target;
CREATE USER admin@"%" IDENTIFIED BY "admin";
GRANT ALL PRIVILEGES ON target.* to admin@"%" WITH GRANT OPTION;
FLUSH PRIVILEGES;

DROP TABLE IF EXISTS `t_user`;
DROP TABLE IF EXISTS `t_target`;
DROP TABLE IF EXISTS `t_record`;

CREATE TABLE `t_user` (
  uid      INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nickname VARCHAR(128),
  gender   INT          DEFAULT 0,
  birthday DATE,
  username VARCHAR(128) UNIQUE,
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
  checkinStart  TIME,
  checkinEnd    TIME,
  `repeat`    TINYINT,
  INDEX i_target_user (uid)
)
  ENGINE InnoDB
  CHARACTER SET UTF8MB4;

CREATE TABLE `t_record` (
  rid           INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uid           INT NOT NULL,
  tid           INT NOT NULL,
  checkinDateTime DATETIME,
  reCheckIn       BOOLEAN      DEFAULT FALSE,
  reason        VARCHAR(128) DEFAULT '',
  INDEX i_record_user (uid),
  INDEX i_record_target (tid)
)
  ENGINE InnoDB
  CHARACTER SET UTF8MB4;

# init
INSERT INTO t_user (nickname, gender, username, password) VALUES ('xlui', 1, 'i@xlui.me', 'pass');
INSERT INTO t_target(uid, title, description) VALUES (1, 'Moab Is My Washpot', 'Accusamus excepturi quo rem cupiditate qui qui.');
