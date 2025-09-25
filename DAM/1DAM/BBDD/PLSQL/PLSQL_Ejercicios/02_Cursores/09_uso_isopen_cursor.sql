-- 11. Uso de %ISOPEN para comprobar si un cursor está abierto
DECLARE
    CURSOR c_dept IS
        SELECT department_id, department_name 
        FROM departments 
        WHERE location_id = 1700;

    v_dept c_dept%ROWTYPE;
BEGIN
    -- Verificar si el cursor está abierto antes de abrirlo
    IF NOT c_dept%ISOPEN THEN
        OPEN c_dept;
    END IF;

    -- Recuperar datos del cursor
    LOOP
        FETCH c_dept INTO v_dept;
        EXIT WHEN c_dept%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(v_dept.department_id || ' - ' || v_dept.department_name);
    END LOOP;

    -- Verificar si el cursor sigue abierto antes de cerrarlo
    IF c_dept%ISOPEN THEN
        CLOSE c_dept;
    END IF;
END;
/
