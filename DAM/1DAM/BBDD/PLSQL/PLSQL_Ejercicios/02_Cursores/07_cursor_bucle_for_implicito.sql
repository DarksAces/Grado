-- 9. Cursor con bucle FOR (implícito)
DECLARE 
    CURSOR c_dept_cursor IS
    SELECT * FROM departments
    WHERE location_id = 1700;

BEGIN
    FOR v_dept IN c_dept_cursor LOOP
        DBMS_OUTPUT.PUT_LINE(v_dept.department_id ||' '|| v_dept.department_name);
    END LOOP;
END;
/
