getChar(Input):-
get_char(Input),
get_char(_).

getInt(Input):-
getChar(Input1),
char_code(Input1,Code),
Input is Code - 48.

interface:-
write('Board to solve [1..1]:\n'),
getInt(N),
\=cpave(N,B)->interface,
normalPrint(B),
length(B,height),
B = [L|_],
length(L,width),
write('Column restictions (-1 to continue):\n'),
colRest = [],
repeat,
colRestTmp = colRest,
getInt(N),
(
N > -1,
N < width
) -> append(colRestTmp,[N],colRest),
N \= -1 -> fail,
write('Line restictions (-1 to continue):\n'),
lineRest = [],
repeat,
lineRestTmp = lineRest,
getInt(N),
(
N > -1,
N < height
) -> append(lineRestTmp,[N],lineRest),
N \= -1 -> fail,
solveGame(B,lineRest,colRest,Sol),
printB(B,Sol),
interface.



