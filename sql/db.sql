# Author: giuliobosco
# Version: 0.1.1 (2019-08-19 - 2019-09-15)

SET sql_mode = '';
# -----------------------------------------------
#
# CREATE DATABASE
#
# -----------------------------------------------

CREATE DATABASE `freqline`;

# -----------------------------------------------
#
# CREATE TABLES
#
# -----------------------------------------------

CREATE TABLE `freqline`.`audit_action`
(
  `id`   INT AUTO_INCREMENT,

  `name` VARCHAR(10) UNIQUE NOT NULL,

  PRIMARY KEY (`id`)
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`user` (
  `id`           INT AUTO_INCREMENT,

  `created_by`   INT                 NOT NULL,
  `created_date` TIMESTAMP           NOT NULL,
  `updated_by`   INT                 NOT NULL,
  `updated_date` TIMESTAMP           NOT NULL,
  `deleted_by`   INT,
  `deleted_date` TIMESTAMP,

  `username`     VARCHAR(32) UNIQUE  NOT NULL,
  `password`     CHAR(64)            NOT NULL,
  `salt`         CHAR(32)            NOT NULL,
  `firstname`    VARCHAR(64)         NOT NULL,
  `lastname`     VARCHAR(64)         NOT NULL,
  `email`        VARCHAR(128) UNIQUE NOT NULL,
  `favorite_generator` INT,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`created_by`) REFERENCES `freqline`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  FOREIGN KEY (`updated_by`) REFERENCES `freqline`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  FOREIGN KEY (`deleted_by`) REFERENCES `freqline`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`user_audit`
(
  `id`                 INT AUTO_INCREMENT,
  `action`             INT          NOT NULL,

  `audit_id`           INT          NOT NULL,
  `audit_created_by`   INT          NOT NULL,
  `audit_created_date` TIMESTAMP    NOT NULL,
  `audit_updated_by`   INT          NOT NULL,
  `audit_updated_date` TIMESTAMP    NOT NULL,
  `audit_deleted_by`   INT,
  `audit_deleted_date` TIMESTAMP,

  `audit_username`     VARCHAR(32)  NOT NULL,
  `audit_password`     CHAR(64)     NOT NULL,
  `audit_salt`         CHAR(32)     NOT NULL,
  `audit_firstname`    VARCHAR(64)  NOT NULL,
  `audit_lastname`     VARCHAR(64)  NOT NULL,
  `audit_email`        VARCHAR(128) NOT NULL,
  `audit_favorite_generator` INT,

  PRIMARY KEY (`id`),
  FOREIGN KEY (action) REFERENCES `freqline`.`audit_action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`permission`
(
  `id`           INT AUTO_INCREMENT,

  `created_by`   INT                NOT NULL,
  `created_date` TIMESTAMP          NOT NULL,
  `updated_by`   INT                NOT NULL,
  `updated_date` TIMESTAMP          NOT NULL,
  `deleted_by`   INT,
  `deleted_date` TIMESTAMP,

  `name`         VARCHAR(64)        NOT NULL,
  `string`       VARCHAR(32) UNIQUE NOT NULL,
  `description`  VARCHAR(256),

  PRIMARY KEY (`id`)
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`permission_audit`
(
  `id`                 INT AUTO_INCREMENT,
  `action`             INT         NOT NULL,

  `audit_id`           INT         NOT NULL,
  `audit_created_by`   INT         NOT NULL,
  `audit_created_date` TIMESTAMP   NOT NULL,
  `audit_updated_by`   INT         NOT NULL,
  `audit_updated_date` TIMESTAMP   NOT NULL,
  `audit_deleted_by`   INT,
  `audit_deleted_date` TIMESTAMP,

  `audit_name`        VARCHAR(64) NOT NULL,
  `audit_string`       VARCHAR(32) NOT NULL,
  `audit_description`  VARCHAR(256),

  PRIMARY KEY (`id`),
  FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`group`
(
  `id`           INT AUTO_INCREMENT,

  `created_by`   INT                NOT NULL,
  `created_date` TIMESTAMP          NOT NULL,
  `updated_by`   INT                NOT NULL,
  `updated_date` TIMESTAMP          NOT NULL,
  `deleted_by`   INT,
  `deleted_date` TIMESTAMP,

  `name`         VARCHAR(64) UNIQUE NOT NULL,
  `parent_group` INT,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`parent_group`) REFERENCES `freqline`.`group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`group_audit`
(
  `id`                 INT AUTO_INCREMENT,
  `action`             INT         NOT NULL,

  `audit_id`           INT         NOT NULL,
  `audit_created_by`   INT         NOT NULL,
  `audit_created_date` TIMESTAMP   NOT NULL,
  `audit_updated_by`   INT         NOT NULL,
  `audit_updated_date` TIMESTAMP   NOT NULL,
  `audit_deleted_by`   INT,
  `audit_deleted_date` TIMESTAMP,

  `audit_name`         VARCHAR(64) NOT NULL,
  `audit_parent_group` INT,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`group_permission`
(
  `id`               INT AUTO_INCREMENT,

  `created_by`       INT       NOT NULL,
  `created_date`     TIMESTAMP NOT NULL,
  `updated_by`       INT       NOT NULL,
  `updated_date`     TIMESTAMP NOT NULL,
  `deleted_by`       INT,
  `deleted_date`     TIMESTAMP,

  `permission`       INT       NOT NULL,
  `group` INT       NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`permission`) REFERENCES `freqline`.`permission` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`group`) REFERENCES `freqline`.`group` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`group_permission_audit`
(
  `id`                     INT AUTO_INCREMENT,
  `action`                 INT       NOT NULL,

  `audit_id`               INT       NOT NULL,
  `audit_created_by`       INT       NOT NULL,
  `audit_created_date`     TIMESTAMP NOT NULL,
  `audit_updated_by`       INT       NOT NULL,
  `audit_updated_date`     TIMESTAMP NOT NULL,
  `audit_deleted_by`       INT,
  `audit_deleted_date`     TIMESTAMP,

  `audit_permission`       INT       NOT NULL,
  `audit_group` INT       NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`user_group` (
  `id`           INT AUTO_INCREMENT,

  `created_by`   INT       NOT NULL,
  `created_date` TIMESTAMP NOT NULL,
  `updated_by`   INT       NOT NULL,
  `updated_date` TIMESTAMP NOT NULL,
  `deleted_by`   INT,
  `deleted_date` TIMESTAMP,

  `user`        INT       NOT NULL,
  `group` INT NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`group`) REFERENCES `freqline`.`group`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`user`) REFERENCES `freqline`.`user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`user_group_audit`
(
  `id`                 INT AUTO_INCREMENT,
  `action`             INT       NOT NULL,

  `audit_id`           INT       NOT NULL,
  `audit_created_by`   INT       NOT NULL,
  `audit_created_date` TIMESTAMP NOT NULL,
  `audit_updated_by`   INT       NOT NULL,
  `audit_updated_date` TIMESTAMP NOT NULL,
  `audit_deleted_by`   INT,
  `audit_deleted_date` TIMESTAMP,

  `audit_user`        INT       NOT NULL,
  `audit_group`   INT       NOT NULL,

  PRIMARY KEY (`id`),
  FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`generator` (
  `id`           INT AUTO_INCREMENT,

    `created_by`   INT         NOT NULL,
    `created_date` TIMESTAMP   NOT NULL,
    `updated_by`   INT         NOT NULL,
    `updated_date` TIMESTAMP   NOT NULL,
    `deleted_by`   INT,
    `deleted_date` TIMESTAMP,

    `name`         VARCHAR(64) NOT NULL,
    `frequence`    INT         NOT NULL,
    `status`       TINYINT(1)  NOT NULL,
    `ip`           VARCHAR(39) NOT NULL,
    `key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`created_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`updated_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`deleted_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`generator_audit` (
    `id`                 INT AUTO_INCREMENT,
    `action`             INT         NOT NULL,

    `audit_id`           INT         NOT NULL,
    `audit_created_by`   INT         NOT NULL,
    `audit_created_date` TIMESTAMP   NOT NULL,
    `audit_updated_by`   INT         NOT NULL,
    `audit_updated_date` TIMESTAMP   NOT NULL,
    `audit_deleted_by`   INT,
    `audit_deleted_date` TIMESTAMP,

    `audit_name`         VARCHAR(64) NOT NULL,
    `audit_frequence`    INT         NOT NULL,
    `audit_status`       TINYINT(1)  NOT NULL,
    `audit_ip`           VARCHAR(39) NOT NULL,
    `audit_key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`remote` (
    `id`           INT AUTO_INCREMENT,

    `created_by`   INT         NOT NULL,
    `created_date` TIMESTAMP   NOT NULL,
    `updated_by`   INT         NOT NULL,
    `updated_date` TIMESTAMP   NOT NULL,
    `deleted_by`   INT,
    `deleted_date` TIMESTAMP,

    `generator`    INT         NOT NULL,
    `command`      VARCHAR(64) NOT NULL,
    `ip`           VARCHAR(39) NOT NULL,
    `key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`created_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`updated_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`deleted_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`generator`) REFERENCES `freqline`.`generator` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`remote_audit` (
    `id`                 INT AUTO_INCREMENT,
    `action`             INT         NOT NULL,

    `audit_id`           INT         NOT NULL,
    `audit_created_by`   INT         NOT NULL,
    `audit_created_date` TIMESTAMP   NOT NULL,
    `audit_updated_by`   INT         NOT NULL,
    `audit_updated_date` TIMESTAMP   NOT NULL,
    `audit_deleted_by`   INT,
    `audit_deleted_date` TIMESTAMP,

    `audit_generator`    INT         NOT NULL,
    `audit_command`      VARCHAR(64) NOT NULL,
    `audit_ip`           VARCHAR(39) NOT NULL,
    `audit_key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`mic` (
    `id`           INT AUTO_INCREMENT,

    `created_by`   INT         NOT NULL,
    `created_date` TIMESTAMP   NOT NULL,
    `updated_by`   INT         NOT NULL,
    `updated_date` TIMESTAMP   NOT NULL,
    `deleted_by`   INT,
    `deleted_date` TIMESTAMP,

    `generator`    INT         NOT NULL,
    `decibel`      VARCHAR(64) NOT NULL,
    `timer`        INT,
    `ip`           VARCHAR(39) NOT NULL,
    `key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`created_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`updated_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`deleted_by`) REFERENCES `freqline`.`user` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (`generator`) REFERENCES `freqline`.`generator` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE `freqline`.`mic_audit` (
    `id`                 INT AUTO_INCREMENT,
    `action`             INT         NOT NULL,

    `audit_id`           INT         NOT NULL,
    `audit_created_by`   INT         NOT NULL,
    `audit_created_date` TIMESTAMP   NOT NULL,
    `audit_updated_by`   INT         NOT NULL,
    `audit_updated_date` TIMESTAMP   NOT NULL,
    `audit_deleted_by`   INT,
    `audit_deleted_date` TIMESTAMP,

    `audit_generator`    INT         NOT NULL,
    `audit_decibel`      VARCHAR(64) NOT NULL,
    `audit_timer`        INT,
    `audit_ip`           VARCHAR(39) NOT NULL,
    `audit_key_c`        VARCHAR(12) NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`action`) REFERENCES `freqline`.`audit_action` (`id`)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

ALTER TABLE `freqline`.`user`
    ADD FOREIGN KEY (`favorite_generator`) REFERENCES `freqline`.`generator` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE;

# -----------------------------------------------
#
# INSERT SYSTEM DATA
#
# -----------------------------------------------

INSERT INTO `freqline`.`audit_action` (`name`)
VALUES ('created'),
       ('updated'),
       ('deleted');

INSERT INTO `freqline`.`permission` (
  created_by, 
  created_date, 
  updated_by, 
  updated_date, 
  name, 
  string,
  description)
VALUES (1, NOW(), 1, NOW(), 'db', 'db', 'Database Permissions'),
       (1, NOW(), 1, NOW(), 'admin', 'admin', 'Administration Permissions'),
       (1, NOW(), 1, NOW(), 'user', 'user', 'Basic User Permissions');

INSERT INTO freqline.`group` (
  created_by, 
  created_date, 
  updated_by, 
  updated_date, 
  name,
  parent_group)
VALUES (1, NOW(), 1, NOW(), 'admin', null),
       (1, NOW(), 1, NOW(), 'user', null);

INSERT INTO freqline.group_permission (
  created_by, 
  created_date, 
  updated_by, 
  updated_date,
  permission, 
  `group`)
VALUES (1, NOW(), 1, NOW(), 2, 1),
       (1, NOW(), 1, NOW(), 3, 1),
       (1, NOW(), 1, NOW(), 3, 2);

INSERT INTO freqline.user (
  created_by, created_date, updated_by, updated_date, 
  username, 
  password,
  salt, 
  firstname, lastname, email, 
  favorite_generator)
VALUES (
  1, NOW(), 1, NOW(), 
  'admin',
  'fea8f928a64d883d28006bb0a8b8fb2973b7b867fafa8af4cff4524028979514',
  'OCxr[@fJfTHeQWXipgfqpgOgsXxfz`XA', 
  'Admin', 'Database', 'admin@db', 
  NULL),
  (1, NOW(), 1, NOW(), 
  'user',
  '50144087fc14bf2e4c19fabaa479eff0859253b9eb29eb3c1a633c3d415763e2',
  'QhzykCqQcAOWXKzpjqBTyoE`gtbVrxI[', 
  'User', 'Database', 'user@db', 
  NULL);

INSERT INTO freqline.generator(
  created_by, 
  created_date, 
  updated_by,
  updated_date,
  name,
  frequence,
  status,
  ip,
  key_c) 
VALUES (1, NOW(), 1, NOW(), 'Generator', 10000, 1, '127.0.0.1', 'AAAA');

UPDATE freqline.user SET favorite_generator=1;

INSERT INTO freqline.mic(
  created_by, 
  created_date, 
  updated_by,
  updated_date,
  generator,
  decibel,
  timer,
  ip,
  key_c)
VALUES (1, NOW(), 1, NOW(), 1, 80, 30000, '127.0.0.1', 'AAAA');

INSERT INTO freqline.remote(
  created_by, 
  created_date, 
  updated_by,
  updated_date,
  generator,
  command,
  ip,
  key_c)
VALUES (1, NOW(), 1, NOW(), 1, 'AAAA', '127.0.0.1', 'AAAA');

INSERT INTO freqline.user_group (
  created_by, 
  created_date, 
  updated_by, 
  updated_date, 
  user, 
  `group`)
VALUES (1, NOW(), 1, NOW(), 1, 1),
       (1, NOW(), 1, NOW(), 2, 2);
