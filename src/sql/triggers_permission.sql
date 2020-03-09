DELIMITER //

CREATE TRIGGER `freqline`.`insert_permission_audit`
  AFTER INSERT
  ON `freqline`.`permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'created'
        INTO `v_action`;

    INSERT INTO `freqline`.`permission_audit` (`action`,
                                      `audit_id`,
                                      `audit_created_by`,
                                      `audit_created_date`,
                                      `audit_updated_by`,
                                      `audit_updated_date`,
                                      `audit_deleted_by`,
                                      `audit_deleted_date`,
                                      `audit_name`,
                                      `audit_string`,
                                      `audit_description`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`name`,
            NEW.`string`,
            NEW.`description`);
  END;
//

DELIMITER //

CREATE TRIGGER `freqline`.`update_permission_audit`
  AFTER UPDATE
  ON `freqline`.`permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'updated'
        INTO `v_action`;

    INSERT INTO `freqline`.`permission_audit` (`action`,
                                      `audit_id`,
                                      `audit_created_by`,
                                      `audit_created_date`,
                                      `audit_updated_by`,
                                      `audit_updated_date`,
                                      `audit_deleted_by`,
                                      `audit_deleted_date`,
                                      `audit_name`,
                                      `audit_string`,
                                      `audit_description`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`name`,
            NEW.`string`,
            NEW.`description`);
  END;
//

DELIMITER //

CREATE TRIGGER `freqline`.`delete_permission_audit`
  AFTER DELETE
  ON `freqline`.`permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'deleted'
        INTO `v_action`;

    INSERT INTO `freqline`.`permission_audit` (`action`,
                                      `audit_id`,
                                      `audit_created_by`,
                                      `audit_created_date`,
                                      `audit_updated_by`,
                                      `audit_updated_date`,
                                      `audit_deleted_by`,
                                      `audit_deleted_date`,
                                      `audit_name`,
                                      `audit_string`,
                                      `audit_description`)
    VALUES (`v_action`,
            OLD.`id`,
            OLD.`created_by`,
            OLD.`created_date`,
            OLD.`updated_by`,
            OLD.`updated_date`,
            OLD.`deleted_by`,
            OLD.`deleted_date`,
            OLD.`name`,
            OLD.`string`,
            OLD.`description`);
  END;
//