SELECT DISTINCT title, year, price, name, description
FROM album
INNER JOIN genre ON album.genre = genre.name
NATURAL JOIN sale
WHERE year > 1970
ORDER BY year DESC;
