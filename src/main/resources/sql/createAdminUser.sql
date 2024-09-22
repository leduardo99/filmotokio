INSERT INTO `filmotokio`.`users` (`active`, `password`, `username`) VALUES (0, '$2a$12$0uGEE2Ky1P8SOUAFnSD0hOHbk/RFmGbJaa/KrF2Cvonn7xVrtnw82', 'tokioschool');
INSERT INTO `filmotokio`.`roles` (`name`) VALUES ('ADMIN');
INSERT INTO `filmotokio`.`roles` (`name`) VALUES ('USER');
INSERT INTO `filmotokio`.`user_role` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `filmotokio`.`user_role` (`user_id`, `role_id`) VALUES ('1', '2');