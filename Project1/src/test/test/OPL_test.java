//Name of the file: OPL_test.java
//What the code does: Test the OPL class
//Authors: Yao Ming, Hao Wu
package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vote.generate_file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static vote.OPL.*;

/**
 * Testing the OPL election type functions
 */
public class OPL_test {
    /**
     * Test OPL election
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_OPL_election() throws IOException {
        String[] individual = {"Pike", "D", "Foster", "D", "Deutsch", "R", "Borg", "R", "Jones", "R", "Smith", "I"};
        String[] vote = {"1,,,,,", "1,,,,,", ",1,,,,", ",,,,1,", ",,,,,1", ",,,1,,", ",,,1,,", "1,,,,,", ",1,,,,"};
        int available_seats = 3;
        int num_vote = 9;
        assertEquals("OPL Done!", OPL_election(individual, vote, available_seats, num_vote));
    }

    /**
     * Assign candidates into their belonging party
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_party_candidates() throws IOException {
        String[] individual = {"Pike", "D", "Foster", "D", "Deutsch", "R", "Borg", "R", "Jones", "R", "Smith", "I"};
        String[] vote = {"1,,,,,", "1,,,,,", ",1,,,,", ",,,,1,", ",,,,,1", ",,,1,,", ",,,1,,", "1,,,,,", ",1,,,,"};
        int available_seats = 3;
        int num_vote = 9;

        ArrayList<String> d = new ArrayList<>();
        d.add("Pike");
        d.add("Foster");
        ArrayList<String> r = new ArrayList<>();
        r.add("Deutsch");
        r.add("Borg");
        r.add("Jones");
        ArrayList<String> i = new ArrayList<>();
        i.add("Smith");
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map.put("D", d);
        map.put("R", r);
        map.put("I", i);
        File file = generate_file.create("test_party_candidates_File");
        assertEquals(map, party_candidates(individual, file));
    }

    /**
     * Testing to get candidates name
     */
    @Test
    public void test_candidates_list() {
        String[] individual = {"Pike", "D", "Foster", "D", "Deutsch", "R", "Borg", "R", "Jones", "R", "Smith", "I"};
        String[] res = {"Pike", "Foster", "Deutsch", "Borg", "Jones", "Smith"};
        String[] out = candidates_list(individual);
        for (int i = 0; i < res.length; i++) {
            assertEquals(res[i], out[i]);
        }
    }

    /**
     * Test to check the assigned votes for the candidates
     */
    @Test
    public void test_candidates_numOfVote() {
        String[] individual = {"Pike", "D", "Foster", "D", "Deutsch", "R", "Borg", "R", "Jones", "R", "Smith", "I"};
        String[] candidates = {"Pike", "Foster", "Deutsch", "Borg", "Jones", "Smith"};
        String[] vote = {"1,,,,,", "1,,,,,", ",1,,,,", ",,,,1,", ",,,,,1", ",,,1,,", ",,,1,,", "1,,,,,", ",1,,,,"};
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Pike", 3);
        map.put("Foster", 2);
        map.put("Deutsch", 0);
        map.put("Borg", 2);
        map.put("Jones", 1);
        map.put("Smith", 1);
        assertEquals(map, candidates_numOfVote(individual, vote, candidates));

    }

    /**
     * Checking the votes from the parties instead of individual
     */
    @Test
    public void test_party_vote() {
        ArrayList<String> d = new ArrayList<>();
        d.add("Pike");
        d.add("Foster");
        ArrayList<String> r = new ArrayList<>();
        r.add("Deutsch");
        r.add("Borg");
        r.add("Jones");
        ArrayList<String> i = new ArrayList<>();
        i.add("Smith");
        HashMap<String, ArrayList<String>> party_candidates = new HashMap<>();
        party_candidates.put("D", d);
        party_candidates.put("R", r);
        party_candidates.put("I", i);

        HashMap<String, Integer> candidates_numOfVote = new HashMap<>();
        candidates_numOfVote.put("Pike", 3);
        candidates_numOfVote.put("Foster", 2);
        candidates_numOfVote.put("Deutsch", 0);
        candidates_numOfVote.put("Borg", 2);
        candidates_numOfVote.put("Jones", 1);
        candidates_numOfVote.put("Smith", 1);

        HashMap<String, Integer> res = new HashMap<>();
        res.put("D", 5);
        res.put("R", 3);
        res.put("I", 1);
        assertEquals(res, party_vote(party_candidates, candidates_numOfVote));
    }

