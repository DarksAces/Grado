
DROP TABLE Relationships CASCADE CONSTRAINTS;
DROP TABLE Users_Interests CASCADE CONSTRAINTS;
DROP TABLE Users CASCADE CONSTRAINTS;
DROP TABLE Interests CASCADE CONSTRAINTS;
DROP TABLE Relationship_Statuses CASCADE CONSTRAINTS;


DROP SEQUENCE interests_seq;
DROP SEQUENCE relationship_statuses_seq;
DROP SEQUENCE users_seq;


CREATE SEQUENCE interests_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE relationship_statuses_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1;


CREATE TABLE Relationship_Statuses (
    id INTEGER
        CONSTRAINT relationship_statuses_id_pk PRIMARY KEY,
    status VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE Interests (
    id INTEGER
        CONSTRAINT interests_id_pk PRIMARY KEY,
    interest VARCHAR2(20) NOT NULL UNIQUE
);

CREATE TABLE Users (
    id INTEGER
        CONSTRAINT users_id_pk PRIMARY KEY,
    username VARCHAR2(20) NOT NULL UNIQUE,
    birthday DATE,
    pic VARCHAR2(50),
    about_me VARCHAR2(200),
    created_at DATE DEFAULT SYSDATE NOT NULL
);

CREATE TABLE Users_Interests (
    id_interest INTEGER,
    id_user INTEGER,
    rate INTEGER NOT NULL,
    CONSTRAINT rate_range CHECK (rate BETWEEN 1 AND 5),
    CONSTRAINT users_interests_pk PRIMARY KEY (id_interest, id_user),
    CONSTRAINT users_interests_interest_fk FOREIGN KEY (id_interest) REFERENCES Interests(id),
    CONSTRAINT users_interests_user_fk FOREIGN KEY (id_user) REFERENCES Users(id)
);

CREATE TABLE Relationships (
    matching_user_id INTEGER,
    matched_user_id INTEGER,
    relationship_status INTEGER NOT NULL,
    blocked INTEGER NOT NULL,
    created_at DATE DEFAULT SYSDATE NOT NULL,
    CONSTRAINT relationships_pk PRIMARY KEY (matching_user_id, matched_user_id),
    CONSTRAINT relationships_matching_user_fk FOREIGN KEY (matching_user_id) REFERENCES Users(id),
    CONSTRAINT relationships_matched_user_fk FOREIGN KEY (matched_user_id) REFERENCES Users(id),
    CONSTRAINT relationships_status_fk FOREIGN KEY (relationship_status) REFERENCES Relationship_Statuses(id)
);


INSERT INTO Relationship_Statuses VALUES (relationship_statuses_seq.NEXTVAL, 'Friend Zone');
INSERT INTO Relationship_Statuses VALUES (relationship_statuses_seq.NEXTVAL, 'Couple');
INSERT INTO Relationship_Statuses VALUES (relationship_statuses_seq.NEXTVAL, 'Lovers');
INSERT INTO Relationship_Statuses VALUES (relationship_statuses_seq.NEXTVAL, 'Broken Up');
INSERT INTO Relationship_Statuses VALUES (relationship_statuses_seq.NEXTVAL, 'Not interested');



INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Movies');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Cooking');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Dancing');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Volunteering');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Yoga');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Exercise');
INSERT INTO Interests VALUES (interests_seq.NEXTVAL, 'Traveling');


INSERT INTO Users (id, username, birthday, pic, about_me, created_at)
VALUES (users_seq.NEXTVAL, 'Carol', TO_DATE('17/08/2000', 'DD/MM/YYYY'), NULL, 
        'Initially, I am seeking friendship with people who share my interests, although I do not rule out falling in love.', SYSDATE);

INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Movies'), (SELECT id FROM Users WHERE username = 'Carol'), 3);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Traveling'), (SELECT id FROM Users WHERE username = 'Carol'), 4);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Volunteering'), (SELECT id FROM Users WHERE username = 'Carol'), 5);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Exercise'), (SELECT id FROM Users WHERE username = 'Carol'), 3);





