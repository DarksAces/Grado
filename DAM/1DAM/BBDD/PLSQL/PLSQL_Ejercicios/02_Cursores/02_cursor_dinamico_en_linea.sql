-- Cursor Dinámico (en línea)
BEGIN 
    FOR r IN (SELECT * FROM employees) LOOP 
        DBMS_OUTPUT.PUT_LINE(r.employee_id); 
    END LOOP; 
END;
/