    /**
     * First allocation method testing
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_first_allocation() throws IOException {
        HashMap<String, Integer> party_vote = new HashMap<>();
        party_vote.put("D", 5);
        party_vote.put("R", 3);
        party_vote.put("I", 1);

        int quota = 3;

        File file = generate_file.create("test_first_allocation_file");

        HashMap<String, Integer> res = new HashMap<>();
        res.put("D", 1);
        res.put("R", 1);
        res.put("I", 0);
        assertEquals(res, first_allocation(party_vote, quota, file));
    }

    /**
     * Checking the reminder
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_reminder() throws IOException {
        HashMap<String, Integer> party_vote = new HashMap<>();
        party_vote.put("D", 5);
        party_vote.put("R", 3);
        party_vote.put("I", 1);

        int quota = 3;
        File file = generate_file.create("test_first_allocation_file");
        HashMap<String, Integer> res = new HashMap<>();
        res.put("D", 2);
        res.put("R", 0);
        res.put("I", 1);
        assertEquals(res, reminder(party_vote, quota, file));

    }

    /**
     * Second allocation method testing
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_second_allocation() throws IOException {
        HashMap<String, Integer> reminder = new HashMap<>();
        reminder.put("D", 2);
        reminder.put("R", 0);
        reminder.put("I", 1);

        int remain_seat = 1;
        File file = generate_file.create("test_first_allocation_file");
        HashMap<String, Integer> res = new HashMap<>();
        res.put("D", 2);

        second_allocation(reminder, remain_seat, file);

        for (String key : res.keySet()) {
            assertEquals(true, reminder.containsKey(key));
            assertEquals(reminder.get(key), res.get(key));
        }
    }

    /**
     * Final allocation method testing
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_final_allocation() throws IOException {
        HashMap<String, Integer> first_allocation = new HashMap<>();
        first_allocation.put("D", 1);
        first_allocation.put("R", 1);
        first_allocation.put("I", 0);

        HashMap<String, Integer> second_allocation = new HashMap<>();
        second_allocation.put("D", 2);
        File file = generate_file.create("test_first_allocation_file");

        HashMap<String, Integer> res = new HashMap<>();
        res.put("D", 2);
        res.put("R", 1);
        res.put("I", 0);

        final_allocation(first_allocation, second_allocation, file);
        for (String key : res.keySet()) {
            assertEquals(true, first_allocation.containsKey(key));
            assertEquals(first_allocation.get(key), res.get(key));
        }
    }

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
     * Check the winner candidates of the OPL election
     *
     * @throws IOException IOException handling for generate_file
     */
    @Test
    public void test_candidates_winner() throws IOException {
        String key = "D";

        ArrayList<String> d = new ArrayList<>();
        d.add("Pike");
        d.add("Foster");

        HashMap<String, Integer> candidates_numOfVote = new HashMap<>();
        candidates_numOfVote.put("Pike", 3);
        candidates_numOfVote.put("Foster", 2);
        candidates_numOfVote.put("Deutsch", 0);
        candidates_numOfVote.put("Borg", 2);
        candidates_numOfVote.put("Jones", 1);
        candidates_numOfVote.put("Smith", 1);

        int party_seats = 2;

        File file1 = generate_file.create("test_candidates_winner_file1");
        File file2 = generate_file.create("test_candidates_winner_file2");

        candidates_winner(key, d, candidates_numOfVote, party_seats, file1, file2);

        assertEquals("For Party D, Foster Pike won the election", outputStreamCaptor.toString()
                .trim());
    }


    /**
     * Flip coin method testing
     */
    @Test
    public void test_flip_coin_winner() {
        String a = "Tom";
        String b = "Jerry";
        String c = flip_coin_winner(a, b);

        assertEquals(true, c.equals(a) || c.equals(b));
    }


}
