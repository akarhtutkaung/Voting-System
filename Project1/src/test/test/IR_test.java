//Name of the file: IR_test.java
//What the code does: Test the IR class
//Authors: Yao Ming
package test;

import org.junit.jupiter.api.Test;
import vote.IR;
import vote.IR_Obj;
import vote.generate_file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for IR class
 */
public class IR_test {

    /**
     * Checking if ballot data really got stored
     */
    @Test
    public void test_store_ballot_data() {
        String[] ballot = new String[7];
        ballot[0] = "1,3,4,2";
        ballot[1] = "1,,2,";
        ballot[2] = "1,2,3,";
        ballot[3] = "3,2,1,4";
        ballot[4] = ",,1,2";
        ballot[5] = ",,,1";
        ballot[6] = "2,,1,";
        String[] candidate = new String[4];
        candidate[0] = "Rose";
        candidate[1] = "Kleinberg";
        candidate[2] = "Chou";
        candidate[3] = "Royce";
        IR_Obj t = new IR_Obj("IR", 4, candidate, 7, ballot);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        HashMap<String, ArrayList<String[]>> map_output = new HashMap<String, ArrayList<String[]>>();
        ArrayList<String[]> res1 = new ArrayList<>();
        String[] a = {"10", "30", "40", "20"};
        String[] b = {"10", "0", "20", "0"};
        String[] c = {"10", "20", "30", "0"};
        res1.add(a);
        res1.add(b);
        res1.add(c);
        map_output = IR.store_ballot_data(map_output, t.getCandidate(), t.getBallot());
        for (int i = 0; i < res1.size(); i++) {
            String[] m = res1.get(i);
            String[] mm = map_output.get("Rose").get(i);
            for (int j = 0; j < m.length; j++) {
                assertEquals(m[j], mm[j]);
            }
        }
    }

    /**
     * Has winner in candidates
     */
    @Test
    public void test_winner_True() {
        ArrayList<String[]> r1 = new ArrayList<>();
        String[] s1 = new String[4];
        s1[0] = "10";
        s1[1] = "20";
        s1[2] = "0";
        s1[3] = "0";
        String[] s2 = new String[4];
        s2[0] = "10";
        s2[1] = "0";
        s2[2] = "0";
        s2[3] = "0";
        String[] s3 = new String[4];
        s3[0] = "10";
        s3[1] = "0";
        s3[2] = "0";
        s3[3] = "20";
        r1.add(s1);
        r1.add(s2);
        r1.add(s3);
        ArrayList<String[]> r2 = new ArrayList<>();
        ArrayList<String[]> r3 = new ArrayList<>();
        ArrayList<String[]> r4 = new ArrayList<>();
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Rose", r1);
        map.put("Kleinberg", r2);
        map.put("Chou", r3);
        map.put("Royce", r4);
        assertTrue(IR.winner(map, 4));
    }

    /**
     * Doesn't has winner in candidate yet
     */
    @Test
    public void test_winner_False() {
        ArrayList<String[]> r1 = new ArrayList<>();
        String[] s1 = new String[4];
        s1[0] = "10";
        s1[1] = "0";
        s1[2] = "0";
        s1[3] = "0";
        String[] s2 = new String[4];
        s2[0] = "0";
        s2[1] = "10";
        s2[2] = "0";
        s2[3] = "0";
        r1.add(s1);
        ArrayList<String[]> r2 = new ArrayList<>();
        ArrayList<String[]> r3 = new ArrayList<>();
        ArrayList<String[]> r4 = new ArrayList<>();
        r2.add(s2);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Rose", r1);
        map.put("Kleinberg", r2);
        map.put("Chou", r3);
        map.put("Royce", r4);
        assertFalse(IR.winner(map, 4));
    }

    /**
     * Checking the winner name
     */
    @Test
    public void test_name_of_winner() {
        ArrayList<String[]> r1 = new ArrayList<>();
        String[] s1 = new String[4];
        s1[0] = "10";
        s1[1] = "20";
        s1[2] = "0";
        s1[3] = "0";
        String[] s2 = new String[4];
        s2[0] = "0";
        s2[1] = "10";
        s2[2] = "0";
        s2[3] = "0";
        String[] s3 = new String[4];
        s3[0] = "10";
        s3[1] = "0";
        s3[2] = "30";
        s3[3] = "20";
        r1.add(s1);
        r1.add(s2);
        r1.add(s3);
        ArrayList<String[]> r2 = new ArrayList<>();
        ArrayList<String[]> r3 = new ArrayList<>();
        ArrayList<String[]> r4 = new ArrayList<>();
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Rose", r1);
        map.put("Kleinberg", r2);
        map.put("Chou", r3);
        map.put("Royce", r4);
        assertEquals(4, map.size());
        assertEquals("Rose", IR.name_of_winner(map));
    }

