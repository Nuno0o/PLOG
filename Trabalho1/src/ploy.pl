:- use_module(library(system)).
:- include('board.pl').
:- include('menu.pl').
:- include('interface.pl').

ploy:- menu.

draw_gameboard:-X^(tabuleiro(X),draw_board(X)).

draw_board(Tab):-
	draw_straightLine,nl,
	draw_lines(Tab).

draw_lines([]).
draw_lines([LIN|REST]):-(
	write('|'),draw_line1(LIN),nl,
	write('|'),draw_line2(LIN),nl,
	write('|'),draw_line3(LIN),nl,
	draw_straightLine,
	nl,
	draw_lines(REST)).

draw_line1([]).
draw_line1([SQUARE|REST]):-
	draw_line1sq1(SQUARE),
	draw_line1sq2(SQUARE),
	draw_line1sq3(SQUARE),
	write('|'),
	draw_line1(REST).

draw_line1sq1([]).
draw_line1sq1([TEAM|[SIDES|NONE]]):-
	member('nw',SIDES) -> write('\\'); write(' ').

draw_line1sq2([]).
draw_line1sq2([TEAM|[SIDES|NONE]]):-
	member('n',SIDES) -> write('|'); write(' ').

draw_line1sq3([]).
draw_line1sq3([TEAM|[SIDES|NONE]]):-
	member('ne',SIDES) -> write('/'); write(' ').

draw_line2([]).
draw_line2([SQUARE|REST]):-
	draw_line2sq1(SQUARE),
	draw_line2sq2(SQUARE),
	draw_line2sq3(SQUARE),
	write('|'),
	draw_line2(REST).

draw_line2sq1([]).
draw_line2sq1([TEAM|[SIDES|NONE]]):-
	member('w',SIDES) -> write('-'); write(' ').

draw_line2sq2([]).
draw_line2sq2([TEAM|[SIDES|NONE]]):-
	=(TEAM,'green') -> write('X');
	=(TEAM,'red') -> write('O') ; write(' ').

draw_line2sq3([]).
draw_line2sq3([TEAM|[SIDES|NONE]]):-
	member('e',SIDES) -> write('-'); write(' ').

draw_line3([]).
draw_line3([SQUARE|REST]):-
	draw_line3sq1(SQUARE),
	draw_line3sq2(SQUARE),
	draw_line3sq3(SQUARE),
	write('|'),
	draw_line3(REST).

draw_line3sq1([]).
draw_line3sq1([TEAM|[SIDES|NONE]]):-
	member('sw',SIDES) -> write('/'); write(' ').

draw_line3sq2([]).
draw_line3sq2([TEAM|[SIDES|NONE]]):-
	member('s',SIDES) -> write('|'); write(' ').

draw_line3sq3([]).
draw_line3sq3([TEAM|[SIDES|NONE]]):-
	member('se',SIDES) -> write('\\'); write(' ').

draw_straightLine:-write('-------------------------------------').
