-- 13. Uso de CURRENT OF para actualizar registros desde un cursor
DECLARE
    CURSOR c_emps IS
        SELECT employee_id, salary
        FROM employees
        WHERE department_id = 50
        FOR UPDATE;

    v_emp c_emps%ROWTYPE;
BEGIN
    FOR v_emp IN c_emps LOOP
        -- Aumentar el salario un 10%
        UPDATE employees
        SET salary = v_emp.salary * 1.10
        WHERE CURRENT OF c_emps;
    END LOOP;

    COMMIT;
END;
/
