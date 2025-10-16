-- 10. Bucle FOR con cursor implícito en línea (sin declarar cursor)
DECLARE 
BEGIN
    FOR v_dept IN (
        SELECT * FROM departments 
        WHERE location_id = 1700
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(v_dept.department_id ||' '|| v_dept.department_name);
    END LOOP;
END;
/
