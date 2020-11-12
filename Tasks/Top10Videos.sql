SELECT s.title, s.description, COUNT(s.id), v.host
FROM yashow s
INNER JOIN video v ON v.showID = s.id
INNER JOIN watched w ON w.videoID = v.id
GROUP BY s.id
ORDER BY COUNT(s.id) DESC
LIMIT 10;








