solveGame(Board,Lines,Cols,Elems):-
    getBiggestN(Board,N),
    length(Elems,N),
    domain(Elems,0,1),
    allConditionsMet(Lines,Cols,Board,Elems),
    statistics(walltime,_),
    labeling([],Elems),
    fd_statistics.

%allConditionsMet(+Board).
allConditionsMet(Lines,Cols,Board,Elems):-
    allLinesMet(Lines,Board,Elems),
    allColsMet(Cols,Board,Elems).

allLinesMet([],_,_).

allLinesMet([Line,N|Rest],Board,Elems):-
    getShadedLine(Line,Board,N,Elems),
    allLinesMet(Rest,Board,Elems).

allColsMet([],_,_).

allColsMet([Col,N|Rest],Board,Elems):-

    getShadedCol(Col,Board,N,Elems),
    allColsMet(Rest,Board,Elems).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

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