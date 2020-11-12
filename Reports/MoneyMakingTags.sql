-- Get the tags every app's paying customers are watching, the profits from each tag 
-- Returns the amount of money that each tag has made off of a user's subscription
-- Because a video can have multiple tags, the sum will not equate to the total profit, but it provides an estimate of the most profitable tags

SELECT tb1.name AS `App Name`, tb1.tagName AS `Tag`, CONCAT("$", FORMAT(SUM(tb1.cost * tb1.numTagWatched/userTotal.totalShows), 2)) AS `Profit From Tag`
FROM 
( 
  SELECT s.cost, a2.name, t.tagName, u.id, COUNT(DISTINCT w.videoID) AS numTagWatched
  FROM app a2
  INNER JOIN video v ON v.host = a2.name
  INNER JOIN watched w ON w.videoID = v.id
  INNER JOIN yauser u ON u.id = w.userID
  INNER JOIN subscription s ON s.userID = u.id AND s.appName = a2.name
  INNER JOIN tag t ON v.id = t.videoID
  WHERE NOT v.isFree
  GROUP BY u.id, a2.name, t.tagName
) tb1
INNER JOIN
(
  SELECT u2.id, s2.appName, COUNT(*) AS totalShows, u2.firstName, v2.title
  FROM yauser u2
  INNER JOIN subscription s2 ON u2.id = s2.userID
  INNER JOIN watched w2 ON u2.id = w2.userID
  INNER JOIN video v2 ON v2.id = w2.videoID AND v2.host = s2.appName
  WHERE NOT v2.isFree
  GROUP BY s2.appName, u2.id
) userTotal ON userTotal.appName = tb1.name AND userTotal.id = tb1.id
GROUP BY tb1.name, tb1.tagName
ORDER BY `Profit From Tag` ASC, `App Name`, `Tag`

-- -------------SCORING-------------
-- +1 Joined a lot of tables
-- +2 2 subqueries
-- +1 Aggregate Function
-- +1 Grouping 
--     Needed to Group to get the correct aggregate function results 
-- +1 Ordering fields
-- +1 A WHERE not used for joins
-- +1 Non-aggregate function Concat and format being used
-- +1 This query provides an estimate of the profitability of each tag, which is a great figure to have 
-- ---------------------------------------
-- 9 Points = Complex