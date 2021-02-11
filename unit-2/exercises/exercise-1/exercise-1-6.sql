SELECT title, year, SUM(price)
FROM album
NATURAL JOIN sale
WHERE year > 1970
GROUP BY title, year
ORDER BY year DESC;
