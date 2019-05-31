USE biotrio;

# selecting all the customers
SELECT * FROM customer;
# inserting a customer
INSERT INTO customer (first_name, last_name, customer_password, email, phone_nb) 
			VALUES ('Bobby', 'Jones', 'Iambob', 'bob@gmail.com', '0093446291');
# selecting a specific customer
SELECT * FROM customer WHERE customer_id = 2;
# finding a customer by email
SELECT * FROM customer WHERE email = 'felix123@gmail.com';
# updating a customer
UPDATE customer SET first_name = 'Frank', last_name = 'Jonsons', customer_password = '123frank', email = 'frankie@gmail.com', phone_nb = '0044587622' WHERE customer_id = 2;


# selecting all the employees
SELECT * FROM employee;
# inserting an employee
INSERT INTO employee (username, employee_password, access_level) 
			VALUES('Christian', 'chris123', 'admin');
# deleting an employee
DELETE FROM employee WHERE employee_id  = 3;
# updating an employee
UPDATE employee SET username = 'Christian', employee_password  = 'chris1234', access_level = 'basic' WHERE employee_id = 4;
# Finding an employee by username
SELECT * FROM employee WHERE username = 'Christian';


# finding a movie by id
SELECT * FROM movie WHERE movie_id = 2;
# finding a movie by showing_id
SELECT * FROM movie
INNER JOIN showing ON showing.movie_id = movie.movie_id
WHERE showing_id = 1;
# finding all movies
SELECT * FROM movie;
# inserting a movie
INSERT INTO movie (movie_name, year_of_production, duration) 
			VALUES ('IT', 2017, 130);
# deleting a movie 
DELETE FROM movie WHERE movie_id = 5;
# updating a movie 
UPDATE movie SET movie_name = 'Punisher', year_of_production = 2018, duration = 140 WHERE movie_id = 6;


# finding all showings for a specific movie
SELECT showing.showing_id, m.movie_id, t.theater_id, showing.start_date_time FROM showing 
INNER JOIN theater t ON showing.theater_id = t.theater_id 
INNER JOIN movie m ON showing.movie_id = m.movie_id 
WHERE m.movie_id = 1;
# finding a showing by id
SELECT * FROM showing WHERE showing_id = 2;
# inserting a showing
INSERT INTO showing ( movie_id, theater_id, start_date_time) VALUES
	( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Long Shot'),
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Red Theater'),
	  '2019-06-05 18:00');
# deleting a showing
DELETE FROM showing WHERE showing_id = 2;
# updating a showing
UPDATE showing SET movie_id = 3, theater_id = 2, start_date_time = '1019-06-06 19:00' WHERE showing_id = 4;
# finding showings by movie_id
SELECT showing.showing_id, m.movie_name, t.theater_name, duration, showing.start_date_time FROM showing 
INNER JOIN theater t ON showing.theater_id = t.theater_id 
INNER JOIN movie m ON showing.movie_id = m.movie_id 
WHERE m.movie_id = 1;


# finding all theater
SELECT * FROM theater;
# finding theater by showing_id
SELECT * FROM theater WHERE theater_id = 3; 
# inserting a theater
INSERT INTO theater (theater_name, nb_of_rows, seats_per_row) 
			VALUES('Purple', 10, 5);
# deleting a theater
DELETE from theater WHERE theater_id = 3;
# updating a theater
UPDATE theater SET theater_name = 'Pink', nb_of_rows = 9, seats_per_row =5 WHERE theater_id = 4;


# finding ticket by id
SELECT * FROM ticket WHERE showing_id = 2;
# finding ticket by phone no
SELECT * FROM ticket
INNER JOIN customer ON ticket.customer_id = customer.customer_id
WHERE (showing_id = 2  AND phone_nb = '00465738254');
# inserting a ticket 
INSERT INTO ticket (showing_id, customer_id, seat_row, seat_nb, used)
			VALUES (2, 4, 6 , 4, FALSE);
# updating the ticket status
UPDATE ticket SET used = TRUE where ticket_id = 2;