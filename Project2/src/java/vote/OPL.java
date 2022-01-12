//Name of the file: OPL.java
//What the code does: It is OPL class;
//                    it will be handling all the operation OPL needs
//Authors: Hao Wu, Moyan Zhou

package vote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * OPL election type which use specific sorting algorithm to generate the winner result
 */
public class OPL {

  /**
   * Execute OPL election process
   *
   * @param individual      candidates name and party type
   * @param vote            votes data
   * @param available_seats total available seats
   * @param num_vote        total number of votes
   * @return nothing
   * @throws IOException IOException handling for generate_file class
   */
  public static String OPL_election(String[] individual, String[] vote, int available_seats, int num_vote) throws IOException {
    File result = generate_file.create("Result_File_");
    File audit = generate_file.create("Audit_File_");

    //create party - candidates map
    HashMap<String, ArrayList<String>> party_candidates = party_candidates(individual, audit);
    //list if candidates
    String[] candidates = candidates_list(individual);
    //create candidates - number of his votes
    HashMap<String, Integer> candidates_numOfVote = candidates_numOfVote(individual, vote, candidates);
    //map that party - and its total java.vote party
    HashMap<String, Integer> party_vote = party_vote(party_candidates, candidates_numOfVote);
    //calculate quota
    int quota = num_vote / available_seats;
    //first allocation |||   party - seats it gains in first allocation
    HashMap<String, Integer> first_allocation = first_allocation(party_vote, quota, audit);
    // party - reminder
    HashMap<String, Integer> reminder = reminder(party_vote, quota, audit);
    //calculate remain seats
    int seat_allocated = 0;
    for (String key : first_allocation.keySet()) {
      seat_allocated += first_allocation.get(key);
    }

    //map contains parties who gains seats in the second allocation
    second_allocation(reminder, available_seats - seat_allocated, audit);
    final_allocation(first_allocation, reminder, audit);  //first_allocation now contains party and their final seats allocation

    //print result for each party.
    for (String key : party_candidates.keySet()) {
      candidates_winner(key, party_candidates.get(key), candidates_numOfVote, first_allocation.get(key), audit, result);
    }
    return "OPL Done!";
  }

  /**
   * Create party database with all the candidates assigned to their party type
   *
   * @param individual candidates name and party
   * @param file       file to write data into
   * @return Database of the candidates
   * @throws IOException IOException handling for generate_file class
   */
  public static HashMap<String, ArrayList<String>> party_candidates(String[] individual, File file) throws IOException {

    HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    for (int i = 1; i < individual.length; i += 2) {
      if (map.containsKey(individual[i])) { // if party exist in the database
        map.get(individual[i]).add(individual[i - 1]); // add the candidate to that party
      } else { // add new party to the database along with the candidate
        ArrayList<String> candidates = new ArrayList<String>();
        candidates.add(individual[i - 1]);
        map.put(individual[i], candidates);
      }
    }

    // audit file purpose
    generate_file.writeFile(file, "For basic information:");
    for (String key : map.keySet()) {
      ArrayList<String> temp = map.get(key);
      generate_file.writeFile(file, "   Party " + key + " has " + temp.size() + " candidates: " + temp.toString());
    }
    return map;
  }

  /**
   * Get candidate name list
   *
   * @param individual candidate name and type
   * @return list of candidate name
   */
  public static String[] candidates_list(String[] individual) {
    String[] s = new String[individual.length / 2];
    for (int i = 0; i < individual.length; i += 2) {
      s[i / 2] = individual[i];
    }
    return s;
  }

