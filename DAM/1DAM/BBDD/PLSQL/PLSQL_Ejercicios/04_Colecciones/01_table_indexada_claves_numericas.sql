-- 4. Uso de TABLE indexada con claves numéricas
DECLARE
    TYPE nombre_array IS TABLE OF VARCHAR2(25) INDEX BY PLS_INTEGER;
    v_nombre nombre_array;
BEGIN
    v_nombre(1) := 'Tommy';
    v_nombre(50) := 'Eva';
    v_nombre(-7) := 'Juan';
    v_nombre(25):= 'Marta';
    DBMS_OUTPUT.PUT_LINE(v_nombre.prior(1));
END;
/
