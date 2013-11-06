@echo off

set host=localhost
set port=5432
set db_superuser=postgres
set db_user=TAIUSER
set db_name=taidb
set fill_db_script_name=fill_db.sql

psql -h %host% -p %port% -U %db_user% -W %db_name% < %fill_db_script_name%