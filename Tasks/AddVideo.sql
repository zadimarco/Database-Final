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







