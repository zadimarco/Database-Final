INSERT INTO yauser
	(email, firstName, lastName, country, passw)
VALUES
	('n.drew@gmail.com', 'nancy', 'drew', 'United States', 'nancyissocool'),
    ('s.wise@yahoo.mail', 'sam', 'wise', 'Hobbit Land', 'IhateGandalf'),
    ('y.m@gmail.com', 'Yasmeen', 'Mendez', 'New Zealand', 'yasssqueen'),
    ('an.tang@northeastern.edu', 'Antoine', 'Tang', 'United States', 'antwon'),
    ('ray.bowden@northeastern.edu', 'Raymond', 'Bowden', 'England', 'IheartBoris'),
    ('ava.lam@fakeemail.com', 'Ava', 'Lam', 'United States', 'Ava:)'),
    ('t.philips@ohboyIamrunningoutofideas.net', 'Terence', 'Philips', 'England', 'xxtdawgxx'),
    ('a.arnold@ahhhhhhh.org', 'Asma', 'Arnold', 'United States', 'heyarnold'),
    ('anne.bassett@annebassett.edu', 'Anne', 'Bassett', 'United States', 'annebassettisaschool'),
    ('MikeShah@northeastern.edu', 'Michael', 'Shah', 'England', 'systems3700');


INSERT INTO yashow
	(title, description)
VALUES
	("It's Always Sunny In Philadelphia", 'Bunch of cool dudes being epic and based (maybe hairy)'),
    ('Beast Stars', 'God this show is a gateway drug'),
    ('Bojack Horseman', 'Funny and then depressing anthropomorphic horse man'),
    ('The Office', 'Funny man haha'),
    ('Duck Tales', 'WooOoo'),
    ('The Boys', 'Homelander cool?'),
    ('Breaking Bad', 'Bald Man recruits highschool student for fun meth filled adventures.'),
    ('Jojos Bizarre Adventure', 'Lot of muscular men in skimpy clothes with special fun powers'),
    ('Friends', 'I really would rather if we did not watch friends'),
    ('Regular Show', 'Raccoon and blue jay have a fun time working at a park');
    
INSERT INTO Season
VALUES
	(1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10),
    (1,11),
    (1,12),
    (1,13),
    (1,14),
    (1,15),
    (2,1),
    (3,1),
    (3,2),
    (3,3),
    (3,4),
    (3,5),
    (3,6),
    (4,1),
    (4,2),
    (4,3),
    (4,4),
    (4,5),
    (4,6),
    (4,7),
    (4,8),
    (5,1),
    (5,2),
    (5,3),
    (6,1),
    (6,2),
    (7,1),
    (7,2),
    (7,3),
    (7,4),
    (7,5),
    (8,1),
    (8,2),
    (8,3),
    (8,4),
    (8,5),
    (9,1),
    (9,2),
    (9,3),
    (9,4),
    (9,5),
    (9,6),
    (9,7),
    (9,8),
    (9,9),
    (9,10),
    (10,1),
    (10,2),
    (10,3),
    (10,4),
    (10,5),
    (10,6),
    (10,7),
    (10,8);

INSERT INTO platform
VALUES
	('Roku', False),
    ('Android', True),
    ('IOS', True),
    ('Fire Stick', False),
    ('Smart TV', False),
    ('Windows Phone', True),
    ('Google Chrome', False),
    ('Fire Fox', False),
    ('Safari', True),
    ('Random Platform', False);
    
INSERT INTO app
VALUES
	('Hulu', 'Lots of ads or costs a lot'),
    ('Netflix', 'The original creations are either ok or awful'),
    ('CrunchyRoll', 'The only way to watch anime besides pirating'),
    ('Vudu', 'I have not thought about Vudu since 2012'),
    ('Cable', 'You only use this one for sports'),
    ('SlingBox', 'Cable but cheaper. No one knows what it is tho'),
    ('Youtube TV', 'Cable through google and youtube. May as well use it if you want cable and give more money to google'),
    ('123Movies', 'Free besides maybe a virus or two. Invest in a good antivirus to avoid ever paying for tv again'),
    ('Apple TV', 'Apple made a TV replacement. Corporate expansion is so odd'),
    ('Crackle', 'I have no idea what crackle is. I just needed 10 apps');
    
INSERT INTO appplatform
VALUES
	('Hulu', 'Roku', 5.8, '1.2.3'),
	('Hulu', 'Android', 7.8, '3.2.3'),
	('Hulu', 'IOS', 6.8, '5.2.30'),
    ('Netflix', 'Android', 9.55, '2.0.1'),
    ('Netflix', 'IOS', 9.55, '2.0.1'),
    ('Netflix', 'Roku', 9.5, '2.0.1'),
    ('Netflix', 'Google Chrome', 10, '5.2.5'),    
    ('Netflix', 'Smart TV', 9.40, '4.0.2'),
    ('Netflix', 'Fire Fox', 7.65, '2.3.1'),
    ('CrunchyRoll', 'Android', 8.89, '5.0.1'),
    ('CrunchyRoll', 'IOS', 8.0, '1.0.56'),
    ('CrunchyRoll', 'Google Chrome', 7.5, '2.1.0'),
    ('CrunchyRoll', 'Fire Fox', 2.0, '0.0.1'),
    ('123movies', 'Fire Fox', 10.0, '10.0.1'),
    ('123movies', 'Google Chrome', 10.0, '10.0.1'),
    ('123movies', 'Safari', 10.0, '10.0.1');
    
