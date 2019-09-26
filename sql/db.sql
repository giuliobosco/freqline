# Author: giuliobosco
# Version: 0.1.1 (2019-09-20 - 2019-09-20)
# Base from: https://github.com/bomaidea/dbe


# -----------------------------------------------
#
# CREATE DATABASE
#
# -----------------------------------------------

CREATE DATABASE freqline;

# -----------------------------------------------
#
# CREATE TABLES
#
# -----------------------------------------------

CREATE TABLE freqline.audit_action (
    id   INT AUTO_INCREMENT,

    name VARCHAR(10) UNIQUE NOT NULL,

    PRIMARY KEY (id)
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.users (
    id                 INT AUTO_INCREMENT,

    created_by         INT                 NOT NULL,
    created_date       TIMESTAMP           NOT NULL,
    updated_by         INT                 NOT NULL,
    updated_date       TIMESTAMP           NOT NULL,
    deleted_by         INT,
    deleted_date       TIMESTAMP,

    username           VARCHAR(32) UNIQUE  NOT NULL,
    password           CHAR(64)            NOT NULL,
    salt               CHAR(32)            NOT NULL,
    firstname          VARCHAR(64)         NOT NULL,
    lastname           VARCHAR(64)         NOT NULL,
    email              VARCHAR(128) UNIQUE NOT NULL,
    favorite_generator INT,

    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (updated_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.users_audit (
    id                       INT AUTO_INCREMENT,
    action                   INT          NOT NULL,

    audit_id                 INT          NOT NULL,
    audit_created_by         INT          NOT NULL,
    audit_created_date       TIMESTAMP    NOT NULL,
    audit_updated_by         INT          NOT NULL,
    audit_updated_date       TIMESTAMP    NOT NULL,
    audit_deleted_by         INT,
    audit_deleted_date       TIMESTAMP,
    audit_favorite_generator INT,

    audit_username           VARCHAR(32)  NOT NULL,
    audit_password           CHAR(64)     NOT NULL,
    audit_salt               CHAR(32)     NOT NULL,
    audit_firstname          VARCHAR(64)  NOT NULL,
    audit_lastname           VARCHAR(64)  NOT NULL,
    audit_email              VARCHAR(128) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.permission (
    id           INT AUTO_INCREMENT,

    created_by   INT                NOT NULL,
    created_date TIMESTAMP          NOT NULL,
    updated_by   INT                NOT NULL,
    updated_date TIMESTAMP          NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    name         VARCHAR(64)        NOT NULL,
    string       VARCHAR(32) UNIQUE NOT NULL,
    description  VARCHAR(256),

    PRIMARY KEY (id)
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.permission_audit (
    id                 INT AUTO_INCREMENT,
    action             INT         NOT NULL,

    audit_id           INT         NOT NULL,
    audit_created_by   INT         NOT NULL,
    audit_created_date TIMESTAMP   NOT NULL,
    audit_updated_by   INT         NOT NULL,
    audit_updated_date TIMESTAMP   NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_name         VARCHAR(64) NOT NULL,
    audit_string       VARCHAR(32) NOT NULL,
    audit_description  VARCHAR(256),

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.groups (
    id           INT AUTO_INCREMENT,

    created_by   INT                NOT NULL,
    created_date TIMESTAMP          NOT NULL,
    updated_by   INT                NOT NULL,
    updated_date TIMESTAMP          NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    name         VARCHAR(64) UNIQUE NOT NULL,
    parent_group INT,

    PRIMARY KEY (id),
    FOREIGN KEY (parent_group) REFERENCES freqline.groups (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.groups_audit (
    id                 INT AUTO_INCREMENT,
    action             INT         NOT NULL,

    audit_id           INT         NOT NULL,
    audit_created_by   INT         NOT NULL,
    audit_created_date TIMESTAMP   NOT NULL,
    audit_updated_by   INT         NOT NULL,
    audit_updated_date TIMESTAMP   NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_name         VARCHAR(64) NOT NULL,
    audit_parent_group INT,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.groups_permission (
    id               INT AUTO_INCREMENT,

    created_by       INT       NOT NULL,
    created_date     TIMESTAMP NOT NULL,
    updated_by       INT       NOT NULL,
    updated_date     TIMESTAMP NOT NULL,
    deleted_by       INT,
    deleted_date     TIMESTAMP,

    permission       INT       NOT NULL,
    permission_group INT       NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (permission) REFERENCES freqline.permission (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (permission_group) REFERENCES freqline.groups (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.groups_permission_audit (
    id                     INT AUTO_INCREMENT,
    action                 INT       NOT NULL,

    audit_id               INT       NOT NULL,
    audit_created_by       INT       NOT NULL,
    audit_created_date     TIMESTAMP NOT NULL,
    audit_updated_by       INT       NOT NULL,
    audit_updated_date     TIMESTAMP NOT NULL,
    audit_deleted_by       INT,
    audit_deleted_date     TIMESTAMP,

    audit_permission       INT       NOT NULL,
    audit_permission_group INT       NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.users_groups (
    id           INT AUTO_INCREMENT,

    created_by   INT       NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_by   INT       NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    users        INT       NOT NULL,
    user_group   INT       NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (users) REFERENCES freqline.users (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (user_group) REFERENCES freqline.groups (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.users_groups_audit (
    id                 INT AUTO_INCREMENT,
    action             INT       NOT NULL,

    audit_id           INT       NOT NULL,
    audit_created_by   INT       NOT NULL,
    audit_created_date TIMESTAMP NOT NULL,
    audit_updated_by   INT       NOT NULL,
    audit_updated_date TIMESTAMP NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_users        INT       NOT NULL,
    audit_user_group   INT       NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.generator (
    id           INT AUTO_INCREMENT,

    created_by   INT         NOT NULL,
    created_date TIMESTAMP   NOT NULL,
    updated_by   INT         NOT NULL,
    updated_date TIMESTAMP   NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    name         VARCHAR(64) NOT NULL,
    frequence    INT         NOT NULL,
    status       TINYINT(1)  NOT NULL,
    ip           VARCHAR(39) NOT NULL,
    key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (updated_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.generator_audit (
    id                 INT AUTO_INCREMENT,
    action             INT         NOT NULL,

    audit_id           INT         NOT NULL,
    audit_created_by   INT         NOT NULL,
    audit_created_date TIMESTAMP   NOT NULL,
    audit_updated_by   INT         NOT NULL,
    audit_updated_date TIMESTAMP   NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_name         VARCHAR(64) NOT NULL,
    audit_frequence    INT         NOT NULL,
    audit_status       TINYINT(1)  NOT NULL,
    audit_ip           VARCHAR(39) NOT NULL,
    audit_key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.remote (
    id           INT AUTO_INCREMENT,

    created_by   INT         NOT NULL,
    created_date TIMESTAMP   NOT NULL,
    updated_by   INT         NOT NULL,
    updated_date TIMESTAMP   NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    generator    INT         NOT NULL,
    command      VARCHAR(64) NOT NULL,
    ip           VARCHAR(39) NOT NULL,
    key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (updated_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (generator) REFERENCES freqline.generator (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.remote_audit (
    id                 INT AUTO_INCREMENT,
    action             INT         NOT NULL,

    audit_id           INT         NOT NULL,
    audit_created_by   INT         NOT NULL,
    audit_created_date TIMESTAMP   NOT NULL,
    audit_updated_by   INT         NOT NULL,
    audit_updated_date TIMESTAMP   NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_generator    INT         NOT NULL,
    audit_command      VARCHAR(64) NOT NULL,
    audit_ip           VARCHAR(39) NOT NULL,
    audit_key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.mic (
    id           INT AUTO_INCREMENT,

    created_by   INT         NOT NULL,
    created_date TIMESTAMP   NOT NULL,
    updated_by   INT         NOT NULL,
    updated_date TIMESTAMP   NOT NULL,
    deleted_by   INT,
    deleted_date TIMESTAMP,

    generator    INT         NOT NULL,
    decibel      VARCHAR(64) NOT NULL,
    timer        TIMESTAMP,
    ip           VARCHAR(39) NOT NULL,
    key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (updated_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (deleted_by) REFERENCES freqline.users (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE,
    FOREIGN KEY (generator) REFERENCES freqline.generator (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

CREATE TABLE freqline.mic_audit (
    id                 INT AUTO_INCREMENT,
    action             INT         NOT NULL,

    audit_id           INT         NOT NULL,
    audit_created_by   INT         NOT NULL,
    audit_created_date TIMESTAMP   NOT NULL,
    audit_updated_by   INT         NOT NULL,
    audit_updated_date TIMESTAMP   NOT NULL,
    audit_deleted_by   INT,
    audit_deleted_date TIMESTAMP,

    audit_generator    INT         NOT NULL,
    audit_decibel      VARCHAR(64) NOT NULL,
    audit_timer        TIMESTAMP,
    audit_ip           VARCHAR(39) NOT NULL,
    audit_key_c        VARCHAR(12) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (action) REFERENCES freqline.audit_action (id)
        ON DELETE NO ACTION
        ON UPDATE CASCADE
);

# -----------------------------------------------
# -----------------------------------------------

ALTER TABLE freqline.users
    ADD FOREIGN KEY (favorite_generator) REFERENCES freqline.generator (id)
    ON DELETE NO ACTION
    ON UPDATE CASCADE;

# -----------------------------------------------
#
# INSERT SYSTEM DATA
#
# -----------------------------------------------

INSERT INTO freqline.audit_action (name)
VALUES ('created'),
       ('updated'),
       ('deleted');

INSERT INTO freqline.users (created_by,
                            created_date,
                            updated_by,
                            updated_date,
                            username,
                            password,
                            salt,
                            firstname,
                            lastname,
                            email)
VALUES (1, NOW(), 1, NOW(), 'db_core', 'pw', 'salt', 'DB', 'CORE', 'core@db');
