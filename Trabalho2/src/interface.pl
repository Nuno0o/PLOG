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
format('Board to solve [1..~c]: ',[A]),
repeat,
getInt(N),
N > 0,
N < A + 1,
cpave(N,B).

chooseColRest(B,ColRest):-
length(B,H),
nth0(0,B,L),
length(L,W),
length(ColRest,0),
format('Column restictions [0..~c] (-1 to continue):\n',[W]),
repeat,
ColRestTmp = ColRest,
write(ColRestTmp),nl,
getInt(N),
(
N > -1,
N < W
) ->
append(ColRestTemp,[N],ColRest),
(
(N =\= -1) -> fail;
true
).
chooseLineRest(B,LineRest):-
length(B,H),
length(LineRest,0),
format('Line restictions [0..~c] (-1 to continue):\n',[H]),
repeat,
LineRestTmp = LineRest,
write(LineRestTmp),nl,
getInt(N),
(
N > -1,
N < W
) ->
append(LineRestTemp,[N],LineRest),
(
N = -1;
fail
).


interface:-
chooseBoard(B),
%normalPrint(B),
chooseColRest(B,ColRest),
chooseLineRest(N,ListRest),
solveGame(B,lineRest,colRest,Sol),
printB(B,Sol),
interface.



