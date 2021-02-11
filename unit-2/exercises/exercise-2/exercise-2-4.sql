SELECT postcode
FROM customer
WHERE custid IN (
  SELECT custid
  FROM sale
  NATURAL JOIN review
);
