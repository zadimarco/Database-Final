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