INSERT INTO subscription
VALUES
	(10, 'Netflix', 10.0, '2021-04-04'),
	(10, 'Hulu', 20.0, '2021-04-04'),
	(10, 'CrunchyRoll', 15.0, '2021-04-04'),
	(10, '123movies', 0.0, '2021-04-04'),
	(10, 'Vudu', 10.0, '2030-04-04'),
    (4, 'Netflix', 10.0, '2022-02-04'),
	(4, 'Hulu', 20.0, '2020-07-20'),
    (6, 'Netflix', 10.0, '2020-05-08'),
	(6, 'Hulu', 20.0, '2021-24-04'),
    (8, 'Netflix', 10.0, '2021-04-04'),
	(8, 'Hulu', 20.0, '2021-04-04'),
    (3, 'Netflix', 10.0, '2021-04-04'),
	(3, 'Hulu', 20.0, '2021-04-04'),
    (3, 'CrunchyRoll', 15.0, '2021-04-04'),
	(3, 'Cable', 200.0, '2021-04-04');
	
INSERT INTO video
	(title, host, releaseDate, isFree, description, durationMS, episodeNum, showID, seasonNum)
VALUES
	('Episode 1', 'Netflix', '2020-03-13', True, "A herbivore's murder unserrles Cherryton academy", 1200000, 1, 2, 1),
	("The Academy's Top Dogs", 'Netflix', '2020-03-20', False, "A herbivore's murder unserrles Cherryton academy", 1210000, 2, 2, 1),
	("Mirror Man and Purple Smoke","CrunchyRoll","2018-12-28", False, "A man in the mirror catches the smoke", 1250000, 13, 8, 4),
    ("Jotaro Kujo! Meets Josuke Higahikata", "CrunchyRoll", "2016-04-01", True, "We meet the main character of Season 3", 1240200, 1, 8, 3),
    ("Heart Father", "CrunchyRoll", "2016-09-16", False, "Heart daddy", 1238000, 25, 8, 3),
    ("The Gang Gets Racist", "Hulu", "2005-08-04", True, "I don't know if I want to describe this one", 1195000, 1, 1, 1),
    ("Who Pooped the Bed?", "Hulu","2008-10-09", False, "Trying to figure out exactly who pooped the bed", 1197010, 7, 1, 4),
    ("Back to the Future", "Netflix", "1985-06-03", False, "Old man and kid go on little picnic back in time", 7138000, NULL, NULL, NULL),
    ("BOJACK HAHAHAHAHA", "123movies", "2014-08-22", True, "fun e fun e", 1175300, 5, 3, 1),
    ("Prickly-Muffin", "123movies", "2014-08-22", False, "Inuendo", 1175000, 3, 3, 1),
    ("Bojack Horseman: The Bojack Horseman Story, Chapter One", "123movies", "2014-08-22", True, "Bojack hires a writer for his memoir", 1177700, 1, 3, 1),
    ("Bojack hates the troops", "123movies", "2014-08-22", True, "Bojack absolutely hates the troops", 1175000, 2, 3, 1),
    ("The New Mutants", "123movies", "2020-08-22", False, "New movie about mutants", 5940000, NULL, NULL, NULL),
    ("Akira", "123movies", "1988-01-01", False, "Japanese boy goes super power crazy", 7560000, NULL, NULL, NULL);
    
INSERT INTO tag
VALUES
	(1, "Hot Dog"),
    (1, "Cute Puppy"),
    (2, "Mean Dog"),
    (2, "Cute Puppy"),
    (2, "Anime"),
    (3, "Anime"),
    (4, "Anime"),
    (5, "Anime"),
    (6, "Anime"),
    (8, "Time Travel"),
    (8, "Classic"),
    (8, "Marty"),
    (9, "Horse"),
    (9, "Funny"),
    (10, "Horse"),
    (10, "Sad"),
    (11, "Horse"),
    (11, "Funny"),
    (12, "Horse"),
    (12, "Anime"),
    (12, "Bojack"),
    (14, "Anime");
    
INSERT INTO userlistshows
VALUES
	(10, 1),
	(10, 2),
	(10, 8),
	(10, 9),
	(1, 5),
	(1, 6),
	(7, 2),
	(7, 5),
	(9, 6),
	(9, 5),
	(9, 10);
    
    
INSERT INTO userlistvideos
VALUES
	(10, 1),
	(10, 2),
	(10, 8),
	(10, 9),
	(1, 5),
	(1, 6),
	(7, 2),
	(7, 5),
	(9, 6),
	(9, 5),
	(9, 10),
    (6, 1),
    (6, 2),
    (6, 3),
    (6, 4),
    (6, 5),
    (6, 6),
    (6, 7),
    (6, 8),
    (6, 9),
    (6, 10),
    (6, 11);


INSERT INTO watched
VALUES
	(10, 3, True),
	(10, 4, True),
	(10, 5, True),
	(10, 6, True),
    (10, 7, False),
	(1, 1, True),
	(1, 2, False),
	(7, 6, False),
	(7, 7, False),
	(9, 3, True),
	(9, 4, True),
	(9, 9, True),
    (3, 1, True),
    (3, 2, True),
    (3, 3, True),
    (3, 4, True),
    (3, 5, True),
    (3, 6, True),
    (3, 7, True),
    (3, 8, True),
    (3, 9, True),
    (3, 10, True),
    (3, 11, True);