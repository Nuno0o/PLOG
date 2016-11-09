menu:-
	abolish(jogador/2),
	print_menu,
	get_char(Input),
	(
		get_char(_),
		Input = '1' -> mode;
		Input = '2' -> help;
		Input = '3' -> halt;
		menu
	).

print_menu:-
	nl,
	write('PLOY'), nl,
	nl,
	write('1. Play'), nl,
	write('2. How to play TODO'), nl,
	write('3. Exit'), nl,
	nl,
	write('Choose an option: ').

mode:-
	print_play,
	get_char(Input),
	(
		get_char(_),
		Input = '1' -> vs_player;
		Input = '2' -> vs_bot;
		Input = '3' -> menu;
		mode
	).

print_play:-
	nl,
	write('PLAY'), nl,
	nl,
	write('1. Vs. Player'), nl,
	write('2. Vs. AI TODO'), nl,
	write('3. Back'), nl,
	nl,
	write('Choose an option: ').

vs_player:-
	assert(jogador('red','human')),
	assert(jogador('green','human')),
	play.

vs_bot:-
	assert(jogador('red','human')),
	assert(jogador('green','bot')),
	play.

%help:-
