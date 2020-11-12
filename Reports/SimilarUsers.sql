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





