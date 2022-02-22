DROP DATABASE mahjong_scorekeeper_test;
CREATE DATABASE mahjong_scorekeeper_test;
USE mahjong_scorekeeper_test;

CREATE TABLE user(
	id INT AUTO_INCREMENT NOT NULL,
	username VARCHAR(20) NOT NULL,
	# Quickly googled max email address length, double-check later.
	email VARCHAR(254) NOT NULL,
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	
	PRIMARY KEY(id)
);

CREATE TABLE game(
	id INT AUTO_INCREMENT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE round(
	id INT AUTO_INCREMENT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE hand(
	id INT AUTO_INCREMENT NOT NULL,
	han INT(3),
	fu INT(3),
	yakuman INT(2),
	PRIMARY KEY(id)
);

INSERT player (username, password)
VALUES
('Player-A', 'nosuch'),
('Player-B', 'nosuch'),
('Player-C', 'nosuch');

INSERT rule_set (
	set_name, description, image_url,
	starting_score, ending_score,
	repeat_value, riichi_value, tenpai_payment,
	num_players)
VALUES
('Online Rules',
'A rule set resembling popular online clients such as Tenhou and MahjongSoul.',
'./images/chun_large.png',
25000, 30000,
100, 1000, 3000,
4),
('WRC Rules',
'A rule set resembling the official rules of the World Riichi Championship.',
'https://images.squarespace-cdn.com/content/v1/5834cfa4f5e231d203fec0cb/1526941446143-MZKA55DK80I2NJLMBKGW/wrc_navy_v1.1_400dpi.png?format=1500w',
30000, 30000,
100, 1000, 3000,
4);
