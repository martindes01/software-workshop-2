CREATE TABLE jabberuser (
  userid INT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  emailadd VARCHAR(255) NOT NULL
);

CREATE TABLE jab (
  jabid INT PRIMARY KEY,
  userid INT REFERENCES jabberuser(userid) NOT NULL,
  jabtext VARCHAR(255) NOT NULL
);

CREATE TABLE likes (
  userid INT REFERENCES jabberuser(userid),
  jabid INT REFERENCES jab(jabid) NOT NULL,
  PRIMARY KEY (userid, jabid)
);

CREATE TABLE follows (
  useridA INT REFERENCES jabberuser(userid) NOT NULL,
  useridB INT REFERENCES jabberuser(userid) NOT NULL,
  PRIMARY KEY (useridA, useridB)
);
