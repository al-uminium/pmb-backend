drop database if exists ppm;

create database ppm;

use ppm;

create table User (
  user_id CHAR(36) NOT NULL, 
  username VARCHAR(100) NOT NULL, 
  email VARCHAR(255) NOT NULL,
  hashpw VARCHAR(255),
  registration_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT unique_email UNIQUE (email),
  PRIMARY KEY (user_id)
);

create table Expenditures (
  expenditure_id CHAR(36) NOT NULL, 
  default_currency VARCHAR(3) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (expenditure_id)
);

CREATE TABLE Expense (
  expense_id CHAR(36) NOT NULL,
  owner_id VARCHAR(36) NOT NULL,
  total_cost INT NOT NULL,
  mongo_split_id VARCHAR(36) NOT NULL,
  expenditure_id VARCHAR(36) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (owner_id) REFERENCES User (user_id),
  FOREIGN KEY (expenditure_id) REFERENCES Expenditures (expenditure_id),
  PRIMARY KEY (expense_id)
);

create table Expenditure_User (
  expenditure_id CHAR(36), 
  user_id CHAR(36), 
  PRIMARY KEY (expenditure_id, user_id),
  FOREIGN KEY (expenditure_id) REFERENCES Expenditures (expenditure_id),
  FOREIGN KEY (user_id) REFERENCES User (user_id)
);

CREATE TABLE Invites (
  invite_token VARCHAR(255) NOT NULL,
  expenditure_id CHAR(36),
  expire_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (expenditure_id) REFERENCES Expenditures (expenditure_id),
  PRIMARY KEY (expenditure_id)
);
