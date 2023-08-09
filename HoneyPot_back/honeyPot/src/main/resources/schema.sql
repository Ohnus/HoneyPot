DROP TABLE IF EXISTS ott;

CREATE TABLE ott 
(
    type      VARCHAR(255)      NOT NULL,
      price   INT         NOT NULL,
      maxppl   INT         NOT NULL,
    PRIMARY KEY (type)
);