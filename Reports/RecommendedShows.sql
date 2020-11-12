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






