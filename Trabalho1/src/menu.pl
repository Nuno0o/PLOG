menu:- 
print,
getChar(input),
(
input = '1' -> mode;
input = '2' -> help;
input = '3';

nl,write('Invalid input.'),
nl,wait,
nl,menu

).

printMenu:-
clear,
	nl,
	write('PLOY'), nl,
	nl,
	write('1. Play'), nl,
	write('2. How to play'), nl,
	write('3. Exit'), nl,
	nl,
	write('Choose an option: ').
	
mode:-

help:-