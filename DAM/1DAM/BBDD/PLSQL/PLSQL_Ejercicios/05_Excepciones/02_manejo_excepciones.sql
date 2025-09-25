-- Manejo de Excepciones
BEGIN 
    -- Código principal
    NULL; -- Alguna operación que podría generar errores
EXCEPTION 
    WHEN NO_DATA_FOUND THEN 
        DBMS_OUTPUT.PUT_LINE('No encontrado'); 
    WHEN OTHERS THEN 
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK); 
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE); 
END;
/
