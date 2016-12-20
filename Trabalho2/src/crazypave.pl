:- include('container.pl').
:- include('solver.pl').
:- include('printer.pl').
:- include('interface.pl').
:- use_module(library(lists)).
:- use_module(library(clpfd)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

test:-cpave1(A),lineRest1(X),colRest1(Y),setAreaByNumber(1,A,B),setAreaByNumber(7,B,C),setAreaByNumber(9,C,D),setAreaByNumber(3,D,E),setAreaByNumber(4,E,F),setAreaByNumber(8,F,G),setAreaByNumber(11,G,H),allConditionsMet(X,Y,H).
test2:-cpave(1,A),lineRest1(X),colRest1(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
crazypave :- interface.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getCoord(+X,+Y,+Board,-N)
getCoord(X,Y,Board,N):-
    nth0(Y,Board,Line),
    nth0(X,Line,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%setAreaByNumber(+N,+Board,-NewBoard)
setAreaByNumber(N,Board,NewBoard):-
    setArea_aux(Board,N,NewBoard).

%setAreaAtCoord(+X,+Y,+Board,-NewBoard)
setAreaAtCoord(X,Y,Board,NewBoard):-
    getCoord(X,Y,Board,Item),
    setArea_aux(Board,Item,NewBoard).


setArea_aux([],_,[]).
setArea_aux([Line|Tail1],Item,[NewLine|Tail2]):-
    setArea_aux2(Line,Item,NewLine),
    setArea_aux(Tail1,Item,Tail2).

setArea_aux2([],_,[]).
setArea_aux2([Item|Tail1],Item,[NewItem|Tail2]):-
    NewItem #= -Item,
    setArea_aux2(Tail1,Item,Tail2).

setArea_aux2([Item1|Tail1],Item,[Item1|Tail2]):-
    Item1 #\= Item,
    setArea_aux2(Tail1,Item,Tail2).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

max(N,N1,N):-
    N1 =< N.

max(N1,N,N):-
    N1 < N.

%getBiggestN(+Board,-N)
getBiggestN(Board,N):-
    getBiggestN_aux(Board,N).

getBiggestN_aux([],0).
getBiggestN_aux([Line|Rest],N):-
    getBiggestN_aux2(Line,N1),
    getBiggestN_aux(Rest,N2),
    max(N1,N2,N).

getBiggestN_aux2(Line,N):-
    max_member(N,Line).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getShadedLine(+Y,+Board,-N)
getShadedLine(Y,Board,N):-
    getShadedLine_aux(0,Y,Board,N).

getShadedLine_aux(Size,_,[Line|_],0):-
    length(Line,Size).

getShadedLine_aux(X,Y,Board,N):-
    getCoord(X,Y,Board,Elem),
    Elem #< 0 #<=> B,
    N1 #= N-B,
    X1 #= X+1,
    getShadedLine_aux(X1,Y,Board,N1).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getShadedCol(+X,+Board,-N)
getShadedCol(X,Board,N):-
    getShadedCol_aux(X,0,Board,N).

getShadedCol_aux(_,Size,[Line|_],0):-
    length(Line,Size).

getShadedCol_aux(X,Y,Board,N):-
    getCoord(X,Y,Board,Elem),
    Elem #< 0 #<=> B,
    N1 #= N-B,
    Y1 #= Y+1,
    getShadedCol_aux(X,Y1,Board,N1).

    