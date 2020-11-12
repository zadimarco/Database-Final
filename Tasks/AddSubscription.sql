-- It is assumed that the user identification will be the email, as that is much more user friendly than the userID
-- The following variables will be parametrized
SET @email = ':email';
SET @app = ':app';
SET @cost = :cost;
SET @expirationDate = ':expirationDate';


INSERT INTO subscription
VALUES
	((SELECT y.id FROM yauser y WHERE y.email = @email), 
     @app, @cost, @expirationDate);
