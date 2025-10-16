-- 5. Uso de TABLE indexada por texto con %ROWTYPE
DECLARE
    TYPE nombre_array IS TABLE OF jobs%ROWTYPE INDEX BY VARCHAR2(3);
    v_nombre nombre_array;
BEGIN
    v_nombre('I+D').job_id := 'I+D';
    v_nombre('I+D').job_title := 'Investigacion';
    v_nombre('I+D').min_salary := 2000;
    v_nombre('I+D').max_salary := 15000;

    DBMS_OUTPUT.PUT_LINE(v_nombre('I+D').job_title || ' su sueldo máximo es ' || v_nombre('I+D').max_salary );
END;
/
