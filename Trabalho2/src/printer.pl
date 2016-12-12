%normalPrint(+Board)
normalPrint(Board):-
    nth0(0,Board,Line),
    length(Line,Size),
    length(Selected,Size),
    domain(Selected,0,0),
    printB(Board,Selected).

solvedPrint(Board,Solution):-
    printB(Board,Solution).


%print(+Board,-Shaded)
printB(Board,Shaded):-
    shadeAllOnList(1,Shaded,Board,ShadedBoard),
    addWalls(ShadedBoard,WalledBoard).


addWalls(ShadedBoard,WalledBoard):-
    addWallsCols(ShadedBoard,WalledBoard1),
    addWallsLines(WalledBoard1,WalledBoard).

addWallsCols([],[]).
addWallsCols([Line1|Rest1],[Line2|Rest2]):-
    addWallsCols_aux(Line1,Line2),
    addWallsCols(Rest1,Rest2).

addWallsCols_aux([_|[]],[_|[]]).
addWallsCols_aux([Elem1,Elem1|Rest1],[Elem1,1,Elem1|Rest2]):-
    addWallsCols_aux([Elem1|Rest1],[Elem1|Rest2]).

addWallsCols_aux([Elem1,Elem2|Rest1],[Elem1,0,Elem2|Rest2]):-
    Elem2 \= Elem1,
    addWallsCols_aux([Elem2|Rest1],[Elem2|Rest2]).

addWallsLines([_|[]],[_|[]]).
addWallsLines([Line1,Line2|Rest1],[Line1,WallLine,Line2|Rest2]):-
    createWallLine(Line1,Line2,WallLine),
    addWallsLines([Line2|Rest1],[Line2|Rest2]).

createWallLine([],[],[]).
createWallLine([Elem1|Rest1],[Elem1|Rest2],[1|Rest3]):-
    skipElem(Rest1,Rest2,Rest3,NewRest1,NewRest2,NewRest3),
    createWallLine(NewRest1,NewRest2,NewRest3).

createWallLine([Elem1|Rest1],[Elem2|Rest2],[0|Rest3]):-
    Elem1 \= Elem2,
    skipElem(Rest1,Rest2,Rest3,NewRest1,NewRest2,NewRest3),
    createWallLine(NewRest1,NewRest2,NewRest3).

skipElem([],[],[],[],[],[]).
skipElem([_|Rest1],[_|Rest2],[1|Rest3],Rest1,Rest2,Rest3).

%printWalledBoard(+WalledBoard,+Shaded)
printWalledBoard([WalledLine|Rest],Shaded):-
    length(WalledLine,Size),!,
    printBorderLine(Size),nl,
    printWalledBoard_aux([WalledLine|Rest],Shaded),
    printBorderLine(Size).

printWalledBoard_aux([WalledLine|Rest],Shaded):-

    printWalledLine(WalledLine,Shaded),
    printWallLine(WalledLine),
    printWalledBoard_aux(Rest,Shaded).





printBorderLine(Size):-
    write('-'),
    Size1 is Size-1,
    printBorderLine_aux(Size1).

printBorderLine_aux(Size):-
    write('---'),
    Size1 is Size-1,
    printBorderLine(Size1).


    