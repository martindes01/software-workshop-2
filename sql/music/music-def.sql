CREATE TABLE artist (
  artistID INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  countryOfOrigin VARCHAR(255)
);

CREATE TABLE genre (
  name VARCHAR(255) PRIMARY KEY,
  description VARCHAR(255)
);

CREATE TABLE album (
  albumID INT PRIMARY KEY,
  artistID INT REFERENCES artist(artistID) NOT NULL,
  title VARCHAR(255) NOT NULL,
  label VARCHAR(255),
  year INT,
  genre VARCHAR(255) REFERENCES genre(name),
  price DECIMAL(6, 2) NOT NULL CHECK(price > 0)
);

CREATE TABLE customer (
  custID INT PRIMARY KEY,
  fname VARCHAR(255),
  lName VARCHAR(255) NOT NULL,
  houseNum VARCHAR(255) NOT NULL,
  postCode VARCHAR(255) NOT NULL,
  creditcard INT NOT NULL UNIQUE
);

CREATE TABLE sale (
  salesRef INT PRIMARY KEY,
  custID INT REFERENCES customer(custID) NOT NULL,
  albumID INT REFERENCES album(albumID) NOT NULL,
  saleDate DATE NOT NULL
);

  CREATE TABLE review (
  albumID INT REFERENCES album(albumID) NOT NULL,
  custID INT REFERENCES customer(custID) NOT NULL,
  rating INT NOT NULL,
  text VARCHAR(1024),
  PRIMARY KEY (albumID, custID)
);

CREATE TABLE label (
  name VARCHAR(255),
  region VARCHAR(255),
  country VARCHAR(255) NOT NULL,
  PRIMARY KEY (name, region)
);

CREATE TABLE composer (
  composerID INT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE credit (
  composerID INT REFERENCES composer(composerID),
  albumID INT REFERENCES album(albumID),
  weighting INT CHECK (weighting >= 0 AND weighting <= 100),
  PRIMARY KEY (composerID, albumID)
);

CREATE TABLE favourite (
  custid INT REFERENCES customer(custid),
  artistName VARCHAR(255)
);
