SELECT postcode
FROM customer
WHERE custid IN (
  SELECT custid
  FROM sale
  INTERSECT
  SELECT custid
  FROM review
);

-- equivalent to
SELECT postcode
FROM customer
WHERE custid IN (
  SELECT custid
  FROM sale
) AND custid IN (
  SELECT custid
  FROM review
);
