@echo off

set host=localhost
set port=5432
set db_superuser=postgres
set db_user=TAIUSER
set db_name=taidb
set clear_db_script_name=clear_db.sql

psql -h %host% -p %port% -U %db_user% -W %db_name% < %clear_db_script_name%