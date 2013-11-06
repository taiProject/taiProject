INSERT INTO ROLES(name)
	VALUES ('ADMIN_ROLE');
INSERT INTO ROLES(name)
	VALUES ('USER_ROLE');
	
INSERT INTO USERS(login, password, role_id)
	VALUES ('admin', '123', 1) 