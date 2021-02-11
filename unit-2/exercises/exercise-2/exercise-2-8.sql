SELECT genre
FROM album
NATURAL JOIN review
GROUP BY genre
HAVING COUNT(genre) >= ALL (
  SELECT COUNT(genre)
  FROM album
  NATURAL JOIN review
  GROUP BY genre
);