INSERT INTO Users (id, username, birthday, pic, about_me, created_at)
VALUES (users_seq.NEXTVAL, 'Nestor', TO_DATE('30/01/1994', 'DD/MM/YYYY'), 'nestor_pic_001.png', 
        'I am looking for my soulmate to travel the world with.', SYSDATE);


INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Yoga'), (SELECT id FROM Users WHERE username = 'Nestor'), 5);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Traveling'), (SELECT id FROM Users WHERE username = 'Nestor'), 5);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Dancing'), (SELECT id FROM Users WHERE username = 'Nestor'), 2);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Volunteering'), (SELECT id FROM Users WHERE username = 'Nestor'), 5);




INSERT INTO Users (id, username, birthday, pic, about_me, created_at)
VALUES (users_seq.NEXTVAL, 'Nadia', TO_DATE('22/02/1999', 'DD/MM/YYYY'), 'nadia_004.png', 
        'Professional dancer. I love exercising, and I''ve started learning to cook... and I love it!', SYSDATE);

INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Dancing'), (SELECT id FROM Users WHERE username = 'Nadia'), 5);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Traveling'), (SELECT id FROM Users WHERE username = 'Nadia'), 2);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Cooking'), (SELECT id FROM Users WHERE username = 'Nadia'), 4);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Exercise'), (SELECT id FROM Users WHERE username = 'Nadia'), 5);






INSERT INTO Users (id, username, birthday, pic, about_me, created_at)
VALUES (users_seq.NEXTVAL, 'Jose', TO_DATE('02/12/2002', 'DD/MM/YYYY'), 'jose_013.png', 
        'Thirsty for love. ;)', SYSDATE);


INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Movies'), (SELECT id FROM Users WHERE username = 'Jose'), 4);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Traveling'), (SELECT id FROM Users WHERE username = 'Jose'), 4);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Volunteering'), (SELECT id FROM Users WHERE username = 'Jose'), 2);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Exercise'), (SELECT id FROM Users WHERE username = 'Jose'), 3);
INSERT INTO Users_Interests VALUES ((SELECT id FROM Interests WHERE interest = 'Cooking'), (SELECT id FROM Users WHERE username = 'Jose'), 1);

UPDATE Users_Interests 
SET rate = 5 
WHERE id_user = (SELECT id FROM Users WHERE username = 'Jose') 
AND id_interest = (SELECT id FROM Interests WHERE interest = 'Cooking');


INSERT INTO Relationships (matching_user_id, matched_user_id, relationship_status, blocked, created_at)
VALUES (
    (SELECT id FROM Users WHERE username = 'Nadia'),
    (SELECT id FROM Users WHERE username = 'Jose'),
    (SELECT id FROM Relationship_Statuses WHERE status = 'Friend Zone'),
    0,
    SYSDATE
);


UPDATE Relationships
SET relationship_status = (SELECT id FROM Relationship_Statuses WHERE status = 'Not interested'),
    blocked = 1
WHERE matching_user_id = (SELECT id FROM Users WHERE username = 'Nadia')
AND matched_user_id = (SELECT id FROM Users WHERE username = 'Jose');


INSERT INTO Relationships (matching_user_id, matched_user_id, relationship_status, blocked, created_at)
VALUES (
    (SELECT id FROM Users WHERE username = 'Nestor'),
    (SELECT id FROM Users WHERE username = 'Carol'),
    (SELECT id FROM Relationship_Statuses WHERE status = 'Couple'),
    0,
    SYSDATE
);


CREATE TABLE New_Users_Interests AS SELECT * FROM Users_Interests;


DELETE FROM New_Users_Interests WHERE rate = 1;


UPDATE New_Users_Interests SET rate = 3;

TRUNCATE TABLE New_Users_Interests;

DROP TABLE New_Users_Interests;


CREATE OR REPLACE VIEW Unblocked_Relationships AS
SELECT matching_user_id, matched_user_id, relationship_status
FROM Relationships
WHERE blocked = 0;