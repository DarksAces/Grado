-- 6. Cursor simple – Trae una fila de departments
DECLARE 
    CURSOR c_dept_cursor IS
    SELECT * FROM departments
    WHERE location_id = 1700;
    v_dept c_dept_cursor%ROWTYPE;
BEGIN
    OPEN c_dept_cursor;
    FETCH c_dept_cursor INTO v_dept;
    DBMS_OUTPUT.PUT_LINE(v_dept.department_id ||' '|| v_dept.department_name);
    DBMS_OUTPUT.PUT_LINE(v_dept.department_id ||' '|| v_dept.department_name);
    CLOSE c_dept_cursor;
END;
/
