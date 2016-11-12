:- use_module(library(system)).
:- use_module(library(between)).
:- use_module(library(random)).
:- include('board.pl').
:- include('menu.pl').
:- include('util.pl').

ploy:- menu.

%play
play:-
(
	board(B),
	playCycle(0,'red',B)
).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%ciclo do jogo
playCycle(N,Team,Board):-
player_plays(Board,Team,NewBoard),
NextN is N+1,
(assertGameEnded(NewBoard,WinnerTeam) -> endGame(WinnerTeam); (switchTeam(Team,NextTeam),playCycle(NextN,NextTeam,NewBoard))).

assertTeam([Team|_],Team).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%switchTeam(+Team,-NextTeam)
switchTeam(Team,NextTeam):-
(
  Team = 'red' -> NextTeam = 'green'; NextTeam = 'red'
).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%player_plays(+Board,+Team,-NewBoard)
player_plays(Board,Team,NewBoard):-
	jogador(Team,HumanOrBot),
	(HumanOrBot = 'human' -> human_plays(Board,Team,NewBoard) ; bot_plays(Board,Team,NewBoard)).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%human_plays(+Board,+Team,-NewBoard)
human_plays(Board,Team,NewBoard):-
	repeat,
	nl,nl,
	write('             '),write(Team),write(' team turn'),nl,
	draw_board(Board),
	getXY(X,Y),
	getPiece(X,Y,Board,Piece),
	assertTeam(Piece,Team),
	chooseOptions(Piece,Move,Rotate),
	(
	(Rotate = 1 , Move = 0) -> chooseRotate(Board, X, Y, NewBoard);
	(Rotate = 0 , Move = 1) -> chooseMove(Board, X, Y, NewBoard);
	(Rotate = 1 , Move = 1) -> (chooseMove(Board, X, Y, IntBoard),chooseRotate(IntBoard, X, Y, NewBoard));
	true
	),
	!
.

getXY(X,Y):-
	write('X - '),
	read(Input),
	X = Input,
	get_char(_),
	write('Y - '),
	read(Input2),
	Y = Input2.

chooseOptions([_|[Orientations|_]],Move,Rotate):-
	length(Orientations,Length),
	(Length = 1 -> write('Move(1) Rotate(2) Both(3)\n'); write('Move(1) Rotate(2)\n')),
	read(Input),
	(
	Input = 1 -> (Move = 1,Rotate = 0);
	Input = 2 -> (Move = 0,Rotate = 1);
	(Input = 3, Length = 1) -> (Move = 1,Rotate = 1);
	false
	)
.

chooseRotate(Board, X, Y, NewBoard):-
	getPiece(X,Y,Board,Piece),
	write('(1)Clockwise (2)CounterClockwise: '),
	read(Input),
	Angle is Input-1,
	rotatePiece(Piece,Angle,NewPiece),setPiece(X,Y,Board,NewBoard,NewPiece)
.

chooseMove(Board, X, Y, NewBoard):-
	getPiece(X,Y,Board,Piece),
	write('Orientation(n,s,w,e,nw,ne,sw,se): '),
	read(InputOri),
	write('Length(1-3): '),
	read(InputLen),
	Length = InputLen,
	movePiece(X,Y,InputOri,Length,Board,NewBoard,_)
	.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%bot_plays(+Board,-NewBoard)
bot_plays(Board,Team,NewBoard):-
	difficulty(Dif),
	bot_plays_diff(Dif,Board,Team,NewBoard)
.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%modo aleatorio
bot_plays_diff(Dif,Board,Team,NewBoard):-
	Dif = 0,
	repeat,
	getRandomXY(X,Y),
	getPiece(X,Y,Board,Piece),
	assertTeam(Piece,Team),
	% MoveOrRotate = 0 -> roda, 1-3 -> move
	random(0,3,MoveOrRotate),
	moveOrRotate(MoveOrRotate,Board,Team,X,Y,Piece,NewBoard),
	!
.
%getRandomXY(-X,-Y)
getRandomXY(X,Y):-
	random(0,8,X),
	random(0,8,Y)
.
%getRandomOriAndLength(+Piece,-Orientation,-Length)
getRandomOriAndLength([_|[Orientations|_]],Orientation,Length):-
	random_member(Orientation,Orientations),
	length(Orientations,L),
	random(1,L,Length)
