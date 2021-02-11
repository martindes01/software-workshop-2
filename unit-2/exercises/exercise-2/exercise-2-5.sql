SELECT DISTINCT title, year, price, name, description
FROM album
INNER JOIN genre ON album.genre = genre.name
WHERE year > 1970 AND albumid IN (
  SELECT albumid
  FROM sale
)
ORDER BY year DESC;
