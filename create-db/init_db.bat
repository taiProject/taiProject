@echo off

:: Notes:
:: db_superuser has to be user with rights to create new databases, after installing Postgres
:: there is a default user available that you can use:
::   user: postgres
::   pass: postgres
::

set host=localhost
set port=5432
set db_superuser=postgres
set db_user=TAIUSER
set db_name=taidb
set create_db_script_name=create_db.sql

echo Dropping existing database %db_name% (enter superuser password)
dropdb -h %host% -p %port% -U %db_superuser% -W --if-exists %db_name%

echo Dropping existing user %db_user% (enter superuser password)
dropuser -h %host% -p %port% -U %db_superuser% -W --if-exists %db_user%

echo Creating user %db_user% (enter %db_user% password twice, then confirm with superuser password)
createuser -h %host% -p %port% -U %db_superuser% -W --encrypted --pwprompt %db_user%

echo Creating database %db_name% (enter superuser password)
createdb -h %host% -p %port% -U %db_superuser% -W -E UTF-8 -l C -O %db_user% -T template0 %db_name%

echo Adding PL/SQL support (enter %db_user% password)
createlang -h %host% -p %port% -U %db_user% -W -d %db_name% plpgsql

echo Updating database schema (enter %db_user% password)
psql -h %host% -p %port% -U %db_user% -W %db_name% < %create_db_script_name% > NUL

echo Done.