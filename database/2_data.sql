USE BioTrio;

INSERT INTO employee (username, employee_password, access_level) VALUES 
	( 'tom', '123', 'admin'),
    ( 'jane1990', 'puppies', 'basic'),
    ( 'marcSmith', '123456', 'basic');
    
INSERT INTO customer (first_name, last_name, customer_password, email, phone_nb) VALUES
	( 'Hilary', 'Jefferson', 'hilary123', 'hilary.jefferson@gmail.com', '00451234567'),
    ( 'Felix', 'Roberts', 'felix_xilef', 'felix123@gmail.com', '00453245678'),
    ( 'Jim', 'Fox', 'jimmy', 'jimfox@gmail.com', '00456783465'),
    ( 'Scarlet', 'Smith', 'monkey321', 'scarlet.smith@gmail.com', '00459872344'),
    ( 'Mary', 'Stone', 'chocolate', 'marystone@gmail.com', '00456548231'),
    ( 'Russel', 'Bush', 'rus987', 'russel.bush@gmail.com', '00456677876');
    
INSERT INTO theater (theater_name, nb_of_rows, seats_per_row) VALUES 
	( 'Blue theater', 14, 20),
    ( 'Red Theater', 8, 12),
    ( 'Orange Theater', 8, 6);

INSERT INTO movie (movie_name, year_of_production, duration) VALUES 
	( 'Avengers: Endgame', 2019, 181),
    ( 'Sonic the Hedgehog', 2019, 98),
    ( 'Avengers: Infinity War', 2019, 149),
    ( 'Extremely Wicked, Shockingly Evil and Vile', 2019, 110),
    ( 'Captain Marvel', 2019, 123),
    ( 'Long Shot', 2019, 93),
    ( 'Spider-Man: Far from Home', 2019, 164),
    ( 'Pokémon Detective Pikachu', 2019, 128),
    ( 'Aladdin', 2019, 135),
    ( 'Cold Pursuit', 2019, 90);

INSERT INTO showing ( movie_id, theater_id, start_date_time) VALUES
	( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Long Shot'),
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Blue Theater'),
	  '2019-06-05 18:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Sonic the Hedgehog'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Blue Theater'), 
	  '2019-06-05 21:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Aladdin'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Blue Theater'), 
	  '2019-06-06 18:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Avengers: Endgame'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Red Theater'), 
	  '2019-06-05 18:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Extremely Wicked, Shockingly Evil and Vile'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Red Theater'), 
	  '2019-06-05 21:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Avengers: Infinity War'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Red Theater'), 
	  '2019-06-06 18:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Spider-Man: Far from Home'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Orange Theater'), 
	  '2019-06-05 17:00'),
    ( (SELECT movie_id 
			FROM movie 
            WHERE movie_name = 'Pokémon Detective Pikachu'),
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Orange Theater'),
	  '2019-06-05 20:00'),
     ( (SELECT movie_id 
			FROM movie 
			WHERE movie_name = 'Cold Pursuit'), 
	  (SELECT theater_id 
			FROM theater 
            WHERE theater_name = 'Orange Theater'), 
	  '2019-06-06 19:00');
     
INSERT INTO ticket (showing_id, customer_id, seat_row, seat_nb, used)
VALUES (2, 3,6 ,4, FALSE),
       (4, 2, 5, 1, FALSE),
       (4, 1, 5, 3, FALSE),
       (5, 2, 1, 1, FALSE),
       (2, 3, 8, 3, FALSE),
       (2, 1, 7, 4, FALSE),
       (8, 2, 2, 2, FALSE),
       (3, 4, 4, 5, FALSE);
        
