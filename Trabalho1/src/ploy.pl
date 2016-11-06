:- use_module(library(system)).
:- include('board.pl').
:- include('menu.pl').
:- include('interface.pl').

ploy:- menu.

%%%%%%%%%%% ROTAÇAO DE PEÇA %%%%%%%%%%%%%%%

% Exemplo de peca: ['red',['s']] %

rotatePiece(Peca,Orientation,PecaNova):-
	rotatePiece_aux(Peca,Orientation,PecaNova).

rotatePiece_aux([Team|[Sides|Rest]],Orientation,[Team|[NewSides|Rest2]]):-
	clockwise(Orientation) -> rotateClock(Sides,NewSides);(
	counterClockwise(Orientation) -> rotateCounterClock(Sides,NewSides)).

rotateClock([],[]).
rotateClock([Side|Rest1],[NewSide|Rest2]):-
	rotateC(Side,NewSide),rotateClock(Rest1,Rest2).

rotateCounterClock([],[]).
rotateCounterClock([Side|Rest1],[NewSide|Rest2]):-
	rotateCC(Side,NewSide),rotateCounterClock(Rest1,Rest2).

%Rotate Clockwise
clockwise(0).
rotateC('n','ne').
rotateC('ne','e').
rotateC('e','se').
rotateC('se','s').
rotateC('s','sw').
rotateC('sw','w').
rotateC('w','nw').
rotateC('nw','n').
%Rotate CounterClockwise
counterClockwise(1).
rotateCC('n','nw').
rotateCC('ne','n').
rotateCC('e','ne').
rotateCC('se','e').
rotateCC('s','se').
rotateCC('sw','s').
rotateCC('w','sw').
rotateCC('nw','w').

%%%%%%%%%%%%% SUBSTITUICAO DE PECA %%%%%%%%%%%%%%%

getPeca(X,Y,Board,Peca):-
	getPeca_aux(X,Y,Board,Peca).

getPeca_aux(_,_,[],_).
getPeca_aux(X,Y,[CurrLine|Rest],Peca):-
	Y == 0 -> getPeca_aux2(X,Y,CurrLine,Peca); (Y1 is Y - 1, getPeca_aux(X,Y1,Rest,Peca)).

getPeca_aux2(_,_,[],_).
getPeca_aux2(X,Y,[CurrPeca|Rest],Peca):-
	X == 0 -> Peca = CurrPeca ; (X1 is X - 1, getPeca_aux2(X1,Y,Rest,Peca)).


setPeca(X,Y,Board,NewBoard,Peca):-
	setPeca_aux(X,Y,Board,NewBoard,Peca).

setPeca_aux(_,_,[],[],_).
setPeca_aux(X,Y,[CurrLine|Rest],[CurrLine2|Rest2],Peca):-
	setPeca_aux2(X,Y,CurrLine,CurrLine2,Peca),
	Y1 is Y-1,
	setPeca_aux(X,Y1,Rest,Rest2,Peca).

setPeca_aux2(_,_,[],[],_).
setPeca_aux2(X,Y,[CurrPeca|Rest],[CurrPeca2|Rest2],Peca):-
	((X = 0, Y = 0) -> CurrPeca2 = Peca ; CurrPeca2 = CurrPeca),
	X1 is X-1,
	setPeca_aux2(X1,Y,Rest,Rest2,Peca).
