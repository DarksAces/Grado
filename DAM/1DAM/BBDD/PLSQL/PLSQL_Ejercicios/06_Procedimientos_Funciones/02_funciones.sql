-- Funciones
CREATE OR REPLACE FUNCTION impuesto(
    p_value NUMBER
) RETURN NUMBER IS 
    v_irpf NUMBER; 
BEGIN 
    v_irpf := p_value * 0.15; 
    RETURN v_irpf; 
END;
/

-- Uso de la función en un bloque PL/SQL:
BEGIN 
    DBMS_OUTPUT.PUT_LINE(impuesto(10000)); 
END;
/

-- Uso de la función en SQL:
-- SELECT salary, impuesto(salary) AS irpf FROM employees;
