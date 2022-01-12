#!/bin/bash

# This file will compile and run the voting system software.
# Authors: Akar Kaung

function pause(){
 read -s -n 1 -p "Press any key to exit"
 echo ""
}

cd ..
javac src/java/vote/main.java src/java/vote/generate_file.java src/java/vote/IR.java src/java/vote/IR_Obj.java src/java/vote/OPL.java src/java/vote/OPL_Obj.java
cd src/java
java vote.main
pause