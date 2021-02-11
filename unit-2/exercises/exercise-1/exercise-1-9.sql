SELECT name
FROM artist
NATURAL JOIN album
NATURAL JOIN sale
GROUP BY name
ORDER BY COUNT(name) DESC
LIMIT 3;
