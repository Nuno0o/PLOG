:- include('container.pl').
:- include('solver.pl').
:- include('printer.pl').
:- include('interface.pl').
:- use_module(library(lists)).
:- use_module(library(clpfd)).
=======

test:-cpave1(A),lineRest1(X),colRest1(Y),setAreaByNumber(1,A,B),setAreaByNumber(7,B,C),setAreaByNumber(9,C,D),setAreaByNumber(3,D,E),setAreaByNumber(4,E,F),setAreaByNumber(8,F,G),setAreaByNumber(11,G,H),allConditionsMet(X,Y,H).
test2:-cpave(1,A),lineRest1(X),colRest1(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
crazypave :- interface.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getCoord(+X,+Y,+Board,-N)
getCoord(X,Y,Board,N):-
    nth0(Y,Board,Line),
    nth0(X,Line,N).

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

%getShadedLine(+Y,+Board,+N,+Elems)
getShadedLine(Y,Board,N,Elems):-
    getShadedLine_aux(0,Y,Board,N,Elems).

getShadedLine_aux(Size,_,[Line|_],0,_):-
    length(Line,Size).

getShadedLine_aux(X,Y,Board,N,Elems):-
    getCoord(X,Y,Board,Elem),
    nth1(Elem,Elems,1),
    N1 #= N-1,
    X1 #= X+1,
    getShadedLine_aux(X1,Y,Board,N1,Elems).

getShadedLine_aux(X,Y,Board,N,Elems):-
    getCoord(X,Y,Board,Elem),
    nth1(Elem,Elems,0),
    N1 #= N,
    X1 #= X+1,
    getShadedLine_aux(X1,Y,Board,N1,Elems).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getShadedCol(+X,+Board,-N)
getShadedCol(X,Board,N,Elems):-
    getShadedCol_aux(X,0,Board,N,Elems).

getShadedCol_aux(_,Size,[Line|_],0,_):-
    length(Line,Size).

getShadedCol_aux(X,Y,Board,N,Elems):-
    getCoord(X,Y,Board,Elem),
    nth1(Elem,Elems,1),
    N1 #= N-1,
    Y1 #= Y+1,
    getShadedCol_aux(X,Y1,Board,N1,Elems).

getShadedCol_aux(X,Y,Board,N,Elems):-
    getCoord(X,Y,Board,Elem),
    nth1(Elem,Elems,0),
    N1 #= N,
    Y1 #= Y+1,
    getShadedCol_aux(X,Y1,Board,N1,Elems).