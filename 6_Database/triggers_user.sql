/* on user insert, create audit */
DELIMITER //

CREATE TRIGGER `freqline`.`insert_user_audit`
  AFTER INSERT
  ON `freqline`.`user`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'created'
        INTO `v_action`;

    INSERT INTO `freqline`.`user_audit` (`action`,
                                 `audit_id`,
                                 `audit_created_by`,
                                 `audit_created_date`,
                                 `audit_updated_by`,
                                 `audit_updated_date`,
                                 `audit_deleted_by`,
                                 `audit_deleted_date`,
                                 `audit_username`,
                                 `audit_password`,
                                 `audit_salt`,
                                 `audit_firstname`,
                                 `audit_lastname`,
                                 `audit_email`,
                                 `audit_favorite_generator`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`username`,
            NEW.`password`,
            NEW.`salt`,
            NEW.`firstname`,
            NEW.`lastname`,
            NEW.`email`,
            NEW.`favorite_generator`);
  END;
//

/* on user update, create audit */
DELIMITER //

CREATE TRIGGER `freqline`.`update_user_audit`
  AFTER UPDATE
  ON `freqline`.`user`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'updated'
        INTO `v_action`;

    INSERT INTO `freqline`.`user_audit` (`action`,
                                 `audit_id`,
                                 `audit_created_by`,
                                 `audit_created_date`,
                                 `audit_updated_by`,
                                 `audit_updated_date`,
                                 `audit_deleted_by`,
                                 `audit_deleted_date`,
                                 `audit_username`,
                                 `audit_password`,
                                 `audit_salt`,
                                 `audit_firstname`,
                                 `audit_lastname`,
                                 `audit_email`,
                                 `audit_favorite_generator`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`username`,
            NEW.`password`,
            NEW.`salt`,
            NEW.`firstname`,
            NEW.`lastname`,
            NEW.`email`,
            NEW.`favorite_generator`);
  END;
//

/* on user delete, create audit */
DELIMITER //

CREATE TRIGGER `freqline`.`delete_user_audit`
  AFTER DELETE
  ON `freqline`.`user`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'deleted'
        INTO `v_action`;

    INSERT INTO `freqline`.`user_audit` (`action`,
                                 `audit_id`,
                                 `audit_created_by`,
                                 `audit_created_date`,
                                 `audit_updated_by`,
                                 `audit_updated_date`,
                                 `audit_deleted_by`,
                                 `audit_deleted_date`,
                                 `audit_username`,
                                 `audit_password`,
                                 `audit_salt`,
                                 `audit_firstname`,
                                 `audit_lastname`,
                                 `audit_email`,
                                 `audit_favorite_generator`)
    VALUES (`v_action`,
            OLD.`id`,
            OLD.`created_by`,
            OLD.`created_date`,
            OLD.`updated_by`,
            OLD.`updated_date`,
            OLD.`deleted_by`,
            OLD.`deleted_date`,
            OLD.`username`,
            OLD.`password`,
            OLD.`salt`,
            OLD.`firstname`,
            OLD.`lastname`,
            OLD.`email`,
            OLD.`favorite_generator`);
  END;
//
