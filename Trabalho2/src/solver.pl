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