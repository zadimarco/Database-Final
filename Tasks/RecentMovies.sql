-- Task was interpreted to only include videos released less than a year ago
-- Long video waws also interpreted to mean longer than an hour
SELECT v.title, v.description
FROM video v
WHERE v.showID is NULL AND 
v.durationMS > 3600000 AND
v.releaseDate > CURDATE() - INTERVAL 1 YEAR;






