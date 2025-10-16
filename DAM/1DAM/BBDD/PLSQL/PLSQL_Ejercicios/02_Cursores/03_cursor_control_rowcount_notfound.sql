-- Cursor con Control de %ROWCOUNT y %NOTFOUND
DECLARE 
    CURSOR c IS 
        SELECT * FROM departments 
        WHERE location_id = 1700; 
    v_dep c%ROWTYPE; 
BEGIN 
    OPEN c; 
    LOOP 
        FETCH c INTO v_dep; 
        EXIT WHEN c%NOTFOUND OR c%ROWCOUNT > 10; 
        DBMS_OUTPUT.PUT_LINE(v_dep.department_name); 
    END LOOP; 
    CLOSE c; 
END;
/
