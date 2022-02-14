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