-- @country will be parametrized


# SET @country = ':country' COLLATE utf8mb4_general_ci;

SELECT a.name AS App, CONCAT("$",SUM(s.cost)) AS revenue
FROM app a
INNER JOIN subscription s ON s.appName = a.name
INNER JOIN yauser u ON u.id = s.userID
WHERE @country = u.country
GROUP BY a.name
ORDER BY revenue