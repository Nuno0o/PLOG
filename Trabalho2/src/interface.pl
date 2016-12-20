chooseBoard(B):-
cpave(A,_),
\+ (
cpave(B,_),
B > A
),
format('Board to solve [1..~d]: ',[A]),
repeat,
%getInt(N),
read(N),
N > 0,
N =< A,
cpave(N,B).

chooseRestAux(Z,[X|Y]):-
read(N),!,
(N > -1, N < Z) -> (X = N, chooseRestAux(Z,Y));
N \= -1 -> chooseRestAux(Z,[X|Y]);
N = -1.

chooseColRest(B,ColRest):-
length(B,H),
nth0(0,B,L),
length(L,W),
format('Column restictions [0..~d] (-1 to continue):\n',[W]),!,
chooseRestAux(W,ColRest).

chooseLineRest(B,LineRest):-
length(B,H),
format('Line restictions [0..~d] (-1 to continue):\n',[H]),!,
chooseRestAux(H,LineRest).

interface:-
chooseBoard(B),
%normalPrint(B),nl,
chooseLineRest(B,ListRest),
chooseColRest(B,ColRest),
solveGame(B,LineRest,ColRest,Sol),
printB(B,Sol),
get_char(_),
interface.