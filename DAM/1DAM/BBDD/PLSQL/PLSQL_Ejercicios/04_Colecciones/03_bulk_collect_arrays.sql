-- BULK COLLECT (alta eficiencia para arrays)
DECLARE 
    TYPE EmpArray IS TABLE OF employees%ROWTYPE 
        INDEX BY PLS_INTEGER; 
    v_emps EmpArray; 
BEGIN 
    SELECT * BULK COLLECT INTO v_emps 
    FROM employees; 
    
    FOR i IN 1..v_emps.COUNT LOOP 
        DBMS_OUTPUT.PUT_LINE(v_emps(i).first_name); 
    END LOOP; 
END;
/
