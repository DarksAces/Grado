-- Cursor Explícito Estático
DECLARE 
    CURSOR c_emp IS 
        SELECT * FROM employees;
BEGIN 
    FOR r IN c_emp LOOP 
        DBMS_OUTPUT.PUT_LINE(r.employee_id); 
    END LOOP; 
END;
/