.
%move
moveOrRotate(MoveOrRotate,Board,Team,X,Y,Piece,NewBoard):-
	between(1,3,MoveOrRotate),
	getRandomOriAndLength(Piece,Orientation,Length),
	movePiece(X,Y,Orientation,Length,Board,NewBoard,_)
.
%rotate
moveOrRotate(MoveOrRotate,Board,Team,X,Y,Piece,NewBoard):-
	MoveOrRotate = 0,
	%% É feito um movimento para um tabuleiro nao usado para que a probabilidade de rotate falhar seja igual à de move falhar
	getRandomOriAndLength(Piece,Orientation,Length),
	movePiece(X,Y,Orientation,Length,Board,_,_),
	random(0,1,Angle),
	rotatePiece(Piece,Angle,NewPiece),
	setPiece(X,Y,Board,NewBoard,NewPiece)
.



%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%modo ganancioso
bot_plays_diff(Dif,Board,Team,NewBoard):-
	Dif = 1,
	findGreedyPlay(Board,Team,X,Y,Orientation,Length,Consumed)
.
%findGreedyPlay(+Board,+Team,-X,-Y,-Orientation,-Length,-Consumed)
findGreedyPlay(Board,Team,X,Y,Orientation,Length,Consumed):-
	findGreedyPlay_Y(0,Board,Team,X,Y,Orientation,Length,Consumed).

findGreedyPlay_Y(CurrY,Board,Team,X,Y,Orientation,Length,Consumed):-
	Consumed = -1,
	CurrY = 9.

findGreedyPlay_Y(CurrY,Board,Team,X,Y,Orientation,Length,Consumed):-
	CurrY \= 9,
	findGreedyPlay_X(0,CurrY,Board,Team,X,Y,Orientation,Length,Consumed),
	CurrY1 is CurrY+1,
	findGreedyPlay_Y(CurryY1,Board,Team,X,Y,Orientation,Length,BConsumed).

findGreedyPlay_X(CurrX,CurrY,Board,Team,X,Y,Orientation,Length,Consumed):-
	Consumed = -1,
	CurrX = 9.

findGreedyPlay_X(CurrX,CurrY,Board,Team,X,Y,Orientation,Length,Consumed):-
	CurrX \= 9,
	getPiece(CurrX,CurrY,Board,[Team|[Ori|_]]),
	findGreedyPlay_Ori(CurrX,CurrY,Ori,Board,Team,X,Y,Orientation,Length,Consumed)
	CurrX1 is CurrX+1,
	findGreedyPlay_X(CurrX1,CurrY,Board,Team,X,Y,Orientation,Length,Consumed).

findGreedyPlay_Ori(_,_,[],_,_,_,_,_,_,_,Consumed):-
	Consumed = -1.

findGreedyPlay_Ori(CurrX,CurrY,[CurrOri|Rest],Board,Team,X,Y,Orientation,Length,Consumed):-
	length(Rest,Length1),
	MaxLength is Length1+1,
	findGreedyPlay_Len(MaxLength,Board,Team,X,Y,Orientation,Length,Consumed),
	findGreedyPlay_Ori(CurrX,CurrY,Rest,Board,Team,X,Y,Orientation,Length,Consumed).

findGreedyPlay_Len(MaxLength,Board,Team,X,Y,Orientation,Length,Consumed):-
	Consumed = -1,
	Length is MaxLength + 1.

findGreedyPlay_Len(MaxLength,Board,Team,X,Y,Orientation,Length,Consumed):-
	Length =< MaxLength,
	nDirections(Length,Len).
	movePiece(X,Y,Orientation,Len,Board,_,Consumed),
	Length1 is Length+1,
	findGreedyPlay_len(MaxLength,Board,Team,X,Y,Orientation,Length1,Board,Consumed).

assertHighest(X1,Y1,Ori1,Len1,Cons1,X2,Y2,Ori2,Len2,Cons2,X,Y,Ori,Len,Cons):-
	Cons1 >= Cons2,
	X = X1,
	Y = Y1,
	Ori = Ori1,
	Len = Len1,
	Cons = Cons1
.

assertHighest(X1,Y1,Ori1,Len1,Cons1,X2,Y2,Ori2,Len2,Cons2,X,Y,Ori,Len,Cons):-
	Cons1 < Cons2,
	X = X2,
	Y = Y2,
	Ori = Ori2,
	Len = Len2,
	Cons = Cons2
.





