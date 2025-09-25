-- 1. Bucle FOR con paso personalizado
DECLARE
    v_paso NUMBER(2);
    v_ini INT;
    v_fin INT;
    acaba INT;
BEGIN
    v_paso := &Pon_el_paso;
    v_ini := &Pon_el_inicio;
    v_fin := &Pon_el_fin;
    acaba := (v_fin - v_ini)/v_paso;

    FOR i IN 1 .. acaba + 1 LOOP
        DBMS_OUTPUT.PUT_LINE(v_ini);
        v_ini := v_paso + v_ini;
    END LOOP;
END;
/
