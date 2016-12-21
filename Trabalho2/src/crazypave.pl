:- use_module(library(lists)).
:- use_module(library(clpfd)).
:- include('container.pl').
:- include('solver.pl').
:- include('printer.pl').
:- include('interface.pl').

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

test:-cpave1(A),lineRest1(X),colRest1(Y),setAreaByNumber(1,A,B),setAreaByNumber(7,B,C),setAreaByNumber(9,C,D),setAreaByNumber(3,D,E),setAreaByNumber(4,E,F),setAreaByNumber(8,F,G),setAreaByNumber(11,G,H),allConditionsMet(X,Y,H).
test2:-cpave(1,A),lineRest1(X),colRest1(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
test3:-cpave(2,A),lineRest2(X),colRest2(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
test4:-cpave(3,A),lineRest3(X),colRest3(Y),solveGame(A,X,Y,Sol),printB(A,Sol).
crazypave :- 
write('\n*---------------*\n| CRAZYPAVEMENT |\n*---------------*\n'),
interface.


