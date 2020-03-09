DELIMITER //

CREATE TRIGGER `freqline`.`insert_remote_audit`
    AFTER INSERT
    ON `freqline`.`remote`
    FOR EACH ROW
    BEGIN
        DECLARE `v_action` INT;

        SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'created'
            INTO `v_action`;

        INSERT INTO `freqline`.`remote_audit` (`action`,
                                        `audit_id`,
                                        `audit_created_by`,
                                        `audit_created_date`,
                                        `audit_updated_by`,
                                        `audit_updated_date`,
                                        `audit_deleted_by`,
                                        `audit_deleted_date`,
                                        `audit_generator`,
                                        `audit_command`,
                                        `audit_ip`,
                                        `audit_key_c`)
        VALUES (`v_action`,
                NEW.`id`,
                NEW.`created_by`,
                NEW.`created_date`,
                NEW.`updated_by`,
                NEW.`updated_date`,
                NEW.`deleted_by`,
                NEW.`deleted_date`,
                NEW.`generator`,
                NEW.`command`,
                NEW.`ip`,
                NEW.`key_c`);
    END;
//

DELIMITER //

CREATE TRIGGER `freqline`.`update_remote_audit`
    AFTER UPDATE
    ON `freqline`.`remote`
    FOR EACH ROW
    BEGIN
        DECLARE `v_action` INT;

        SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'updated'
            INTO `v_action`;

        INSERT INTO `freqline`.`remote_audit` (`action`,
                                        `audit_id`,
                                        `audit_created_by`,
                                        `audit_created_date`,
                                        `audit_updated_by`,
                                        `audit_updated_date`,
                                        `audit_deleted_by`,
                                        `audit_deleted_date`,
                                        `audit_generator`,
                                        `audit_command`,
                                        `audit_ip`,
                                        `audit_key_c`)
        VALUES (`v_action`,
                NEW.`id`,
                NEW.`created_by`,
                NEW.`created_date`,
                NEW.`updated_by`,
                NEW.`updated_date`,
                NEW.`deleted_by`,
                NEW.`deleted_date`,
                NEW.`generator`,
                NEW.`command`,
                NEW.`ip`,
                NEW.`key_c`);
    END;
//

DELIMITER //

CREATE TRIGGER `freqline`.`delete_remote_audit`
    AFTER DELETE
    ON `freqline`.`remote`
    FOR EACH ROW
    BEGIN
        DECLARE `v_action` INT;

        SELECT `id` FROM `freqline`.`audit_action` WHERE `audit_action`.`name` = 'deleted'
            INTO `v_action`;

        INSERT INTO `freqline`.`remote_audit` (`action`,
                                        `audit_id`,
                                        `audit_created_by`,
                                        `audit_created_date`,
                                        `audit_updated_by`,
                                        `audit_updated_date`,
                                        `audit_deleted_by`,
                                        `audit_deleted_date`,
                                        `audit_generator`,
                                        `audit_command`,
                                        `audit_ip`,
                                        `audit_key_c`)
        VALUES (`v_action`,
                OLD.`id`,
                OLD.`created_by`,
                OLD.`created_date`,
                OLD.`updated_by`,
                OLD.`updated_date`,
                OLD.`deleted_by`,
                OLD.`deleted_date`,
                OLD.`generator`,
                OLD.`command`,
                OLD.`ip`,
                OLD.`key_c`);
    END;
//