  /**
   * Assign java.vote to candidate according to ballot
   *
   * @param individual      candidates name and party
   * @param vote            java.vote data
   * @param candidates_list list of candidates name
   * @return database of the candidate with assigned votes
   */
  public static HashMap<String, Integer> candidates_numOfVote(String[] individual, String[] vote, String[] candidates_list) {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    for (String s : candidates_list) { // initialize the candidate into the map
      map.put(s, 0);
    }
    for (String s : vote) { // assign the votes according to voter choice
      int idx = -1;
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '1') {
          idx = i;
        }
      }
      map.put(candidates_list[idx], map.get(candidates_list[idx]) + 1);
    }
    return map;
  }

  /**
   * Assign the candidate's votes as their party java.vote
   *
   * @param party_candidates     database of party with assigned candidate
   * @param candidates_numOfVote database of candidate with assigned votes
   * @return database of party with assigned votes it
   */
  public static HashMap<String, Integer> party_vote(HashMap<String, ArrayList<String>> party_candidates, HashMap<String, Integer> candidates_numOfVote) {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    for (String key : party_candidates.keySet()) {
      int total_vote = 0;
      ArrayList list = party_candidates.get(key);
      for (int i = 0; i < list.size(); i++) {
        total_vote += candidates_numOfVote.get(list.get(i));
      }
      map.put(key, total_vote);
    }
    return map;
  }

  /**
   * First allocation sorting
   *
   * @param party_vote database of party with votes assigned
   * @param quota      quota of the votes
   * @param file       file to write audit
   * @return database of the party during first allocation
   * @throws IOException IOException handling for generate_file class
   */
  public static HashMap<String, Integer> first_allocation(HashMap<String, Integer> party_vote, int quota, File file) throws IOException {
    HashMap<String, Integer> first_alloc = new HashMap<String, Integer>();
    for (String key : party_vote.keySet()) {
      first_alloc.put(key, party_vote.get(key) / quota);
    }

    generate_file.writeFile(file, "During first allocation:");
    for (String key : first_alloc.keySet()) {
      generate_file.writeFile(file, "   Party " + key + " has " + first_alloc.get(key) + " seats");
    }
    return first_alloc;
  }

  /**
   * After first allocation sorting
   *
   * @param party_vote database of party with votes assigned
   * @param quota      quota of the votes
   * @param file       file to write audit
   * @return database of the party after first allocation
   * @throws IOException IOException handling for generate_file class
   */
  public static HashMap<String, Integer> reminder(HashMap<String, Integer> party_vote, int quota, File file) throws IOException {
    HashMap<String, Integer> reminder = new HashMap<String, Integer>();
    for (String key : party_vote.keySet()) {
      reminder.put(key, party_vote.get(key) % quota);
    }
    generate_file.writeFile(file, "After first allocation:");
    for (String key : reminder.keySet()) {
      generate_file.writeFile(file, "   Party " + key + " has " + reminder.get(key) + " votes left");
    }
    return reminder;
  }

  /**
   * Sort the seats for party for second allocation
   *
   * @param reminder     database of the party after first allocation
   * @param remain_seats remain seats to be assign
   * @param file         file to write audit
   * @throws IOException IOException handling for generate_file class
   */
  public static void second_allocation(HashMap<String, Integer> reminder, int remain_seats, File file) throws IOException {
    generate_file.writeFile(file, "And there are " + remain_seats + " seats left");
    generate_file.writeFile(file, "Note: we eliminate the party/candidates in the list until there are " + remain_seats + " parties/candidates left and they get the seats");

    while (reminder.size() > remain_seats) {
      int min = Integer.MAX_VALUE;
      String party = "";
      for (String key : reminder.keySet()) {
        if (reminder.get(key) < min) {
          min = reminder.get(key);
          party = key;
        }
        if (reminder.get(key) == min) {
          generate_file.writeFile(file, "Coin is flipped");
          min = reminder.get(key);
          party = flip_coin_winner(key, party);
        }
      }
      generate_file.writeFile(file, "   Party/Candidate " + party + " is removed");
      reminder.remove(party);

    }
    ArrayList<String> party_left = new ArrayList<String>();
    for (String key : reminder.keySet()) {
      party_left.add(key);
    }
    generate_file.writeFile(file, "After removing, Party/Candidates " + party_left.toString() + " get the " + remain_seats + " seats");
  }

  /**
   * Sort the seats for party for final allocation
   *
   * @param first_allocation  database of first allocation
   * @param second_allocation database of second allocation
   * @param file              file to write audit
   * @throws IOException IOException handling for generate_file class
   */
  public static void final_allocation(HashMap<String, Integer> first_allocation, HashMap<String, Integer> second_allocation, File file) throws IOException {
    for (String key : second_allocation.keySet()) {
      if (first_allocation.containsKey(key)) {
        first_allocation.put(key, first_allocation.get(key) + 1);
      }
    }
    generate_file.writeFile(file, "As a final result:");
    for (String key : first_allocation.keySet()) {
      generate_file.writeFile(file, "   Party " + key + " gets " + first_allocation.get(key) + " seats in total");
    }
  }

  /**
   * Generate the candidate inside the party that won the election
   *
   * @param party                party name
   * @param candidates           candidates name
   * @param candidates_numOfVote candidates with number of votes
   * @param seats                total number of seats
   * @param file                 audit file
   * @param file2                result file
   * @throws IOException IOException handling for generate_file class
   */
  public static void candidates_winner(String party, ArrayList<String> candidates, HashMap<String, Integer> candidates_numOfVote, int seats, File file, File file2) throws IOException {
    HashMap<String, Integer> map = new HashMap<>();
    for (int i = 0; i < candidates.size(); i++) {
      map.put(candidates.get(i), candidates_numOfVote.get(candidates.get(i)));
    }

    second_allocation(map, seats, file);

    String finalS = "";
    for (String key : map.keySet()) {
      finalS += key + " ";
    }
    if (!finalS.equals("")) {
      generate_file.writeFile(file, "The result is: ");
      generate_file.writeFile(file2, "The result is: ");
      generate_file.writeFile(file, "   For Party " + party + ", Candidates " + finalS + " won the election");
      generate_file.writeFile(file2, "   For Party " + party + ", Candidates " + finalS + " won the election");

      //print to terminal
      System.out.print("For Party " + party + ",");
      for (String key : map.keySet()) {
        System.out.print(" " + key);
      }
      System.out.println(" won the election");
    }
  }

  /**
   * Flip the coin and decide the winner and loser
   *
   * @param a first candidate name
   * @param b second candidate name
   * @return winner name
   */
  public static String flip_coin_winner(String a, String b) {

    String[] cand_list = new String[2];
    cand_list[0] = a;
    cand_list[1] = b;

    Random rand = new Random();
    int rand_int = rand.nextInt(2);
    return cand_list[rand_int];

  }
}
