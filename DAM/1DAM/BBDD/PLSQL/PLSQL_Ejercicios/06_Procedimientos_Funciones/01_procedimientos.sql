-- Procedimientos
CREATE OR REPLACE PROCEDURE add_dept(
    p_id NUMBER, 
    p_name VARCHAR2
) IS 
BEGIN 
    INSERT INTO departments VALUES(p_id, p_name); 
    COMMIT; 
EXCEPTION 
    WHEN OTHERS THEN 
        DBMS_OUTPUT.PUT_LINE(SQLERRM); 
END;
/

-- Uso del procedimiento:
BEGIN 
    add_dept(290, 'I+D'); 
END;
/
