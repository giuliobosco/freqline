/* on group permission insert, create audit */
DELIMITER //

CREATE TRIGGER `freqline`.`insert_group_permission_audit`
  AFTER INSERT
  ON `freqline`.`group_permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'created'
        INTO `v_action`;

    INSERT INTO `freqline`.`group_permission_audit` (`action`,
                                             `audit_id`,
                                             `audit_created_by`,
                                             `audit_created_date`,
                                             `audit_updated_by`,
                                             `audit_updated_date`,
                                             `audit_deleted_by`,
                                             `audit_deleted_date`,
                                             `audit_permission`,
                                             `audit_group`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`group`,
            NEW.`permission`);
  END;
//

/* on group permission update, create audit */
DELIMITER //

CREATE TRIGGER `freqline`.`update_group_permission_audit`
  AFTER UPDATE
  ON `freqline`.`group_permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'updated'
        INTO `v_action`;

    INSERT INTO `freqline`.`group_permission_audit` (`action`,
                                             `audit_id`,
                                             `audit_created_by`,
                                             `audit_created_date`,
                                             `audit_updated_by`,
                                             `audit_updated_date`,
                                             `audit_deleted_by`,
                                             `audit_deleted_date`,
                                             `audit_permission`,
                                             `audit_group`)
    VALUES (`v_action`,
            NEW.`id`,
            NEW.`created_by`,
            NEW.`created_date`,
            NEW.`updated_by`,
            NEW.`updated_date`,
            NEW.`deleted_by`,
            NEW.`deleted_date`,
            NEW.`group`,
            NEW.`permission`);
  END;
//

/* on group permission delete, create audit audit */
DELIMITER //

CREATE TRIGGER `freqline`.`delete_group_permission_audit`
  AFTER DELETE
  ON `freqline`.`group_permission`
  FOR EACH ROW
  BEGIN
    DECLARE `v_action` INT;

    SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'deleted'
        INTO `v_action`;

    INSERT INTO `freqline`.`group_permission_audit` (`action`,
                                             `audit_id`,
                                             `audit_created_by`,
                                             `audit_created_date`,
                                             `audit_updated_by`,
                                             `audit_updated_date`,
                                             `audit_deleted_by`,
                                             `audit_deleted_date`,
                                             `audit_permission`,
                                             `audit_group`)
    VALUES (`v_action`,
            OLD.`id`,
            OLD.`created_by`,
            OLD.`created_date`,
            OLD.`updated_by`,
            OLD.`updated_date`,
            OLD.`deleted_by`,
            OLD.`deleted_date`,
            OLD.`group`,
            OLD.`permission`);
  END;
//