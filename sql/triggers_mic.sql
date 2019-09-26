DELIMITER //

CREATE TRIGGER freqline.insert_mic_audit
    AFTER INSERT
    ON freqline.mic
    FOR EACH ROW
    BEGIN
        DECLARE v_action INT;

        SELECT id FROM freqline.audit_action WHERE audit_action.name = 'created'
            INTO v_action;

        INSERT INTO freqline.mic_audit (action,
                                        audit_id,
                                        audit_created_by,
                                        audit_created_date,
                                        audit_updated_by,
                                        audit_updated_date,
                                        audit_deleted_by,
                                        audit_deleted_date,
                                        audit_generator,
                                        audit_timer,
                                        audit_decibel,
                                        audit_ip,
                                        audit_key_c)
        VALUES (v_action,
                NEW.id,
                NEW.created_by,
                NEW.created_date,
                NEW.updated_by,
                NEW.updated_date,
                NEW.deleted_by,
                NEW.deleted_date,
                NEW.generator,
                NEW.timer,
                NEW.decibel,
                NEW.ip,
                NEW.key_c);
    END;
//

DELIMITER //

CREATE TRIGGER freqline.update_mic_audit
    AFTER UPDATE
    ON freqline.mic
    FOR EACH ROW
    BEGIN
        DECLARE v_action INT;

        SELECT id FROM freqline.audit_action WHERE audit_action.name = 'updated'
            INTO v_action;

        INSERT INTO freqline.mic_audit (action,
                                        audit_id,
                                        audit_created_by,
                                        audit_created_date,
                                        audit_updated_by,
                                        audit_updated_date,
                                        audit_deleted_by,
                                        audit_deleted_date,
                                        audit_generator,
                                        audit_timer,
                                        audit_decibel,
                                        audit_ip,
                                        audit_key_c)
        VALUES (v_action,
                NEW.id,
                NEW.created_by,
                NEW.created_date,
                NEW.updated_by,
                NEW.updated_date,
                NEW.deleted_by,
                NEW.deleted_date,
                NEW.generator,
                NEW.timer,
                NEW.decibel,
                NEW.ip,
                NEW.key_c);
    END;
//

DELIMITER //

CREATE TRIGGER freqline.delete_mic_audit
    AFTER DELETE
    ON freqline.mic
    FOR EACH ROW
    BEGIN
        DECLARE v_action INT;

        SELECT id FROM freqline.audit_action WHERE audit_action.name = 'deleted'
            INTO v_action;

        INSERT INTO freqline.mic_audit (action,
                                        audit_id,
                                        audit_created_by,
                                        audit_created_date,
                                        audit_updated_by,
                                        audit_updated_date,
                                        audit_deleted_by,
                                        audit_deleted_date,
                                        audit_generator,
                                        audit_timer,
                                        audit_decibel,
                                        audit_ip,
                                        audit_key_c)
        VALUES (v_action,
                OLD.id,
                OLD.created_by,
                OLD.created_date,
                OLD.updated_by,
                OLD.updated_date,
                OLD.deleted_by,
                OLD.deleted_date,
                OLD.generator,
                OLD.timer,
                OLD.decibel,
                OLD.ip,
                OLD.key_c);
    END;
//