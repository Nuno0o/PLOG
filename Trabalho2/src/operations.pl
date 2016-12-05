:- include('container.pl').
:- use_module(library(lists)).
:- use_module(library(clpfd)).


test:-cpave1(A),setAreaByNumber(1,A,B),setAreaByNumber(7,B,C),setAreaByNumber(9,C,D),setAreaByNumber(3,D,E),setAreaByNumber(4,E,F),setAreaByNumber(8,F,G),setAreaByNumber(11,G,H),allConditionsMet(H).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getCoord(+X,+Y,+Board,-N)
getCoord(X,Y,[_,_|Board],N):-
    nth0(Y,Board,Line),
    nth0(X,Line,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%setAreaByNumber(+N,+Board,-NewBoard)
setAreaByNumber(N,[RLines,RCols|Board],[RLines,RCols|NewBoard]):-
    setArea_aux(Board,N,NewBoard).

%setAreaAtCoord(+X,+Y,+Board,-NewBoard)
setAreaAtCoord(X,Y,[RLines,RCols|Board],[RLines,RCols|NewBoard]):-
    getCoord(X,Y,[_,_|Board],Item),
    setArea_aux(Board,Item,NewBoard).


setArea_aux([],_,[]).
setArea_aux([Line|Tail1],Item,[NewLine|Tail2]):-
    setArea_aux2(Line,Item,NewLine),
    setArea_aux(Tail1,Item,Tail2).

setArea_aux2([],_,[]).
setArea_aux2([Item|Tail1],Item,[NewItem|Tail2]):-
    NewItem is -Item,
    setArea_aux2(Tail1,Item,Tail2).

setArea_aux2([Item1|Tail1],Item,[Item1|Tail2]):-
    Item1 \= Item,
    setArea_aux2(Tail1,Item,Tail2).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

max(N,N1,N):-
    N1 =< N.

max(N1,N,N):-
    N1 < N.

%getBiggestN(+Board,-N)
getBiggestN([_,_|Board],N):-
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
    getShadedLine_aux(0,Y,Board,0,N).

getShadedLine_aux(Size,_,[_,_|Board],N,N):-
    nth0(0,Board,Line),
    length(Line,Size).


getShadedLine_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem < 0,
    N2 is N1+1,
    X1 is X+1,
    getShadedLine_aux(X1,Y,Board,N2,N).

getShadedLine_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem > 0,
    X1 is X+1,
    getShadedLine_aux(X1,Y,Board,N1,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getShadedCol(+X,+Board,-N)
getShadedCol(X,Board,N):-
    getShadedCol_aux(X,0,Board,0,N).

getShadedCol_aux(_,Size,[_,_|Board],N,N):-
    nth0(0,Board,Line),
    length(Line,Size).

getShadedCol_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem < 0,
    N2 is N1+1,
    Y1 is Y+1,
    getShadedCol_aux(X,Y1,Board,N2,N).

getShadedCol_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem > 0,
    Y1 is Y+1,
    getShadedCol_aux(X,Y1,Board,N1,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%allConditionsMet(+Board).
allConditionsMet([Lines,Cols|Board]):-
    allLinesMet(Lines,[Lines,Cols|Board]),
    allColsMet(Cols,[Lines,Cols|Board]).

allLinesMet([],_).

allLinesMet([Line,N|Rest],Board):-
    getShadedLine(Line,Board,N),
    allLinesMet(Rest,Board).

allColsMet([],_).

allColsMet([Col,N|Rest],Board):-

    getShadedCol(Col,Board,N),
    allColsMet(Rest,Board).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

shadeAllOnList([],Board,Board).
shadeAllOnList([Elem|List],Board,NewBoard):-
    setAreaByNumber(Elem,Board,Board1),
    shadeAllOnList(List,Board1,NewBoard).

solveGame(Board,Elems):-
    getBiggestN(Board,N),
    SolLength #> 0,
    SolLength #=< N,
    length(Elems,SolLength),
    domain(Elems,1,N),
    shadeAllOnList(Elems,Board,NewBoard),
    allConditionsMet(NewBoard).
    