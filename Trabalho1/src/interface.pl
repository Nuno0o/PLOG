clear:-	clear(40), !.

clear(0).
clear(N):-
	nl,
	N1 is N-1,
	clear(N1).

wait:- get_char(_).