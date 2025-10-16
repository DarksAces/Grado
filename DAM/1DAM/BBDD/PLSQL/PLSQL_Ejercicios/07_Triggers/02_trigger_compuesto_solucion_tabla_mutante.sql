-- Trigger Compuesto (soluciona el problema de tabla mutante)
CREATE OR REPLACE TRIGGER check_salary_comp 
FOR INSERT OR UPDATE OF salary ON employees 
WHEN (NEW.job_id <> 'AD_PRES') 
COMPOUND TRIGGER 
    -- Variables globales para el trigger
    
    -- BEFORE STATEMENT: recoger salarios por departamento
    BEFORE STATEMENT IS
    BEGIN
        NULL; -- Aquí se recogen datos globales
    END BEFORE STATEMENT;
    
    -- AFTER EACH ROW: comprobar límites
    AFTER EACH ROW IS
    BEGIN
        NULL; -- Aquí se procesan las filas
    END AFTER EACH ROW;
    
    -- Otras secciones posibles:
    -- BEFORE EACH ROW
    -- AFTER STATEMENT
END check_salary_comp;
/