    /**
     * Testing transfer votes to another candidate
     */
    @Test
    public void test_transfer() {
        ArrayList<String[]> r1 = new ArrayList<>();
        String[] s1 = new String[4];
        s1[0] = "10";
        s1[1] = "20";
        s1[2] = "0";
        s1[3] = "0";
        String[] s2 = new String[4];
        s2[0] = "0";
        s2[1] = "10";
        s2[2] = "0";
        s2[3] = "0";
        String[] s3 = new String[4];
        s3[0] = "0";
        s3[1] = "20";
        s3[2] = "0";
        s3[3] = "10";
        String[] s4 = new String[4];
        s4[0] = "10";
        s4[1] = "20";
        s4[2] = "0";
        s4[3] = "0";
        ArrayList<String[]> r2 = new ArrayList<>();
        ArrayList<String[]> r3 = new ArrayList<>();
        ArrayList<String[]> r4 = new ArrayList<>();
        r1.add(s1);
        r2.add(s2);
        r3.add(s3);
        r4.add(s4);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Rose", r1);
        map.put("Kleinberg", r2);
        map.put("Chou", r3);
        map.put("James", r4);
        String[] cand = new String[4];
        cand[0] = "Rose";
        cand[1] = "Kleinberg";
        cand[2] = "Chou";
        cand[2] = "James";
        map = IR.transfer_ballots(map, cand, "Chou");
        String[] a = {"0", "10", "0", "0"};
        String[] b = {"0", "20", "0", "10"};
        ArrayList<String[]> d = new ArrayList<>();
        d.add(a);
        d.add(b);
        assertEquals(3, map.size());
        for (int i = 0; i < d.size(); i++) {
            String[] m = d.get(i);
            String[] mm = map.get("Kleinberg").get(i);
            for (int j = 0; j < m.length; j++) {
                assertEquals(m[j], mm[j]);
            }
        }
    }

    /**
     * Test to get loser name among the candidates
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_loser_name_1() throws IOException {
        File f = generate_file.create("test_loser_name_file");
        String[] s1 = {"10", "20"};
        String[] s2 = {"10", "0"};
        ArrayList<String[]> r1 = new ArrayList<>();
        ArrayList<String[]> r2 = new ArrayList<>();
        r1.add(s1);
        r1.add(s2);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Dog", r1);
        map.put("Cat", r2);
        String res = IR.loser_name(map, f);
        assertEquals("Cat", res);
    }

    /**
     * Testing for losers case, 2 candidate with same amount of votes
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_loser_name_2() throws IOException {
        File f = generate_file.create("test_loser_name_file");
        String[] s1 = {"10", "20"};
        String[] s2 = {"10", "0"};
        ArrayList<String[]> r1 = new ArrayList<>();
        ArrayList<String[]> r2 = new ArrayList<>();
        ArrayList<String[]> r3 = new ArrayList<>();
        r1.add(s1);
        r1.add(s2);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Dog", r1);
        map.put("Cat", r2);
        map.put("Tiger", r3);
        String res = IR.loser_name(map, f);
        assertEquals(true, res.equals("Cat") || res.equals("Tiger"));
    }

    /**
     * Flip coin testing
     */
    @Test
    public void test_flip_coin() {
        ArrayList<String[]> r1 = new ArrayList<>();
        String[] s1 = new String[4];
        s1[0] = "10";
        s1[1] = "0";
        s1[2] = "0";
        s1[3] = "0";
        String[] s2 = new String[4];
        s2[0] = "0";
        s2[1] = "10";
        s2[2] = "0";
        s2[3] = "0";
        ArrayList<String[]> r2 = new ArrayList<>();
        r1.add(s1);
        r2.add(s2);
        HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
        map.put("Rose", r1);
        map.put("Kleinberg", r2);
        String s = IR.flip_coin_winner(map);
        assertEquals(true, s.equals("Rose") || s.equals("Kleinberg"));
    }
}
