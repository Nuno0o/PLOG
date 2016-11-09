board([
[['empty',[]],['green',['w','s','e']],['green',['sw','se','n']],['green',['sw','s','se']],['green',['sw','se','ne','nw']],['green',['sw','se','s']],['green',['sw','se','n']],['green',['w','s','e']],['empty',[]]],
[['empty',[]],['empty',[]],['green',['s','se']],['green',['sw','se']],['green',['s','n']],['green',['sw','se']],['green',['sw','s']],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['green',['s']],['green',['s']],['green',['s']],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['red',['n']],['red',['n']],['red',['n']],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['red',['n','ne']],['red',['nw','ne']],['red',['s','n']],['red',['nw','ne']],['red',['nw','n']],['empty',[]],['empty',[]]],
[['empty',[]],['red',['w','n','e']],['red',['nw','ne','s']],['red',['nw','n','ne']],['red',['sw','se','ne','nw']],['red',['nw','ne','n']],['red',['nw','ne','s']],['red',['w','n','e']],['empty',[]]]]).

boardFinal([
[['empty',[]],['empty',[]],['empty',[]],['green',['sw','se','ne','nw']],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['empty',[]],['red',['n']],['red',['n']],['red',['n']],['empty',[]],['empty',[]],['empty',[]]],
[['empty',[]],['empty',[]],['red',['n','ne']],['red',['nw','ne']],['red',['s','n']],['red',['nw','ne']],['red',['nw','n']],['empty',[]],['empty',[]]],
[['empty',[]],['red',['w','n','e']],['red',['nw','ne','s']],['red',['nw','n','ne']],['red',['sw','se','ne','nw']],['red',['nw','ne','n']],['red',['nw','ne','s']],['red',['w','n','e']],['empty',[]]]]).
vazio(['empty',[]]).

draw_gameboard:-X^(board(X),draw_board(X)).

draw_board(Tab):-
  nl,
  write(' | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 |'),nl,
	draw_straightLine,nl,
	draw_lines(Tab,0).

draw_lines([],_).
draw_lines([LIN|REST],LineNumber):-(
	write(' |'),draw_line1(LIN),nl,
  write(LineNumber),
	write('|'),draw_line2(LIN),nl,
	write(' |'),draw_line3(LIN),nl,
	draw_straightLine,
	nl,
  LineNumber2 is LineNumber+1,
	draw_lines(REST,LineNumber2)).

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
	=(TEAM,'green') -> write('G');
	=(TEAM,'red') -> write('R') ; write(' ').

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

draw_straightLine:-write('---------------------------------------').