%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%endGame(+WinnerTeam)
endGame(WinnerTeam):-
	nl,nl,write(WinnerTeam), write(' won the game!').


%%%%%%%%%%% ROTAÇAO DE PEÇA %%%%%%%%%%%%%%%

% Exemplo de Piece: ['red',['s']] %

rotatePiece(Piece,Orientation,PieceNova):-
between(0,1,Orientation),
rotatePiece_aux(Piece,Orientation,PieceNova).

rotatePiece_aux([Team|[Sides|Rest]],Orientation,[Team|[NewSides|Rest2]]):-
	rotatePiece_aux2(Orientation,Sides,NewSides).

rotatePiece_aux2(_,[],[]).
rotatePiece_aux2(Orientation,[Side|Rest1],[NewSide|Rest2]):-
	clockwise(Orientation),
	rotateC(Side,NewSide),rotatePiece_aux2(Orientation,Rest1,Rest2).

rotatePiece_aux2(Orientation,[Side|Rest1],[NewSide|Rest2]):-
	counterClockwise(Orientation),
	rotateCC(Side,NewSide),rotatePiece_aux2(Orientation,Rest1,Rest2).

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

%%%%%%%%%%%%% MANIPULACAO DE PEÇAS %%%%%%%%%%%%%%%

%getPiece(+X,+Y,+Board,-Piece)
getPiece(X,Y,Board,Piece):-
	between(-1,9,X),
	between(-1,9,Y),
	getPiece_aux(0,X,Y,Board,Piece).

getPiece_aux(_,_,_,[],_).
getPiece_aux(Y,X,Y,[CurrLine|Rest],Piece):-
	getPiece_aux2(0,X,Y,CurrLine,Piece).

getPiece_aux(N,X,Y,[CurrLine|Rest],Piece):-
	N \= Y,
	Y1 is N + 1,
	getPiece_aux(Y1,X,Y,Rest,Piece).

getPiece_aux2(_,_,_,[],_).
getPiece_aux2(X,X,Y,[CurrPiece|Rest],Piece):-
	Piece = CurrPiece.

getPiece_aux2(N,X,Y,[CurrLine|Rest],Piece):-
	N \= X,
	X1 is N + 1,
	getPiece_aux2(X1,X,Y,Rest,Piece).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%setPiece(+X,+Y,+Board,-NewBoard,+Piece)
setPiece(X,Y,Board,NewBoard,Piece):-
setPiece_aux(X,Y,Board,NewBoard,Piece).

setPiece_aux(_,_,[],[],_).
setPiece_aux(X,Y,[CurrLine|Rest],[CurrLine2|Rest2],Piece):-
	setPiece_aux2(X,Y,CurrLine,CurrLine2,Piece),
	Y1 is Y-1,
	setPiece_aux(X,Y1,Rest,Rest2,Piece).

setPiece_aux2(_,_,[],[],_).
setPiece_aux2(X,Y,[CurrPiece|Rest],[CurrPiece2|Rest2],Piece):-
	X = 0, Y = 0,
	CurrPiece2 = Piece,
	X1 is X-1,
	setPiece_aux2(X1,Y,Rest,Rest2,Piece).

setPiece_aux2(X,Y,[CurrPiece|Rest],[CurrPiece2|Rest2],Piece):-
	(X \= 0; Y \= 0),
	CurrPiece2 = CurrPiece,
	X1 is X-1,
	setPiece_aux2(X1,Y,Rest,Rest2,Piece).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%movePiece(+X,+Y,+Orientation,+Length,+Board,-NewBoard,-Consumed) consumed= length da peça que foi consumida(0 empty, 1 shield, etc)
movePiece(X,Y,Orientation,Length,Board,NewBoard,Consumed):-
getPiece(X,Y,Board,Piece),
calcEndPoint(X,Y,Orientation,Length,Xf,Yf),
assertCanMove(Piece,X,Y,Xf,Yf,Orientation,Length,Board),
getPiece(Xf,Yf,Board,[_|[ConsOri|_]]),
length(ConsOri,Consumed),
setPiece(Xf,Yf,Board,IntBoard,Piece),
vazio(Vazio),
setPiece(X,Y,IntBoard,NewBoard,Vazio).


assertCanMove(Piece,X,Y,Xf,Yf,Orientation,Length,Board):-
assertHasOrientation(Orientation,Piece),
assertValidLength(Length,Piece),
assertInsideBoundaries(Xf,Yf),
assertNoColision(X,Y,Orientation,Length,Board).

