SELECT SUM(price)
FROM album
WHERE albumid IN (
  SELECT albumid
  FROM sale
  WHERE custid IN (
    SELECT custid
    FROM customer
    WHERE fname = 'Jimmy' AND lname = 'Osmond'
  )
);

-- equivalent to
SELECT SUM(price)
FROM album
NATURAL JOIN sale
NATURAL JOIN customer
WHERE fname = 'Jimmy' AND lname = 'Osmond';
