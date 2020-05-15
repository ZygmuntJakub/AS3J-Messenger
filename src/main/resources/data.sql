INSERT INTO USER (USERNAME, PASSWORD, EMAIL, AVATAR_URL) VALUES ( 'user1', '$2y$12$mbgq1js5u63Nmw.sGjbNSeM3rreiCrYiwCIo/.VY1w5TYImbMwbo', 'user1@mail.com', 'https://qph.fs.quoracdn.net/main-qimg-8d945bbaf167b063040eca16b0c59cd8.webp');
INSERT INTO USER (USERNAME, PASSWORD, EMAIL, AVATAR_URL) VALUES ( 'user2', '$2y$12$mbgq1js5u63Nmw.sGjbNSeM3rreiCrYiwCIo/.VY1w5TYImbMwbo', 'user2@mail.com', 'https://qph.fs.quoracdn.net/main-qimg-8d945bbaf167b063040eca16b0c59cd8.webp');
INSERT INTO USER (USERNAME, PASSWORD, EMAIL, AVATAR_URL) VALUES ( 'user3', '$2y$12$mbgq1js5u63Nmw.sGjbNSeM3rreiCrYiwCIo/.VY1w5TYImbMwbo', 'user3@mail.com', 'https://qph.fs.quoracdn.net/main-qimg-8d945bbaf167b063040eca16b0c59cd8.webp');

INSERT INTO USER_BLACKLIST (USER_ID, BLACKLIST_USER_ID) VALUES (2, 3);

INSERT INTO CHAT (NAME) VALUES ('Chat1');

INSERT INTO CHAT_USERS (CHAT_ID, USER_ID) VALUES (1, 1);
INSERT INTO CHAT_USERS (CHAT_ID, USER_ID) VALUES (1, 2);

INSERT INTO MESSAGE (CONTENT, TIMESTAMP, CHAT_ID, USER_ID) VALUES ('Hi', NOW(), 1, 1);
INSERT INTO MESSAGE (CONTENT, TIMESTAMP, CHAT_ID, USER_ID) VALUES ('Hi bro', NOW(), 1, 2);