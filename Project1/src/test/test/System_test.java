//Name of the file: System_test.java
//What the code does: Test the system
//Authors: Yao Ming
package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vote.main;

import java.io.*;
import java.util.ArrayList;

import static java.lang.System.exit;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing for the user of the user
 */
public class System_test {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/More_data_IR.csv"), "utf-8"));//GBK
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/10000data_IR.csv"), "utf-8"));//GBK
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/1000data_IR.csv"), "utf-8"));//GBK
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/6_OPL.csv"), "utf-8"));//GBK
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/11_OPL.csv"), "utf-8"));//GBK
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
}
