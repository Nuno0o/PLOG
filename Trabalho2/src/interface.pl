getChar(Input):-
	read(Input),
	get_char(_).

getInt(Input):-
	read(Input),
	get_char(_).

chooseBoard(B):-
	cpave(A,_),
	\+ (
	cpave(B,_),
	B > A
	),
	format('Board to solve [1..~d]: ',[A]),
	repeat,
	getInt(N),
	%read(N),
	N > 0,
	N =< A,
	cpave(N,B).

chooseRestAux1(Z,Rest):-
	write('Index: '),
	getInt(N),
	checkNumber1(Z,N,Rest).

checkNumber1(Z,N,[N|Y]):-
	N > -1,N < Z,
	chooseRestAux2(Z,Y).

checkNumber1(_,_,[]).

chooseRestAux2(Z,Rest):- 
	write('Restriction: '),
	getInt(N),
	checkNumber2(Z,N,Rest).

checkNumber2(Z,N,[N|Y]):-
	N > -1,N < Z,
	chooseRestAux1(Z,Y).

checkNumber2(_,_,[]).

chooseColRest(B,ColRest):-
	nth0(0,B,L),
	length(L,W),
	format('Column restictions [0..~d] (-1 to continue):\n',[W]),
	chooseRestAux1(W,ColRest).

chooseLineRest(B,LineRest):-
	length(B,H),
	format('Line restictions [0..~d] (-1 to continue):\n',[H]),
	chooseRestAux1(H,LineRest).

interface:-
	chooseBoard(B),
	normalPrint(B),nl,
	chooseLineRest(B,LineRest),
	chooseColRest(B,ColRest),!,
	nl,
	(solveGame(B,LineRest,ColRest,Sol)->(printB(B,Sol),nl);(write('No solution found'),nl)),
	interface.