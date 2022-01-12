# Voting System Program
This program can run three different type of elections. <br>
IR, OPL and PO, all type of elections must specify inside the voting file.<br>
**Important**: All the vote files must be inside ~/src/ballots/ folder

# To Run the program
**Run any of the two methods below from terminal instead of IDE to get our desire output location.**<br>
**Before you start, you need to copy all .csv files from  ~/testing/testingVoteFiles/ to ~/src/ballots directory OR
you can create your own test but must under ~/src/ballots directory.** <br>
(Method 1) (*Recommended method*) <br>
Run the ~/misc/Run.sh file. <br><br>
(Method 2) <br>
Compile all the files inside ~/src/java/vote/ <br>
Run the main.class from ~/src/java/vote/

# Audit and Result files
Audit and result files will be generated at ~/Project2/src/java/ with the name of:<br>
Audit file: Audit_File_*hh:mm:ss*.txt<br>
Result file: Result_File_*hh:mm:ss*.txt<br>
Invalid ballot file: invalidated_*hh:mm:ss*.txt<br> 
(*hh:mm:ss* means the time that the user started to run the program)

# Testing
**Copy the documents from ~/test/testingVoteFiles/ to ~/src/ballots/ <br>
Test cases will not work if the files were not moved.<br>
The **audit** and **result** file that is produced from the testing can be found at the top layer folder (**if run from IDE**). <br>
After all the test cases have finished, run ~/misc/clean.sh to clean the audit,result and invalid files that were generated during the test run.

# Testing log
Testing log report is under ~/src/testing/ directory.

# Documentation
Documentation of the codes can be open with the file below:<br>
~/documentation/javaDoc/allclasses-index.html

# Issues
Since this program is mainly focus for linux OS, it will not work properly in Windows OS

**Authors**
1. Akar Kaung
2. Moyan Zhou
3. Hao Wu
4. Yiming Yao
