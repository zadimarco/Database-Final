
-- Task a
-- All of the following variables will be parametrized in the actual product
SET @email = ':email';
SET @firstName = ':firstName';
SET @lastName = ':lastName';
SET @country = ':country';
SET @password = ':password'


INSERT INTO YAUser
	(email, firstName, lastName, country, passw)
VALUES
	(@email, @firstName, @lastName, @country, @password);





-- Task b
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





-- Task c
-- This task is quite odd. The only unique identifier for a show is the ID
-- This means that we need the user to input the showID to exactly identify the show
-- The user, however, can be identified through their email

SET @user = ':userEmail' COLLATE utf8mb4_general_ci;
SET @show = ':showID';

INSERT INTO userlistshows
VALUES
	((SELECT u.id FROM yauser u WHERE u.email = @user), @show);
	
	
	
	
	

-- Task d
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




-- Task e
-- All of these variables will be parametrized within the actual program

SET @videoTitle = ':videoTitle';
SET @description = ':description';
SET @appHost = ':appHost';
SET @duration = :duration;
SET @isFreeVideo = :isFreeVideo;
SET @releaseDate = ':releaseDate';
SET @showID = :showID;
SET @currentSeason = 
(SELECT MAX(v.seasonNum)
FROM video v
WHERE v.showID = @showID);




INSERT INTO video
	(title, description, host, releaseDate, isFree, durationMS, episodeNum, showID, seasonNum)
VALUES
	(@videoTitle, @description, @appHost, @releaseDate, @isFreeVideo, @duration, 
     (SELECT MAX(v2.episodeNum) + 1
       FROM video v2
       WHERE v2.seasonNum = @currentSeason AND
     		v2.showID = @showID),
     @showID,
     @currentSeason);







-- Task f

SELECT s.title, s.description, COUNT(s.id), v.host
FROM yashow s
INNER JOIN video v ON v.showID = s.id
INNER JOIN watched w ON w.videoID = v.id
GROUP BY s.id
ORDER BY COUNT(s.id) DESC
LIMIT 10;









-- Task g
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








-- Task h
-- Task was interpreted to only include videos released less than a year ago
-- Long video waws also interpreted to mean longer than an hour
SELECT v.title, v.description
FROM video v
WHERE v.showID is NULL AND 
v.durationMS > 3600000 AND
v.releaseDate > CURDATE() - INTERVAL 1 YEAR;





-- Task i
-- @country will be parametrized


# SET @country = ':country' COLLATE utf8mb4_general_ci;

SELECT a.name AS App, CONCAT("$",SUM(s.cost)) AS revenue
FROM app a
INNER JOIN subscription s ON s.appName = a.name
INNER JOIN yauser u ON u.id = s.userID
WHERE @country = u.country
GROUP BY a.name
ORDER BY revenue




-- Task j
-- The task was interpreted as producing every video that falls into the top 3 tags and returning their individual watch counts

SELECT v.title AS `Video Title`, t.tagName AS `Tag Name`, SUM(w.videoID IS NOT NULL) AS `Watch Count`
FROM video v 
LEFT OUTER JOIN watched w ON v.id = w.videoID
INNER JOIN tag t ON t.videoID = v.id
INNER JOIN
(
  SELECT t.tagName AS Tag, COUNT(*) AS Watches
  FROM tag t
  INNER JOIN video v ON t.videoID = v.id
  INNER JOIN watched w ON w.videoID = v.id
  GROUP BY t.tagName
  ORDER BY Watches DESC
  LIMIT 3
) top3 ON top3.Tag = t.tagName
GROUP BY v.id, t.tagName
ORDER BY `Tag Name`, `Watch Count` DESC, `Video Title`;








