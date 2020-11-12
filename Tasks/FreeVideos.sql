-- These commands must be run separately for this query to output 
-- For some reason the SET command denies any output from the SELECT statement below
-- This will not be an issue in the final product, as this variable will be parametrized
SET @platform = ':platform' COLLATE utf8mb4_general_ci;



SELECT DISTINCT v.title
FROM video v
INNER JOIN app a ON v.host = a.name
INNER JOIN appplatform ap ON a.name = ap.appName
WHERE ap.platformName = @platform AND
	v.isFree = True;
