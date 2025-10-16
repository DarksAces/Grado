-- 12. Cursor con parámetro
DECLARE
    CURSOR c_dept_por_loc(p_location_id departments.location_id%TYPE) IS
        SELECT department_id, department_name
        FROM departments
        WHERE location_id = p_location_id;

    v_dept c_dept_por_loc%ROWTYPE;
BEGIN
    OPEN c_dept_por_loc(1700);  -- Llamada con parámetro

    LOOP
        FETCH c_dept_por_loc INTO v_dept;
        EXIT WHEN c_dept_por_loc%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_dept.department_id || ' - ' || v_dept.department_name);
    END LOOP;

    CLOSE c_dept_por_loc;
END;
/
