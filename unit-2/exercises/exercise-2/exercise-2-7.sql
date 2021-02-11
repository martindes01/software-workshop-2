SELECT title
FROM album
WHERE albumid IN (
  SELECT albumid
  FROM review
) AND albumid NOT IN (
  SELECT albumid
  FROM sale
);

-- equivalent to
SELECT title
FROM album
NATURAL JOIN review
WHERE albumid NOT IN (
  SELECT albumid
  FROM sale
);
