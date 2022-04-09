--UPDATE SEQUENCE. USE IT TO UPDATE ID TO THE BIGEST ID AVAILABLE AFTER IMPORT DUMP
do $$
    declare max_id int;
    begin
        select max(id) from pre into max_id;
        if (max_id is not null) then
            PERFORM setval('pre_id_seq', max_id);
        end if;
    end;
$$ language plpgsql;
