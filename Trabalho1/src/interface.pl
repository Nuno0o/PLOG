clear_screen:-
	clear_screen(40), !.

clear_screen(0).
clear_screen(N):-
	nl,
	N1 is N-1,
	clear_screen(N).
