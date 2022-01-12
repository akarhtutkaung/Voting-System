//Name of the file: System_test.java
//What the code does: Test the system
//Authors: Yao Ming
package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vote.main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing for the user of the user
 */
public class System_test {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  private void provideInput(String data) {
    System.setIn(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)));
  }

  /**
   * Capture the data that system display to the user
   */
  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  /**
   * Testing the program with real data and expected real result for IR election type
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void test_program1() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/More_data_IR.csv"), "utf-8"));//GBK
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
    assertEquals("---> winner is H\n" +
        "---> For more details, please check result and audit file under /src.\n" +
        "---> Have a good day!", outputStreamCaptor.toString()
        .trim());
  }

  /**
   * Testing the program with real data (10000 votes) and expected real result for IR election type
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void test_program_2() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/10000data_IR.csv"), "utf-8"));//GBK
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
    assertEquals("---> winner is F\n" +
        "---> For more details, please check result and audit file under /src.\n" +
        "---> Have a good day!", outputStreamCaptor.toString()
        .trim());
  }

  /**
   * Testing the program with real data (1000 votes) and expected real result for IR election type
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void test_program_3() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/1000data_IR.csv"), "utf-8"));//GBK
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
    assertEquals("---> winner is D\n" +
        "---> For more details, please check result and audit file under /src.\n" +
        "---> Have a good day!", outputStreamCaptor.toString()
        .trim());
  }

  /**
   * Testing the program with real data (6 votes) and expected real result for OPL election type
   *
   * @throws IOException IOException handling for generate_file
   */

  @Test
  public void test_program4() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/6_OPL.csv"), "utf-8"));//GBK
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
    main.execute_OPL(election);
    assertEquals("For Party R, Borg won the election\n" +
        "For Party D, Foster Pike won the election\n" +
        "---> For more details, please check result and audit file under /src.\n" +
        "---> Have a good day!", outputStreamCaptor.toString()
        .trim());
  }

  /**
   * Testing the program with real data (11 votes) and expected real result for OPL election type
   *
   * @throws IOException IOException handling for generate_file
   */

  @Test
  public void test_program5() throws IOException {
    ArrayList<String> election = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/ballots/11_OPL.csv"), "utf-8"));//GBK
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
    main.execute_OPL(election);
    assertEquals("For Party R, Deutsch won the election\n" +
        "For Party D, Foster Pike won the election\n" +
        "---> For more details, please check result and audit file under /src.\n" +
        "---> Have a good day!", outputStreamCaptor.toString()
        .trim());
  }

  /**
   * Testing the whole program with multiple files input different type of election and check whether it is executed or not.
   * 6_OPL.csv, 15_OPL.csv, 1000data_IR.csv
   *
   * @throws IOException IOException handling for generate_file
   */
  @Test
  public void test_program6() throws Exception {
    String input = "6_OPL.csv\n" +
        "Y\n" +
        "15_OPL.csv\n" +
        "Y\n" +
        "1000data_IR.csv\n" +
        "N";
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
