SELECT DISTINCT postcode
FROM customer
NATURAL JOIN sale
INNER JOIN review ON customer.custid = review.custid;
