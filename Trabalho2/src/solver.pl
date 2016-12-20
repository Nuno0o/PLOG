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

solveGame(Board,Lines,Cols,Elems):-
    getBiggestN(Board,N),
    length(Elems,N),
    domain(Elems,0,1),
    allConditionsMet(Lines,Cols,Board,Elems),
    statistics(walltime,_),
    labeling([],Elems),
    fd_statistics.