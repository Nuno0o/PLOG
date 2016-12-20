%normalPrint(+Board)
normalPrint(Board):-
    getBiggestN(Board,Size),
    length(Selected,Size),
    domain(Selected,0,0),
    printB(Board,Selected).

solvedPrint(Board,Solution):-
    printB(Board,Solution).


%print(+Board,-Shaded)
printB(Board,Shaded):-
    addWalls(Board,WalledBoard),
    printWalledBoard(WalledBoard,Shaded).


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
printWalledBoard([Line|Board],Shaded):-
    length(Line,Size),
    printBorderWall(Size),nl,
    printWalledBoard_aux([Line|Board],Shaded),
    printBorderWall(Size),nl.

printWalledBoard_aux([],_).
printWalledBoard_aux([Line|Board],Shaded):-
    write('|'),printWalledLine(Line,Shaded),write('|'),nl,
    write('|'),printWalledLine(Line,Shaded),write('|'),nl,
    write('|'),printWalledLine(Line,Shaded),write('|'),nl,
    printWalledBoard_aux2(Board,Shaded).

printWalledBoard_aux2([],_).
printWalledBoard_aux2([Line|Board],Shaded):-
    printWall(Line),nl,
    printWalledBoard_aux(Board,Shaded).

printWalledLine([],_).

printWalledLine([Elem|Rest],Shaded):-
    nth1(Elem,Shaded,0),
    write('   '),
    printWalledLineWall(Rest,Shaded).

printWalledLine([Elem|Rest],Shaded):-
    nth1(Elem,Shaded,1),
    write('XXX'),
    printWalledLineWall(Rest,Shaded).


printWalledLineWall([],_).

printWalledLineWall([1|Rest],Shaded):-
    write(' '),
    printWalledLine(Rest,Shaded).

printWalledLineWall([0|Rest],Shaded):-
    write('|'),
    printWalledLine(Rest,Shaded).



printBorderWall(Size):-
    length(Shaded,Size),
    domain(Shaded,0,0),
    labeling([],Shaded),
    printWall(Shaded).

printWall(Shaded):-
    write('*'),
    printWallAux(Shaded),
    write('*').

printWallAux([]).
printWallAux([0|Rest]):-
    write('---'),
    printWallAuxEdge(Rest).

printWallAux([1|Rest]):-
    write('   '),
    printWallAuxEdge(Rest).

printWallAuxEdge([]).
printWallAuxEdge([_|Rest]):-
    write('*'),
    printWallAux(Rest).




    