-- Trigger Simple que genera error por tabla mutante
CREATE OR REPLACE TRIGGER check_salary 
BEFORE INSERT OR UPDATE OF salary ON employees 
FOR EACH ROW 
WHEN (NEW.job_id <> 'AD_PRES') 
BEGIN 
    -- Error común: tabla mutante
    -- Aquí se intentaría verificar algo con la misma tabla
    -- lo que generaría un error de mutating table
    NULL;
END;
/
