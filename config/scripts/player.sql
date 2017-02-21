CREATE TABLE PLAYER (
player_id UUID PRIMARY KEY,
name VARCHAR(255),
email VARCHAR(255),
passcode VARCHAR(255),
seed UUID,
locale VARCHAR(255),
timezone VARCHAR(255)
);

INSERT INTO PLAYER VALUES (
'0a3a322d-7528-4680-9518-4ace0128a8ab',
'borkus',
'borkus@guildsr.us',
'IQjcGOYUoT2oXUllojh4c7PTmxgJSJJIfxi9EBwbUnc=',
'e2417bbe-984d-45a0-94cc-5d3ef2b487e8',
'en_US',
'America/New_York'
);
