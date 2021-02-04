SELECT year, ROUND(AVG(price), 2)
FROM album
GROUP BY year
ORDER BY year;
