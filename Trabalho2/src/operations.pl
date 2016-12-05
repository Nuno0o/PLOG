:- include('container.pl').
:- use_module(library(lists)).
:- use_module(library(clpfd)).


test:-cpave1(A),lineRest1(X),colRest1(Y),setAreaByNumber(1,A,B),setAreaByNumber(7,B,C),setAreaByNumber(9,C,D),setAreaByNumber(3,D,E),setAreaByNumber(4,E,F),setAreaByNumber(8,F,G),setAreaByNumber(11,G,H),allConditionsMet(X,Y,H).
test2:-cpave1(A),lineRest1(X),colRest1(Y),solveGame(A,X,Y,Sol),write(Sol).
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
    getShadedLine_aux(0,Y,Board,0,N).

getShadedLine_aux(Size,_,Board,N,N):-
    nth0(0,Board,Line),
    length(Line,Size).


getShadedLine_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem #< 0,
    N2 #= N1+1,
    X1 #= X+1,
    getShadedLine_aux(X1,Y,Board,N2,N).

getShadedLine_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem #> 0,
    X1 #= X+1,
    getShadedLine_aux(X1,Y,Board,N1,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%getShadedCol(+X,+Board,-N)
getShadedCol(X,Board,N):-
    getShadedCol_aux(X,0,Board,0,N).

getShadedCol_aux(_,Size,Board,N,N):-
    nth0(0,Board,Line),
    length(Line,Size).

getShadedCol_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem #< 0,
    N2 #= N1+1,
    Y1 #= Y+1,
    getShadedCol_aux(X,Y1,Board,N2,N).

getShadedCol_aux(X,Y,Board,N1,N):-
    getCoord(X,Y,Board,Elem),
    Elem #> 0,
    Y1 #= Y+1,
    getShadedCol_aux(X,Y1,Board,N1,N).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%allConditionsMet(+Board).
allConditionsMet(Lines,Cols,Board):-
    allLinesMet(Lines,Board),
    allColsMet(Cols,Board).

allLinesMet([],_).

allLinesMet([Line,N|Rest],Board):-
    getShadedLine(Line,Board,N),
    allLinesMet(Rest,Board).

allColsMet([],_).

allColsMet([Col,N|Rest],Board):-

    getShadedCol(Col,Board,N),
    allColsMet(Rest,Board).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

shadeAllOnList(_,[],Board,Board).
shadeAllOnList(Elem,[1|List],Board,NewBoard):-
    setAreaByNumber(Elem,Board,Board1),
    NextElem #= Elem+1,
    shadeAllOnList(NextElem,List,Board1,NewBoard).

shadeAllOnList(Elem,[0|List],Board,NewBoard):-
    NextElem #= Elem+1,
    shadeAllOnList(NextElem,List,Board,NewBoard).

solveGame(Board,Lines,Cols,Elems):-
    getBiggestN(Board,N),
    length(Elems,N),
    domain(Elems,0,1),
    shadeAllOnList(1,Elems,Board,NewBoard),
    allConditionsMet(Lines,Cols,NewBoard),
    statistics(walltime,_),
    labeling([],Elems),
    fd_statistics.
    