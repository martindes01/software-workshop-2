/*
SQL prepared statement for getFollowerUserIDs(userid).
Lists the user IDs of users who are followers of the specified user.
These are the users who follow the specified user.
*/
SELECT useridA
FROM follows
WHERE useridB = ?;

/*
SQL prepared statement for getFollowingUserIDs(userid).
Lists the user IDs of users who the specified user is following.
These are the users who are followed by the specified user.
*/
SELECT useridB
FROM follows
WHERE useridA = ?;

/*
SQL prepared statement for getLikesOfUser(userid).
Lists the username and text of Jabs that the specified user has liked.
*/
SELECT username, jabtext
FROM likes
INNER JOIN jab USING (jabid)
INNER JOIN jabberuser ON (jab.userid = jabberuser.userid)
WHERE likes.userid = ?;

/*
SQL prepared statement for getTimelineOfUser(userid).
Lists the username and text of Jabs authored by users who the specified user follows.
*/
SELECT username, jabtext
FROM follows
INNER JOIN jabberuser ON (follows.useridB = jabberuser.userid)
INNER JOIN jab USING (userid)
WHERE useridA = ?;

/*
SQL statement for getMutualFollowUserIDs().
Lists the distinct pairs of user IDs belonging to users who follow each other mutually.
In the case of a self-following user, a pair comprises two identical user IDs.
The list contains only one permutation of each pair.
*/
-- DISTINCT modifier removes the duplicates permitted by the equality comparison in the WHERE clause below
SELECT DISTINCT f1.useridA, f1.useridB
FROM follows AS f1
INNER JOIN follows AS f2 ON (f1.useridA = f2.useridB AND f1.useridB = f2.useridA)
-- Less-than comparison prevents permutations of the same pair
-- Equality comparison allows for the case that users can follow themselves
WHERE f1.useridA <= f1.useridB;

/*
SQL prepared statement for addUser(username, emailadd).
Adds a user to the 'jabberuser' table with the specified username and email address.
The user is given a unique user ID.
*/
INSERT INTO jabberuser
VALUES
((SELECT MAX(userid) FROM jabberuser) + 1, ?, ?);

/*
SQL prepared statement for addJab(username, jabtext).
Adds a Jab to the 'jab' table with the specified text and a user ID corresponding to the specified username.
The user ID is that of the first user found with the specified username.
The Jab is given a new Jab ID.
*/
INSERT INTO jab
VALUES
((SELECT MAX(jabid) FROM jab) + 1, (SELECT userid FROM jabberuser WHERE username = ? LIMIT 1), ?);

/*
SQL prepared statement for addFollower(userida, useridb).
Adds a follows relationship to the 'follows' table.
This indicates that the user specified by 'userida' follows the user specified by 'useridb'.
*/
INSERT INTO follows
VALUES
(?, ?);

/*
SQL prepared statement for addLike(userid, jabid).
Adds a likes relationship to the 'likes' table.
This indicates that the user specified by 'userid' likes the Jab specified by 'jabid'.
*/
INSERT INTO likes
VALUES
(?, ?);

/*
SQL statement for getUsersWithMostFollowers().
Lists the user IDs of all users with the current greatest number of followers.
*/
SELECT useridB
FROM follows
GROUP BY useridB
HAVING COUNT(useridA) >= ALL (
  SELECT COUNT(useridA)
  FROM follows
  GROUP BY useridB
);
