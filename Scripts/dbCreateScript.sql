/* Script that creates all Tables for RMA Application */

CREATE TABLE Run (
	RunID INT AUTO_INCREMENT,
	Name VARCHAR(40),
	Day DATETIME NOT NULL,
	EventID INT NOT NULL,
	
	PRIMARY KEY (RunID)
);


CREATE TABLE Round (
	RoundNumber INT AUTO_INCREMENT,
	
	/*alternatively time with start and end date:
	StartDate DATETIME(3),
	EndDate DATETIME(3),*/
	LapTime TIME(3), /*Time with precision of 3: hh:mm:ss.000*/
	RunID INT NOT NULL, 
	RunnerID INT NOT NULL,
	
	PRIMARY KEY (RoundNumber,RunID,RunnerID)
);

CREATE TABLE Runner (
	RunnerID INT AUTO_INCREMENT,
	FirstName VARCHAR(30) NOT NULL,
	LastName VARCHAR(30) NOT NULL,
	BirthDate DATE,
	
	PRIMARY KEY (RunnerID)
);

CREATE TABLE RMA_User (
    UserID INT AUTO_INCREMENT,
    Username varchar(50) NOT NULL UNIQUE,
    Password varchar(90) NOT NULL,
    PRIMARY KEY (UserID)
);

CREATE TABLE Event (
	EventID INT AUTO_INCREMENT,
	Name VARCHAR(50) NOT NULL,
	StartDate DATE NOT NULL,
	EndDate DATE,
	PRIMARY KEY (EventID)
);

CREATE TABLE Track (
	RunnerID INT AUTO_INCREMENT,
	RunID INT NOT NULL,
	Track INT NOT NULL,
	
	PRIMARY KEY (RunnerID, RunID)
);

CREATE TABLE Participation (
	RunnerID INT,
	EventID INT,
	Ranking INT,
	
	PRIMARY KEY (RunnerID, EventID)
);

CREATE TABLE Ownership (
	EventID INT,
	UserID INT,
	
	PRIMARY KEY (EventID, UserID)
);


/*
Foreign keys
*/

ALTER TABLE Run ADD CONSTRAINT FK_Event_Run FOREIGN KEY (EventID) REFERENCES Event(EventID);

ALTER TABLE Round ADD CONSTRAINT FK_Run_Round FOREIGN KEY (RunID) REFERENCES Run(RunID);
ALTER TABLE Round ADD CONSTRAINT FK_Runner_Round FOREIGN KEY (RunnerID) REFERENCES Runner(RunnerID);

ALTER TABLE Track ADD CONSTRAINT FK_Runner_Track FOREIGN KEY (RunnerID) REFERENCES Runner(RunnerID);
ALTER TABLE Track ADD CONSTRAINT FK_Run_Track FOREIGN KEY (RunID) REFERENCES Run(RunID);

ALTER TABLE Participation ADD CONSTRAINT FK_Runner_Participation FOREIGN KEY (RunnerID) REFERENCES Runner(RunnerID);
ALTER TABLE Participation ADD CONSTRAINT FK_Event_Participation FOREIGN KEY (EventID) REFERENCES Event(EventID);

ALTER TABLE Ownership ADD CONSTRAINT FK_User_Ownership FOREIGN KEY (UserID) REFERENCES RMA_User(UserID);
ALTER TABLE Ownership ADD CONSTRAINT FK_Event_Ownership FOREIGN KEY (EventID) REFERENCES Event(EventID);