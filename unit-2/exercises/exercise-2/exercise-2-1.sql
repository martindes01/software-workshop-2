SELECT DISTINCT custid
FROM sale
WHERE albumid IN (
  SELECT albumid
  FROM album
  WHERE label = 'RCA'
);
