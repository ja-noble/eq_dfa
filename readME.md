# Description

This is a project made for my Theory of Computation class to check
whether two Deterministic Finite Autamata accepts the same language.

# Usage

To run the algorithm by itself, run `Simulator.java`. In this file, you can adjust the files read to input your own DFA's.

# Inputting Original DFA's

Inputting original DFA's will require a specific formatting of a .txt file, as follows:

1. Separate each name of each state by line.
2. Place a comma on a new line to indicate it is the end of the set of states.
3. Separate each character of the alphabet by line.
4. Place a comma on a new line to indicate it is the end of the input alphabet.
5. Separate each name of each acceptance state by line.
6. Place a comma on a new line to indicate it is the end of the set of acceptance states.
7. Build the transitions as follows:
    a. Place the start state first on a new line
    b. Place the input required on a new line
    c. Place the end state on a new line
    d. Repeat for all transitions.
8. Place a comma on a new line to indicate it is the end of the set of transitions.
9. Place the name of the startState at the end of the file.
