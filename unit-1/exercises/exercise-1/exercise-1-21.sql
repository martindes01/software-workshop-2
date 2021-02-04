SELECT year, ROUND(AVG(price), 2)
FROM album
WHERE price > 8
GROUP BY year
ORDER BY year;
