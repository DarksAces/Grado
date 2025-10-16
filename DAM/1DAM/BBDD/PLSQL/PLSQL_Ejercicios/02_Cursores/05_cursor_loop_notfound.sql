-- 7. Cursor con LOOP hasta NOTFOUND
DECLARE 
    CURSOR c_dept_cursor IS
    SELECT * FROM departments
    WHERE location_id = 1700;
    v_dept c_dept_cursor%ROWTYPE;
BEGIN
    OPEN c_dept_cursor;
    LOOP
        FETCH c_dept_cursor INTO v_dept;
        EXIT WHEN c_dept_cursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_dept.department_id ||' '|| v_dept.department_name);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(c_dept_cursor%ROWCOUNT ||' filas ');
    CLOSE c_dept_cursor;
END;
/
