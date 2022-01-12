#!/bin/bash

# This file will clean all the audit and result files which were generated while the software was running.
# Authors: Akar Kaung

SCRIPT=$(readlink -f "$0")
BASEDIR=$(dirname "$SCRIPT")
cd $BASEDIR/..
rm Audit_File_*.txt Result_File_*.txt test_*.txt invalidated_*.txt src/java/Audit_File_*.txt src/java/Result_File_*.txt src/java/invalidated_*.txt src/java/test_*.txt src/java/vote/*.class