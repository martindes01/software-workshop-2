SELECT custid, SUM(price)
FROM sale
NATURAL JOIN album
GROUP BY custid
ORDER BY SUM(price) DESC
LIMIT 1;
