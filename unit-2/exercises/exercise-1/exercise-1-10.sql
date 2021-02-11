SELECT label.name, artist.name, COUNT(artist.name)
FROM label
INNER JOIN album ON label.name = album.label
INNER JOIN artist ON album.artistid = artist.artistid
WHERE artist.countryoforigin = 'UK'
GROUP BY label.name, artist.name
ORDER BY COUNT(artist.name) DESC
LIMIT 1;
