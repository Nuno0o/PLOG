getChar(Input):-
	get_char(Input),
	get_char(_).

getInt(Input):-
	getChar(Input1),
	char_code(Input1,Code),
	Input is Code - 48.

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

chooseRestAux1(Z,[X|Y]):-
write('Index: '),
%read(N),
getInt(N),!,
(N > -1, N < Z) -> (X = N, chooseRestAux2(Z,Y));
N \= -1 -> chooseRestAux2(Z,[X|Y]);
N = -1.


chooseRestAux2(Z,[X|Y]):-
write('Restriction: '),
%read(N),
getInt(N),!,
(N > -1, N < Z) -> (X = N, chooseRestAux1(Z,Y));
N \= -1 -> chooseRestAux1(Z,[X|Y]);
N = -1.

chooseColRest(B,ColRest):-
length(B,H),
nth0(0,B,L),
length(L,W),
format('Column restictions [0..~d] (-1 to continue):\n',[W]),!,
chooseRestAux1(W,ColRest).

chooseLineRest(B,LineRest):-
length(B,H),
format('Line restictions [0..~d] (-1 to continue):\n',[H]),!,
chooseRestAux1(H,LineRest).

interface:-
chooseBoard(B),
%normalPrint(B),nl,
chooseLineRest(B,ListRest),
chooseColRest(B,ColRest),
solveGame(B,LineRest,ColRest,Sol),
printB(B,Sol),
get_char(_),
interface.