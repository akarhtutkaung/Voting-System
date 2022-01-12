//Name of the file: Main_test.java
//What the code does: Test the main class
//Authors: Yao Ming
package test;

import org.junit.jupiter.api.Test;
import vote.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the main class functions
 */
public class Main_test {
  /**
   * Testing for IR election type setting
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void test_for_IR_file_setting() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/IR_tie_for_winner.csv"), "utf-8"));//GBK
      String line = null;
      while ((line = reader.readLine()) != null) {
        String item[] = line.split("\n");
        String last = item[item.length - 1];
        election.add(last);
      }
    } catch (Exception e) {
      System.out.println("---> File is not detected, please check typing. (Did you forget '.csv' ?)");
      System.out.println("---> Please try it again. ");
      exit(0);
    }
    main.execute_IR(election);
    int num_candidate = Integer.parseInt(election.get(1));
    assertEquals(4, num_candidate);
    String s = election.get(2);
    String[] tmp = s.split(",");
    String[] candidates = s.split(",");
    String[] candidates_output = {"Rose", "Kleinberg", "Chou", "Royce"};
    assertEquals("Rose", candidates[0]);
    assertEquals("Kleinberg", candidates[1]);
    assertEquals("Chou", candidates[2]);
    assertEquals("Royce", candidates[3]);
    assertEquals(Arrays.toString(candidates_output), Arrays.toString(candidates));

    int num_votes = Integer.parseInt(election.get(3));
    assertEquals(7, num_votes);

    String[] vote = new String[num_votes];
    for (int i = 4; i < election.size(); i++) {
      vote[i - 4] = election.get(i);
    }
    String[] vote_result = {"1,3,4,2", "1,,2,", "1,2,3,", "3,2,1,4", ",,1,2", ",,,1", "2,,1,"};
    assertEquals(Arrays.toString(vote_result), Arrays.toString(vote));
  }

  /**
   * Testing for OPL election type settings
   */
  @Test
  public void test_for_OPL_file_setting() {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/6_OPL.csv"), "utf-8"));//GBK
      String line = null;
      while ((line = reader.readLine()) != null) {
        String item[] = line.split("\n");
        String last = item[item.length - 1];
        election.add(last);
        //System.out.println(last);
      }
    } catch (Exception e) {
      System.out.println("---> File is not detected, please check typing. (Did you forget '.csv' ?)");
      //e.printStackTrace();
      System.out.println("---> Please try it again. ");
    }
    //********** get the type of election ********
    String election_type = election.get(0);
    assertEquals("OPL", election_type);
    assertEquals("6", election.get(1));
    String all = election.get(2);
    all = all.substring(1, all.length() - 1); //substract " " sign
    String temp = all.replace("[", "").replace("]", "").replace(" ", "");
    assertEquals("Pike,D,Foster,D,Deutsch,R,Borg,R,Jones,R,Smith,I", temp);
    String[] individual = temp.split(",");
    String[] test = {"Pike", "D", "Foster", "D", "Deutsch", "R", "Borg", "R", "Jones", "R", "Smith", "I"};
    assertEquals(Arrays.toString(test), Arrays.toString(individual));
    assertEquals(3, Integer.parseInt(election.get(3)));
    assertEquals(9, Integer.parseInt(election.get(4)));
    String[] vote = new String[Integer.parseInt(election.get(4))];
    for (int i = 5; i < election.size(); i++) {
      vote[i - 5] = election.get(i);
    }
    String test1[] = {"1,,,,,", "1,,,,,", ",1,,,,", ",,,,1,", ",,,,,1", ",,,1,,", ",,,1,,", "1,,,,,", ",1,,,, "};
    assertEquals("1,,,,,", vote[0]);
    assertEquals(9, vote.length);
    assertEquals(Arrays.toString(test1), Arrays.toString(vote));
  }

  /**
   * Test for executing IR election
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void execute_IR() throws IOException {
    String election_type = "IR";
    String[] ballot = new String[4];
    ballot[0] = "1,3,4,2";
    ballot[1] = "1,,2,";
    ballot[2] = "1,2,3,";
    ballot[3] = "3,2,1,4";
    String[] candidate = new String[4];
    candidate[0] = "Rose";
    candidate[1] = "Kleinberg";
    candidate[2] = "Chou";
    candidate[3] = "Royce";
    int num_candidate = 4;
    int num_ballot = 4;
    IR_Obj t = new IR_Obj(election_type, num_candidate, candidate, num_ballot, ballot);
    assertEquals(4, t.getCandidate().length);
    assertEquals(4, t.getBallot().length);
    assertEquals(4, t.getNum_ballot());
    assertEquals(4, t.GetNum_candidate());
    assertEquals("IR", t.GetElection_type());
    assertEquals("Rose", IR.ir_election(t.getCandidate(), t.getBallot(), t.getNum_ballot()));
  }

  /**
   * Test for executing OPL election
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void execute_OPL() throws IOException {
    String[] ballot = new String[9];
    ballot[0] = "1,,,,,";
    ballot[1] = "1,,,,,";
    ballot[2] = ",1,,,,";
    ballot[3] = ",,,1,,";
    ballot[4] = "1,,,,,";
    ballot[5] = ",,,,,1";
    ballot[6] = ",,,,,1";
    ballot[7] = ",1,,,,";
    ballot[8] = ",,,,1,";
    String[] individual = new String[12];
    individual[0] = "Pike";
    individual[1] = "D";
    individual[2] = "Foster";
    individual[3] = "D";
    individual[4] = "Deutsch";
    individual[5] = "R";
    individual[6] = "Borg";
    individual[7] = "R";
    individual[8] = "Jones";
    individual[9] = "R";
    individual[10] = "Smith";
    individual[11] = "I";
    OPL_Obj opl_obj = new OPL_Obj("OPL", 6, individual, 3, 9, ballot);
    assertEquals("OPL", opl_obj.getElection_type());
    assertEquals(6, opl_obj.getNumber_candidates());
    assertEquals(12, opl_obj.getCandidates().length);
    assertEquals(3, opl_obj.getNumber_seats());
    assertEquals(9, opl_obj.getNumber_ballots());
    assertEquals(9, opl_obj.getBallots().length);
    String res = OPL.OPL_election(opl_obj.getCandidates(), opl_obj.getBallots(), opl_obj.getNumber_seats(), opl_obj.getNumber_ballots());
    assertEquals("OPL Done!", res);
  }

  /**
   * Test for initialize PO election
   */
  @Test
  public void execute_PO() {
    String[] ballot = new String[9];
    ballot[0] = "1,,,,,";
    ballot[1] = "1,,,,,";
    ballot[2] = ",1,,,,";
    ballot[3] = ",,,1,,";
    ballot[4] = "1,,,,,";
    ballot[5] = ",,,,,1";
    ballot[6] = ",,,,,1";
    ballot[7] = ",1,,,,";
    ballot[8] = ",,,,1,";
    String[] individual = new String[12];
    individual[0] = "Pike";
    individual[1] = "D";
    individual[2] = "Foster";
    individual[3] = "D";
    individual[4] = "Deutsch";
    individual[5] = "R";
    individual[6] = "Borg";
    individual[7] = "R";
    individual[8] = "Jones";
    individual[9] = "R";
    individual[10] = "Smith";
    individual[11] = "I";
    PO_Obj po_obj = new PO_Obj("PO", 6, individual, 9, ballot);
    assertEquals("PO", po_obj.getElection_type());
    assertEquals(6, po_obj.getNum_candidate());
    assertEquals(12, po_obj.getCandidate().length);
    assertEquals(9, po_obj.getNum_ballot());
    assertEquals(9, po_obj.getBallot().length);
    ArrayList<String> election = new ArrayList<String>();
    election.add("PO");
    election.add("6");
    election.add("[Pike,D], [Foster,D],[Deutsch,R], [Borg,R], [Jones,R],[Smith,I]");
    election.add("9");
    election.add("1,,,,,");
    election.add("1,,,,,");
    election.add(",1,,,,");
    election.add(",,,1,,");
    election.add("1,,,,,");
    election.add(",,,,,1");
    election.add(",,,,,1");
    election.add(",1,,,,");
    election.add(",,,,1,");
    String res = main.execute_PO(election);
    assertEquals("PO6PikeDFosterDDeutschRBorgRJonesRSmithI91,,,,,1,,,,,,1,,,,,,,1,,1,,,,,,,,,,1,,,,,1,1,,,,,,,,1,", res.toString());
  }

  private void provideInput(String data) {
    System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * Testing the program with multiple files input different type of election while the one of the file does not exist and check whether it is executed or not.
   * 6_OPL.csv, 15_OPL_Fake.csv, 1000data_IR.csv
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void testNonExistenceInputFile() throws Exception {
    String input = "6_OPL.csv\n" +
        "Y\n" +
        "15_OPL_Fake.csv\n" +
        "Y\n" +
        "1000data_IR.csv\n" +
        "N";
    provideInput(input);
    String[] args = null;
    main.oplCount = 0;
    main.irCount = 0;
    main.poCount = 0;
    main.main(args);
    assertEquals(1, main.oplCount);
    assertEquals(1, main.irCount);
    assertEquals(0, main.poCount);
  }

  /**
   * Testing the whole program with multiple files input different type of election and check whether it is executed or not.
   * Note: This test is for user who want to add more file at first but decided not to add later on.
   * 6_OPL.csv, 15_OPL.csv, 1000data_IR.csv
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void testDecisionChangedInputFiles() throws Exception {
    String input = "6_OPL.csv\n" +
        "Y\n" +
        "15_OPL.csv\n" +
        "Y\n" +
        "1000data_IR.csv\n" +
        "Y\n" +
        "CONTINUE\n";
    provideInput(input);
    String[] args = null;
    main.oplCount = 0;
    main.irCount = 0;
    main.poCount = 0;
    main.main(args);
    assertEquals(2, main.oplCount);
    assertEquals(1, main.irCount);
    assertEquals(0, main.poCount);
  }
}
