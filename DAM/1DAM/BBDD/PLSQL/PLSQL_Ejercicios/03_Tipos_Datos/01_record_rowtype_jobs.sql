-- 3. Uso de RECORD y %ROWTYPE con tabla jobs
DECLARE
    TYPE j_rec IS RECORD (
        id      jobs.job_id%TYPE,
        title   jobs.job_title%TYPE,
        minimo  jobs.min_salary%TYPE,
        maximo  jobs.max_salary%TYPE
    );

    v_jobs j_rec;
    v_jobs1 jobs%ROWTYPE;

BEGIN
    SELECT * INTO v_jobs1
    FROM jobs
    WHERE job_id = 'RRHH';

    DBMS_OUTPUT.PUT_LINE(v_jobs1.job_id || ' ' || v_jobs1.job_title || ' ' || v_jobs1.min_salary || ' ' || v_jobs1.max_salary);
END;
/
