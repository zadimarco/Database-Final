-- The following variables will be parametrized in the final product
SET @newVersion = ':newVersion';
SET @app = ':app';
SET @platform = ':platform';

UPDATE appplatform
SET 
	version = @newVersion
WHERE
	appName LIKE @app AND
    platformName LIKE @platform;








