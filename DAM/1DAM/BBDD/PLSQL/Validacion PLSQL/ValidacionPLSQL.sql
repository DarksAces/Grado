-------------------------
--Ejercicio 1
-------------------------
CREATE OR REPLACE PROCEDURE actualiza_job_history(
    p_id_empleado     NUMBER,
    p_id_cargo        VARCHAR2,
    p_id_departamento NUMBER
) IS
    v_start_date  DATE;
    v_end_date    DATE := SYSDATE -1; -- Fecha de fin: día anterior al sistema
    v_count       NUMBER;
    v_hire_date   DATE;
BEGIN
    -- Verificar si ya existe historial para el empleado
    SELECT COUNT(*)
    INTO v_count
    FROM job_history
    WHERE employee_id = p_id_empleado;

    IF v_count = 0 THEN
        -- Primera inserción: usar hire_date como start_date
        SELECT hire_date
        INTO v_hire_date
        FROM employees
        WHERE employee_id = p_id_empleado;

        v_start_date := v_hire_date;
    ELSE
        -- Inserciones posteriores: buscar la última end_date + 1
        SELECT MAX(end_date) + 1
        INTO v_start_date
        FROM job_history
        WHERE employee_id = p_id_empleado;
    END IF;

    -- Insertar nuevo historial
    INSERT INTO job_history (
        employee_id,
        job_id,
        department_id,
        start_date,
        end_date
    ) VALUES (
        p_id_empleado,
        p_id_cargo,
        p_id_departamento,
        v_start_date,
        v_end_date
    );

    COMMIT; -- Confirmar los cambios

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: valor duplicado.');
        ROLLBACK; -- Revertir cambios en caso de error
    WHEN OTHERS THEN
        IF SQLCODE = -1400 THEN
            DBMS_OUTPUT.PUT_LINE('Error: no se permiten valores NULL en campos obligatorios.');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Error inesperado: ' || SQLERRM);
        END IF;
        ROLLBACK; -- Revertir cambios en caso de otro error
END;
/

-------------------------
--Ejercicio 2
-------------------------

DECLARE
    v_id_empleado     employees.employee_id%TYPE;
    v_id_cargo        jobs.job_id%TYPE;
    v_id_departamento departments.department_id%TYPE;
BEGIN
    -- Solicitar valores al usuario
    v_id_empleado := &id_empleado;
    v_id_cargo := '&id_cargo';
    v_id_departamento := &id_departamento;

    -- Llamada al procedimiento
    actualiza_job_history(v_id_empleado, v_id_cargo, v_id_departamento);

    DBMS_OUTPUT.PUT_LINE('Historial actualizado correctamente.');

EXCEPTION
    WHEN OTHERS THEN
        -- Si ocurre un error, revertimos los cambios
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Se ha producido un error:');
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK);
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
END;
/




-------------------------
--Ejercicio 3
-------------------------
BEGIN
    -- Desactivar el trigger antes de hacer actualizaciones
    EXECUTE IMMEDIATE 'ALTER TRIGGER update_job_history DISABLE';
END;
/

DECLARE
    CURSOR c_empleados IS
        SELECT employee_id, job_id, department_id, salary, hire_date
        FROM employees;
    
    v_id_empleado     employees.employee_id%TYPE;
    v_id_cargo        jobs.job_id%TYPE;
    v_id_departamento departments.department_id%TYPE;
    v_salary          employees.salary%TYPE;
    v_hire_date       employees.hire_date%TYPE;
BEGIN
    FOR r_empleado IN c_empleados LOOP
        v_id_empleado := r_empleado.employee_id;
        v_id_cargo := r_empleado.job_id;
        v_id_departamento := r_empleado.department_id;
        v_salary := r_empleado.salary;
        v_hire_date := r_empleado.hire_date;

        -- Cambiar de departamento si es necesario
        IF v_id_departamento = 50 AND TO_CHAR(v_hire_date, 'YYYY') < '1997' THEN
            v_id_departamento := 160;  
        END IF;

        -- Cambiar de cargo si es necesario
        IF v_id_cargo = 'SA_REP' AND v_salary >= 3200 THEN
            v_id_cargo := 'SH_CLERK';
        END IF;

        -- Actualizar el historial del empleado
        actualiza_job_history(v_id_empleado, v_id_cargo, v_id_departamento);
    END LOOP;

    -- Confirmar cambios
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Actualizaciones completadas.');

EXCEPTION
    WHEN OTHERS THEN
        -- Revertir cambios si ocurre un error
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Se ha producido un error:');
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK);
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE);
END;
/

BEGIN
    -- Activar el trigger después de las actualizaciones
    EXECUTE IMMEDIATE 'ALTER TRIGGER update_job_history ENABLE';
END;
/

