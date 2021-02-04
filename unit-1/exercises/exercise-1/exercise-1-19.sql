SELECT year, AVG(price)
FROM album
GROUP BY year
ORDER BY year;
