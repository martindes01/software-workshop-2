SELECT title, ROUND(AVG(rating), 2)
FROM album
NATURAL JOIN review
GROUP BY title
HAVING AVG(rating) > 3;
