-- Find other users with similar tastes (only looks at users that have watched a show)
-- Criteria is that you both have liked the same shows at a 75% rate
-- Outputs the percentages in a nice format for users
-- @user will be parametrized
# SET @user = 'MikeShah@northeastern.edu' COLLATE utf8mb4_general_ci;

SELECT CONCAT("You and ", tb.firstName , " ", tb.lastName, " have similar tastes (A ", tb.percentageAgrees,"% rate)")
FROM 
(SELECT yu1.firstName, yu1.lastName, yu1.email, SUM(w1.liked = w2.liked)/ COUNT(*) AS percentageAgrees
 FROM yauser yu1
 INNER JOIN watched w1 ON yu1.id = w1.userID
 INNER JOIN watched w2 ON w1.videoID = w2.videoID
 WHERE w2.userID = (SELECT yu2.id FROM yauser yu2 WHERE yu2.email = @user)
 GROUP BY yu1.id
 HAVING SUM(w1.liked = w2.liked)/COUNT(*) > .75) tb
WHERE NOT tb.email = @user
ORDER BY percentageAgrees DESC


-- -------------SCORING-------------
-- +1 Joined 3 tables (2 are the same table, but there are 3 tables total still)
-- +1 One subquery (didn't add 2, as the inner subquery could probably be taken out)
-- +1 Aggregate Function
-- +1 Grouping 
--     Needed to Group to get the correct aggregate function results 
-- +1 WHERE/HAVING conditions not for joins 
-- +1 Non-aggregate function Concat being used
-- +1 This query allows people to find other users with similar tastes, which I feel is a useful feature
-- ---------------------------------------
-- 7 Points = Complex





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











-- Report of the videos that other users on the same apps as the given user have like (ranked from most likes to least likes)
-- Including free videos, as this is to determine what the app should recommend to the user

# SET @user = 's.wise@yahoo.mail' COLLATE utf8mb4_general_ci;

SELECT v.title AS "Recommended Videos", SUM(w.liked) AS "Number of Likes"
FROM video v
INNER JOIN watched w ON v.id = w.videoID
INNER JOIN yauser yu ON yu.id = w.userID
INNER JOIN app a ON a.name = v.host
WHERE a.name IN 
(
  SELECT a2.name 
  FROM app a2
  INNER JOIN subscription s2 ON s2.appName = a2.name
  WHERE s2.userID = (SELECT yauser.id FROM yauser WHERE yauser.email = @user)
) OR
v.isFree
GROUP BY v.title
ORDER BY SUM(w.liked) DESC

-- -------------SCORING-------------
-- +1 Joined 4 tables
-- +1 Two subqueries (Inner subquery is kind of trivial)
-- +1 Aggregate Function
-- +1 Grouping 
--     Needed to Group to get the correct aggregate function results 
-- +1 Ordering
-- +1 WHERE/HAVING conditions not for joins 
-- +1 This query is a recommendation system based on other users, which is incredibly useful for a tv application
-- ---------------------------------------
-- 7 Points = Complex






-- Get the most anticipated shows from user lists
-- Uses the metric of counting the amount of times that the show appears on a user list
-- Also adds occurrances of a video from the show by treating it as a percentage of the show
-- 		This percentage is calculated from the total episodes within the show

SELECT s.title, COUNT(*) + IF(showCount.totalEps = 0, 0, showToWatch.numToWatch/showCount.totalEps) AS `Number of times this show appears on a list`
FROM userlistshows uls
INNER JOIN yashow s ON s.id = uls.showID
INNER JOIN
(
  SELECT SUM(v3.id IS NOT NULL) AS totalEps, s2.id
  FROM yashow s2 
  LEFT OUTER JOIN video v3 ON v3.showID = s2.id
  GROUP BY s2.id
) showCount ON showCount.id = s.id 
INNER JOIN
(
  SELECT COUNT(v4.id) AS numToWatch, s3.id
  FROM yashow s3
  LEFT OUTER JOIN 
  (video v4 
  INNER JOIN userlistvideos ulv2 ON ulv2.videoID = v4.id
  ) ON v4.showID = s3.id
  GROUP BY s3.id
) showToWatch ON showToWatch.id = s.id
GROUP BY s.id
ORDER BY `Number of times this show appears on a list` DESC

-- -------------SCORING-------------
-- +1 Joined a lot of tables (largest join is 4 tables being the 2 subqueries and 2 additional tables)
-- +2 2 subqueries
-- +1 Aggregate Function
-- +1 Non-Inner join being used
-- +1 Grouping 
--     Needed to Group to get the correct aggregate function results 
-- +1 Ordering fields
-- +1 Non-aggregate function If being used
-- ---------------------------------------
-- 9 Points = Complex







-- Top Rated Shows or Movies given a tag
-- Criteria is that a show has at least 50% of its episodes tagged as the given tag
# SET @tag = 'Anime' COLLATE utf8mb4_general_ci;

SELECT tags.name AS "Recommended", IFNULL(10 * likes.rating, "Unrated") AS Rating, IF(likes.isMovie = 1, 'Movie', 'Show') AS 'Type of experience'
FROM
(
  SELECT IFNULL(s.id, v.id) AS id, IFNULL(s.title, v.title) AS name
  FROM video v
  LEFT OUTER JOIN yashow s ON v.showID = s.id
  LEFT OUTER JOIN tag t ON t.videoID = v.id
  GROUP BY name
  HAVING SUM( NOT t.tagName IS NULL AND t.tagName = @tag)/COUNT(DISTINCT v.id) >= .5
) tags
INNER JOIN
(
  SELECT IFNULL(s.id, v.id) AS id, SUM(w.liked)/SUM(NOT w.userID is NULL) AS rating, SUM(NOT w.userID is NULL) AS totalRatings, s.id IS NULL AS isMovie
  FROM video v 
  LEFT OUTER JOIN watched w ON v.id = w.videoID
  LEFT OUTER JOIN yashow s ON v.showID = s.id
  GROUP BY id, s.id
) likes
ON likes.id = tags.id
GROUP BY tags.name
ORDER BY likes.rating DESC, tags.name

-- -------------SCORING-------------
-- +1 Joined More than 3 tables (Subqueries each use 3)
-- +1 Non-inner join used
-- +2 Two subquery
-- +1 Aggregate Function
-- +1 Grouping 
-- +1 Ordering fields 
-- 		Ordering by the show's rating and the name of the show
-- +1 This query allows a user to find the highest rated shows of a specific tag, which seems very useful
-- ---------------------------------------
-- 8 Points = Complex
