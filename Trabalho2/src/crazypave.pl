:- use_module(library(lists)).
:- use_module(library(clpfd)).
:- include('container.pl').
:- include('solver.pl').
:- include('printer.pl').
:- include('interface.pl').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

test1:-cpave(1,A),lineRest1(X),colRest1(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
test2:-cpave(2,A),lineRest2(X),colRest2(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
test3:-cpave(3,A),lineRest3(X),colRest3(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
crazypave :- 
write('\n*---------------*\n| CRAZYPAVEMENT |\n*---------------*\n'),
interface.


