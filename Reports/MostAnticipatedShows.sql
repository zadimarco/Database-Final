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