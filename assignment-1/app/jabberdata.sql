INSERT INTO jabberuser
VALUES
(0, 'donaldJtrump', 'donald@donald.com'),
(1, 'kim','kim@kanye.com'),
(2, 'DavidBowie','david@bowienet.com'),
(3, 'HackerTDog','hackert@dog.com'),
(4, 'robertsmith', 'rob@thecure.com'),
(5, 'klopp', 'manager@anfield.com'),
(6, 'percy', 'ppig@marksandspencer.net'),
(7, 'cyborg', 'cyborg@teentitansgo.com'),
(8, 'icebear', 'iceb@wbb.com'),
(9, 'solesurvivor', 'sole@vault111.com'),
(10, 'ellie', 'ellie@lastofus.com'),
(11, 'jumpman', 'jumpmanmario@marioworld.net'),
(12, 'edballs', 'ed@edballs.com');

INSERT INTO jab
VALUES
(0, 0, 'MAGA!'),
(1, 0, 'SAD!'),
(2, 1, 'uokhun'),
(3, 2, 'My version of The Man Who Sold The World is way better'),
(4, 3, 'WOOF!'),
(5, 0, 'covfefe'),
(6, 12, 'Ed Balls'),
(7, 7, 'anybody watching that Gumball show?'),
(8, 8, 'ice bear says We Bare Bears is the best show'),
(9, 11, 'coming for you, Donkey Kong!!!'),
(10, 9, 'Mmm, roasted mirelurk for lunch.'),
(11, 3, 'WOOF!!!');

INSERT INTO follows
VALUES
(1,2),
(11,2),
(0,12),
(0,11),
(0,6),
(0,7),
(1,3),
(1,0),
(1,12),
(2,0),
(2,9),
(3,1),
(3,7),
(3,8),
(6,0),
(6,8),
(6,10),
(7,0),
(7,3),
(9,1),
(9,4),
(9,11),
(11,0),
(11,12),
(12,11),
(12,0);

INSERT INTO likes
VALUES
(0,2),
(2,3),
(0,4),
(0,11),
(1,2),
(1,6),
(3,3),
(3,11),
(4,10),
(10,0),
(7,0),
(10,10);