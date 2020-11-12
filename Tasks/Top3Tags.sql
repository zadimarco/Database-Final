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








