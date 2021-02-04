SELECT year, ROUND(AVG(price), 2)
FROM album
WHERE price > 8
GROUP BY year
HAVING AVG(price) < 10
ORDER BY year;
