//Name of the file: main.java
//What the code does: It is main class;
//                    it will be handling inputs from the users and call to needed functions
//Authors: Hao Wu, Moyan Zhou
package vote;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handled inputs from the users, display results and call to needed functions
 */
public class main {

  // for testing purpose only

  /**
   * Number of times PO is called....
   */
  public static int poCount = 0;

  /**
   * Number of times IR is called.
   */
  public static int irCount = 0;

  /**
   * Number of times OPL is called.
   */
  public static int oplCount = 0;

  /**
   * This method is where the program start and get data from user.
   * Then decide which election type should it sort the votes as.
   *
   * @param args N/A
   * @throws IOException if the exceptions produced by failed or interrupted I/O operation
   */
  public static void main(String[] args) throws IOException {
    System.out.println("**********Welcome To Voting System***********");
    ArrayList<String> filesName = new ArrayList<>();
    int filesCount = 0;
    boolean moreFile = true;
    String str;
    Scanner sc = new Scanner(System.in);
    while (true) {
      System.out.println("---> Please enter the name of election file with '.csv' followed, NO space."); //6_cands.csv  IR_tie_for_winner.csv
      if (filesCount != 0) {
        System.out.println("---> If you decide not to add more files, enter CONTINUE (case sensitive)");
      }
      str = sc.nextLine();
      Path path = Paths.get(System.getProperty("user.dir"));
      Path parentPath = path.getParent();
      String fnTemp = str;

      String[] arrParent = parentPath.toString().split("/");
      if (!arrParent[arrParent.length - 1].equals("src")) {
        // for testing purpose
        str = path + "/src/ballots/" + str;
      } else {
        str = parentPath + "/ballots/" + str;
      }
      if (fnTemp.equals("CONTINUE") && filesCount != 0) { // if user want to continue without adding more files
        break;
      } else {
        filesName.add(str);
        filesCount++;
        if (utilities.checkFileExist(str) && utilities.checkFileType(str)) {  // check if file exist and if the file type is csv
          while (true) {
            System.out.println("Would you like to add more files? (Y/N)");
            str = sc.nextLine();
            if (str.equals("Y")) {
              moreFile = true;
              break;
            } else if (str.equals("N")) {
              moreFile = false;
              break;
            } else {
              System.out.println("[!] Invalid input.");
              System.out.println("[!] Please try again...");
            }
          }
        } else {
          System.out.println("---> File is not detected, please check typing. (Did you forget '.csv' ?)");
          System.out.println("---> Please try it again. ");
          filesCount--;
          filesName.remove(filesCount); // remove the file name from database
        }
        if (!moreFile) {    // user don't want to add more files
          break;
        }
      }
    }

    for (int i = 0; i < filesCount; i++) {
      String[] name = filesName.get(i).split("/");
      ArrayList<String> election = new ArrayList<String>();
      try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filesName.get(i)), StandardCharsets.UTF_8));//GBK
        String line = null;
        while ((line = reader.readLine()) != null) {

          String[] item = line.split("\n");
          String last = item[item.length - 1];
          election.add(last);
        }
      } catch (Exception e) {
        System.out.println("[!] Error: File not found.");
      }
      //********** get the type of election ********
      String election_type = election.get(0);
      System.out.println("[+] " + name[name.length - 1] + " is being process.");
      if (election_type.equals("IR")) {
        //********** election type is IR **********
        execute_IR(election);
      } else if (election_type.equals("OPL")) {
        //********** election type is OPL **********
        execute_OPL(election);
      } else if (election_type.equals("PO")) {
        //********** election type is PO **********
        execute_PO(election);
      } else {
        System.out.println("[!] " + name[name.length - 1] + " election type is not supported.");
        System.out.println("[!] " + name[name.length - 1] + " will be skip.");
      }
      System.out.println();
    }
  }//main ends

  /**
   * This method handled the process for Instant Runoff election type.
   * It will store candidates and votes data into the database and then pass the data to IR class so that the system can sort the winner.
   * Then it will display the result to the user.
   *
   * @param election data from ballot file
   * @throws IOException if the object class (IR) does not exist
   */
  public static void execute_IR(ArrayList<String> election) throws IOException {
    String election_type = election.get(0);

    // store the number of candidate
    int num_candidate = 0;
    try {
      num_candidate = Integer.parseInt(election.get(1));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the name of the candidates
    String s = election.get(2);
    String[] candidates = s.split(",");

    // store the number of votes
    int num_votes = 0;
    try {
      num_votes = Integer.parseInt(election.get(3));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the initial votes
    String[] vote = new String[num_votes];
    for (int i = 4; i < election.size(); i++) {
      vote[i - 4] = election.get(i);
    }

    // create ballot object and start sorting
    IR_Obj ir = new IR_Obj(election_type, num_candidate, candidates, num_votes, vote);

    //end of program
    System.out.println("---> winner is " + IR.ir_election(ir.getCandidate(), ir.getBallot(), ir.getNum_ballot()));
    System.out.println("---> For more details, please check result and audit file under /src.");
    System.out.println("---> Have a good day!");
    irCount++;
  }//******* IR ends *********

  /**
   * This method handled the process for Open Party Listing election type.
   * It will store candidates and votes data into the database and then pass the data to OPL class so that the system can sort the winner.
   * Then it will display the result to the user.
   *
   * @param election data from ballot file
   * @throws IOException if the object class (OPL) does not exist
   */
  public static void execute_OPL(ArrayList<String> election) throws IOException {
    //OPL
    String election_type = election.get(0);

    // store the number of candidate
    int number_candidate = 0;
    try {
      number_candidate = Integer.parseInt(election.get(1));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the candidate data
    String all = election.get(2);
    String temp = all.replace("[", "").replace("]", "").replace(" ", "");
    String[] individual = temp.split(",");

    // store number of available seats
    int available_seats = 0;
    try {
      available_seats = Integer.parseInt(election.get(3));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the number of ballots
    int number_ballots = 0;
    try {
      number_ballots = Integer.parseInt(election.get(4));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the ballot data
    String[] vote = new String[number_ballots];
    for (int i = 5; i < election.size(); i++) {
      vote[i - 5] = election.get(i);
    }

    // create ballot object and start sorting
    OPL_Obj opl = new OPL_Obj(election_type, number_candidate, individual, available_seats, number_ballots, vote);

    //System.out.println("---> winner is " + IR.ir_election(ir.getCandidate(),ir.getBallot(), ir.getNum_ballot()));
    OPL.OPL_election(opl.getCandidates(), opl.getBallots(), opl.getNumber_seats(), opl.getNumber_ballots());
    System.out.println("---> For more details, please check result and audit file under /src.");
    System.out.println("---> Have a good day!");
    oplCount++;
  }

  /**
   * This is function is to store the PO ballot data, and generate to String
   *
   * @param election the arraylist of election from ballot file
   * @return String of ballot information
   */
  public static String execute_PO(ArrayList<String> election) {
    String election_type = election.get(0);

    //store the number of candidates
    int num_candidate = 0;
    try {
      num_candidate = Integer.parseInt(election.get(1));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    //store the name of the candidates
    String before = election.get(2);
    String temp = before.replace("[", "").replace("]", "").replace(" ", "");
    String[] candidates_and_party = temp.split(",");

    int num_votes = 0;
    try {
      num_votes = Integer.parseInt(election.get(3));
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    // store the initial votes
    String[] vote = new String[num_votes];
    for (int i = 4; i < election.size(); i++) {
      vote[i - 4] = election.get(i);
    }

    String res = election_type + num_candidate;
    for (int i = 0; i < candidates_and_party.length; i++) {
      res = res + candidates_and_party[i];
    }
    res = res + num_votes;
    for (int i = 0; i < vote.length; i++) {
      res = res + vote[i];
    }

    return res;

  }


}
