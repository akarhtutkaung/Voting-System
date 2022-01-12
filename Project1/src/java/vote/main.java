//Name of the file: main.java
//What the code does: It is main class;
//                    it will be handling inputs from the users and call to needed functions
//Authors: Hao Wu, Moyan Zhou
package vote;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handled inputs from the users, display results and call to needed functions
 */
public class main {

    /**
     * This method is where the program start and get data from user.
     * Then decide which election type should it sort the votes as.
     *
     * @param args N/A
     * @throws IOException if the exceptions produced by failed or interrupted I/O operation
     */
    public static void main(String args[]) throws IOException {
        System.out.println("**********Welcome To Voting System***********");
        ArrayList<String> election = new ArrayList<String>();
        while (true) {
            System.out.println("---> Please enter the name of election file with '.csv' followed, NO space."); //6_cands.csv  IR_tie_for_winner.csv
            Scanner sc = new Scanner(System.in);
            String str = null;
            str = sc.nextLine();

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "utf-8"));//GBK
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String item[] = line.split("\n");
                    String last = item[item.length - 1];
                    election.add(last);
                }
                break;
            } catch (Exception e) {
                try {
                    str = "src/java/" + str;
                    System.out.println(str);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "utf-8"));//GBK
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        String item[] = line.split("\n");
                        String last = item[item.length - 1];
                        election.add(last);
                    }
                    break;
                } catch (Exception a){
                    System.out.println("---> File is not detected, please check typing. (Did you forget '.csv' ?)");
                    System.out.println("---> Please try it again. ");
                }
            }
        }
        //********** get the type of election ********
        String election_type = election.get(0);

        if (election_type.equals("IR")) {
            //********** election type is IR **********
            execute_IR(election);
        } else if(election_type.equals("OPL")){
            //********** election type is OPL **********
            execute_OPL(election);
        } else {
            System.out.println("[!] Wrong election type.");
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

    }
}
