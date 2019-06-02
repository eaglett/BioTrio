DROP DATABASE IF EXISTS BioTrio;

CREATE DATABASE IF NOT EXISTS BioTrio;
USE BioTrio;

CREATE TABLE IF NOT EXISTS employee(
	employee_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    employee_password VARCHAR(50) NOT NULL,
    access_level VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS customer(
	customer_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    customer_password VARCHAR(50),
    email VARCHAR(50),
    phone_nb VARCHAR(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS theater(
	theater_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    theater_name VARCHAR(50) NOT NULL,
    nb_of_rows INT(10) NOT NULL,
    seats_per_row INT(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS movie(
	movie_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    movie_name VARCHAR(50) NOT NULL,
    year_of_production INT(10),
    duration INT(10)
);

CREATE TABLE IF NOT EXISTS showing(
	showing_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    movie_id INT(10) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movie(movie_id) ON DELETE CASCADE ON UPDATE CASCADE,
    theater_id INT(10) NOT NULL,
    FOREIGN KEY (theater_id) REFERENCES theater(theater_id) ON DELETE CASCADE ON UPDATE CASCADE,
    start_date_time DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket(
	ticket_id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    showing_id INT(10) NOT NULL,
    customer_id INT(10) NOT NULL,
    FOREIGN KEY (showing_id) REFERENCES showing(showing_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    seat_row VARCHAR(2) NOT NULL,
    seat_nb VARCHAR(2) NOT NULL,
    used BOOLEAN NOT NULL
);