assertHasOrientation(Orientation,Piece):-
assertHasOrientation_aux(Orientation,Piece).

assertHasOrientation_aux(Orientation,[_|[Orientations|_]]):-
member(Orientation,Orientations).

assertValidLength(Length,Piece):-
assertValidLength_aux(Length,Piece).

assertValidLength_aux(Length,[_|[Orientations|_]]):-
length(Orientations,NDirections),
nDirections(NDirections,MaxLength),
Length =< MaxLength.

nDirections(1,1).
nDirections(2,2).
nDirections(3,3).
nDirections(4,1).
%Coord multipliers
multiplierX('s',0).
multiplierX('n',0).
multiplierX('e',1).
multiplierX('w',-1).
multiplierX('ne',1).
multiplierX('se',1).
multiplierX('nw',-1).
multiplierX('sw',-1).

multiplierY('s',1).
multiplierY('n',-1).
multiplierY('e',0).
multiplierY('w',0).
multiplierY('ne',-1).
multiplierY('se',1).
multiplierY('nw',-1).
multiplierY('sw',1).


assertInsideBoundaries(Xf,Yf):-
Xf < 9,
Xf > -1,
Yf < 9,
Yf > -1.

%calcEndPoint(+X,+Y,+Orientation,+Length,-Xf,-Yf)
calcEndPoint(X,Y,Orientation,Length,Xf,Yf):-
multiplierX(Orientation,MultX),
multiplierY(Orientation,MultY),
Xf is X + Length*MultX,
Yf is Y + Length*MultY.

assertNoColision(X,Y,Orientation,Length,Board):-
calcEndPoint(X,Y,Orientation,Length,Xf,Yf),
getPiece(X,Y,Board,Piece1),
getPiece(Xf,Yf,Board,Piece2),
assertDifferentTeam(Piece1,Piece2),
Length1 is Length - 1,
(Length > 1 -> assertNoColision_inter(X,Y,Orientation,Length1,Board) ; true).

assertNoColision_inter(X,Y,Orientation,Length,Board):-
Length > 0 ->(
	calcEndPoint(X,Y,Orientation,Length,Xf,Yf),
	getPiece(Xf,Yf,Board,Piece),
	assertEmpty(Piece),
	Length1 is Length - 1,
	assertNoColision_inter(X,Y,Orientation,Length1,Board)
); true.

assertEmpty([Team|_]):-
Team == 'empty'.

assertDifferentTeam([Team1|_],[Team2|_]):-
Team1 \= Team2.

%%%%%%%%%%%%% VERIFICA SE JOGO ACABOU %%%%%%%%%%%%%%%

%assertGameEnded(+Board,-Team)
assertGameEnded(Board,Team):-
(assertCommanderDead(Board,'red')-> Team = 'green';
assertCommanderDead(Board,'green')-> Team = 'red';
assertAllSmallDead(Board,'red')-> Team = 'green';
assertAllSmallDead(Board,'green')-> Team = 'red';
false).

assertCommanderDead(Board,Team):-
assertCommanderDead_aux(Board,Team,0).

assertCommanderDead_aux(Board,Team,Y):-
assertCommanderDead_aux2(Board,Team,0,Y),
Y1 is Y+1,
(Y < 8 -> assertCommanderDead_aux(Board,Team,Y1);true).

assertCommanderDead_aux2(Board,Team,X,Y):-
getPiece(X,Y,Board,[TeamO|[Orientations|_]]),
length(Orientations,Length),
(Team = TeamO -> Length \= 4; true),
X1 is X+1,
(X < 8 -> assertCommanderDead_aux2(Board,Team,X1,Y);true).

assertAllSmallDead(Board,Team):-
assertAllSmallDead_aux(Board,Team,0).

assertAllSmallDead_aux(Board,Team,Y):-
assertAllSmallDead_aux2(Board,Team,0,Y),
Y1 is Y+1,
(Y < 8 -> assertAllSmallDead_aux(Board,Team,Y1);true).

assertAllSmallDead_aux2(Board,Team,X,Y):-
getPiece(X,Y,Board,[TeamO|[Orientations|_]]),
length(Orientations,Length),
(Team = TeamO -> Length == 4; true),
X1 is X+1,
(X < 8 -> assertAllSmallDead_aux2(Board,Team,X1,Y);true).
