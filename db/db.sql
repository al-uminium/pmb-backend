drop database if exists pmb; 

create database pmb; 

use pmb;

CREATE TABLE User(
  uid CHAR(36) NOT NULL, 
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  hashed_password VARCHAR(255) NOT NULL,
  CONSTRAINT unique_email UNIQUE (email),
  PRIMARY KEY (uid)
);


CREATE TABLE Expense_Group(
  gid CHAR(36) NOT NULL,
  group_name VARCHAR(255) NOT NULL,
  token CHAR(36) NOT NULL, 
  default_currency CHAR(3) NOT NULL,
  created_at DATETIME,
  PRIMARY KEY (gid)
);

CREATE TABLE Member(
  mid CHAR(36) NOT NULL, 
  uid CHAR(36),
  name VARCHAR(255) NOT NULL, 
  FOREIGN KEY (uid) REFERENCES User (uid),
  PRIMARY KEY (mid)
);

CREATE TABLE Expense(
  eid CHAR(36) NOT NULL,
  gid CHAR(36) NOT NULL, 
  owner_mid CHAR(36) NOT NULL, 
  title VARCHAR(255) NOT NULL, 
  currency CHAR(3) NOT NULL, 
  total_cost DECIMAL (10, 2) NOT NULL,
  created_at DATETIME,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (gid) REFERENCES Expense_Group (gid),
  FOREIGN KEY (owner_mid) REFERENCES Member (mid),
  PRIMARY KEY (eid)
);

-- For sorting purposes
CREATE TABLE Expense_Participants(
  eid CHAR(36) NOT NULL, 
  mid CHAR(36) NOT NULL,
  FOREIGN KEY (eid) REFERENCES Expense (eid),
  FOREIGN KEY (mid) REFERENCES Member (mid),
  PRIMARY KEY (eid, mid)
);