SELECT *
FROM album
WHERE year < 1989 AND genre NOT IN ('rock', 'art rock') AND price <= 7;
