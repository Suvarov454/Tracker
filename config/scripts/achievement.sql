CREATE TABLE ACHIEVEMENT (
achievement_id UUID PRIMARY KEY,
title_id UUID,
name VARCHAR(255),
description VARCHAR(4000),
sort VARCHAR(255)
);

ALTER TABLE ACHIEVEMENT ADD FOREIGN KEY (title_id) REFERENCES TITLE(title_id);

INSERT INTO ACHIEVEMENT VALUES (
'8c85fac7-328f-42ca-86d2-15f5650f2f9a',
'10052271-56fa-46d4-acce-4665bcbc00e6',
'Gathered 50 coins',
'Gathered 50 coins so far. Keep up the good work!',
'GATHER-COINS-00050'
);
