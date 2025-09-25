-- 2. Manejo de errores en consultas con EXCEPTION
DECLARE
    v_fname employees.first_name%TYPE;
    v_lname employees.last_name%TYPE;

    e_dato_repetido EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_dato_repetido, -01422);
BEGIN
    v_fname := INITCAP('&Pon_nombre');

    SELECT last_name INTO v_lname
    FROM employees
    WHERE first_name = v_fname;

    DBMS_OUTPUT.PUT_LINE('El apellido de ' || v_fname || ' es ' || v_lname);

EXCEPTION
    WHEN no_data_found THEN
        DBMS_OUTPUT.PUT_LINE('No se ha encontrado ningún nombre');
    WHEN e_dato_repetido THEN
        DBMS_OUTPUT.PUT_LINE('Error: con este nombre hay más de un empleado.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error inesperado.');
        DBMS_OUTPUT.PUT_LINE('Código: ' || SQLCODE);
        DBMS_OUTPUT.PUT_LINE('Mensaje: ' || SQLERRM);
END;
/
