-- All of the following variables will be parametrized in the actual product
SET @email = ':email';
SET @firstName = ':firstName';
SET @lastName = ':lastName';
SET @country = ':country';
SET @password = ':password'


INSERT INTO YAUser
	(email, firstName, lastName, country, passw)
VALUES
	(@email, @firstName, @lastName, @country, @password);


