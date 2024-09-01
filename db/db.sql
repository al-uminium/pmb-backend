drop database if exists pmb; 

create database pmb; 

use pmb;

CREATE TABLE user(
  uid CHAR(36) NOT NULL, 
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  hashed_password VARCHAR(255) NOT NULL,
  CONSTRAINT unique_email UNIQUE (email),
  PRIMARY KEY (uid)
);


CREATE TABLE expense_group(
  gid CHAR(36) NOT NULL,
  group_name VARCHAR(255) NOT NULL,
  token CHAR(36) NOT NULL, 
  default_currency CHAR(3) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (gid)
);

CREATE TABLE member(
  mid CHAR(36) NOT NULL, 
  uid CHAR(36),
  name VARCHAR(255) NOT NULL, 
  FOREIGN KEY (uid) REFERENCES User (uid),
  PRIMARY KEY (mid)
);

CREATE TABLE group_members(
    gid CHAR(36) NOT NULL,
    mid CHAR(36) NOT NULL,
    PRIMARY KEY (gid, mid),
    FOREIGN KEY (gid) REFERENCES expense_group (gid),
    FOREIGN KEY (mid) REFERENCES member (mid)
);

CREATE TABLE expense(
  eid CHAR(36) NOT NULL,
  gid CHAR(36) NOT NULL, 
  owner_mid CHAR(36) NOT NULL, 
  title VARCHAR(255) NOT NULL, 
  currency CHAR(3) NOT NULL, 
  total_cost DECIMAL (10, 2) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (gid) REFERENCES expense_group (gid),
  FOREIGN KEY (owner_mid) REFERENCES member (mid),
  PRIMARY KEY (eid)
);

-- For sorting purposes
CREATE TABLE expense_participants(
  eid CHAR(36) NOT NULL, 
  mid CHAR(36) NOT NULL,
  FOREIGN KEY (eid) REFERENCES expense (eid),
  FOREIGN KEY (mid) REFERENCES member (mid),
  PRIMARY KEY (eid, mid)
);