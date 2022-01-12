#!/bin/bash

# This file will compile and run the voting system software.
# Authors: Akar Kaung

SCRIPT=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT")
cd $BASEDIR/..
javac src/java/vote/main.java src/java/vote/generate_file.java src/java/vote/IR.java src/java/vote/IR_Obj.java src/java/vote/OPL.java src/java/vote/OPL_Obj.java src/java/vote/utilities.java
cd src/java
java vote.main