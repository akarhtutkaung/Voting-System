//Name of the file: IR.java
//What the code does: It is IR class;
//                    it will be handling all the operation IR needs
//Authors: Hao Wu
package vote;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * IR election type which use specific sorting algorithm to generate the winner result
 */
public class IR {

  /**
   * Execute IR election process
   *
   * @param cand     candidate names
   * @param vote     votes data
   * @param num_vote number of votes
   * @return winner candidate name
   * @throws IOException IOException handling for generate_file class
   */
  public static String ir_election(String[] cand, String[] vote, int num_vote) throws IOException {
    File result = generate_file.create("Result_File_");
    File audit = generate_file.create("Audit_File_");
    File invalidated = generate_file.create("invalidated_");
    generate_file.writeFile(invalidated, "Invalid ballot are below");

    vote = delete_invalid_ballots(cand, vote, invalidated);

    HashMap<String, ArrayList<String[]>> map = new HashMap<String, ArrayList<String[]>>();
    map = store_ballot_data(map, cand, vote); // store ballot data and assign the java.vote to candidate

    if (winner(map, vote.length)) { // check if there's winner candidate
      return name_of_winner(map);
    } else {
      while (map.size() > 2) { // sort the votes until there's 2 candidates remaining
        String loser = loser_name(map, audit);
        transfer_ballots(map, cand, loser);
        if (winner(map, vote.length)) {
          String s = name_of_winner(map);
          generate_file.writeFile(result, "The Winner Of election is " + s);
          generate_file.writeFile(audit, "The Winner Of election is " + s);
          return s;
        }
      }

      if (winner(map, vote.length)) {   // one of two remain candidates has over 50% ballots
        String s = name_of_winner(map);
        generate_file.writeFile(result, "The Winner Of election is " + s);
        generate_file.writeFile(audit, "The Winner Of election is " + s);
        return s;
      } else {                        // tie, flip coin
        String s = flip_coin_winner(map);
        generate_file.writeFile(result, "The Winner Of election is " + s);
        generate_file.writeFile(audit, "The Winner Of election is " + s);
        return s;
      }
    }
  }

  /**
   * Delete all invalid ballots from vote[]
   *
   * @param cand candidate names
   * @param vote votes data
   * @param file the file contains invalid ballots
   * @return all valid ballots
   * @throws IOException IOException handling for generate_file class
   */
  public static String[] delete_invalid_ballots(String[] cand, String[] vote, File file) throws IOException {
    int valid_length = 0;
    if (cand.length % 2 == 0) {
      valid_length = cand.length / 2 + cand.length - 1;
    } else {
      valid_length = cand.length / 2 + cand.length;
    }
    ArrayList<String> tmp = new ArrayList<>();
    for (int i = 0; i < vote.length; i++) {
      if (vote[i].length() >= valid_length) {
        tmp.add(vote[i]);
      } else {
        generate_file.writeFile(file, vote[i]);
      }
    }
    generate_file.writeFile(file, "Totally " + String.valueOf(vote.length - tmp.size()) + " invalid ballots");
    String[] valid_ballot = new String[tmp.size()];
    for (int i = 0; i < tmp.size(); i++) {
      valid_ballot[i] = tmp.get(i);
    }
    return valid_ballot;
  }

    /*
     ,,,0 -> 0,0,0,10  split() cannot recognize if nothing between two comma
     map after initial
     Rose  --------  [ ["10","30","40","20"],  ["10","0","20","0"], ["10","20","30","0"] ]
     K     --------  [ [] ]
     C     --------  [ ["30","20","10","40"], ["0","0","10","20"], ["20","0","10","0"] ]
     R     --------  [ ["0","0","0","10"] ]
     */

  /**
   * Assign the votes and ballot to the candidates
   *
   * @param map  Database of candidate
   * @param cand Candidate names
   * @param vote Ballots data
   * @return Database of candidate with updated data
   */
  public static HashMap<String, ArrayList<String[]>> store_ballot_data(HashMap<String, ArrayList<String[]>> map, String[] cand, String[] vote) {
    for (String s : cand) { // initialize the candidate in the map
      map.put(s, new ArrayList<String[]>());
    }
    for (int i = 0; i < vote.length; i++) {
      int idx = -1;
      String[] tmp = vote[i].split(",");

      for (int j = 0; j < tmp.length; j++) {
        tmp[j] = tmp[j] + "0";     // ,,,0 -> 0,0,0,10  split() cannot recognize if nothing between two comma
      }
      if (tmp.length < map.size()) {
        String[] tmp1 = new String[map.size()];
        for (int p = 0; p < tmp.length; p++) {
          tmp1[p] = tmp[p];
        }
        for (int x = tmp.length; x < map.size(); x++) {
          tmp1[x] = "0";
        }

        tmp = tmp1;
      }
      for (int j = 0; j < tmp.length; j++) {
        if (Integer.parseInt(tmp[j]) == 10) {
          idx = j;    // set who is the candidate is this java.vote for
        }
      }
      map.get(cand[idx]).add(tmp); // assign the java.vote to the specific candidate
    }
    return map;
  }


  /**
   * Check if any candidate earned over 50% ballots
   *
   * @param map      Database of candidate
   * @param num_vote Total number of votes
   * @return if there's winner candidate or not
   */
  public static boolean winner(HashMap<String, ArrayList<String[]>> map, int num_vote) {
    for (String key : map.keySet()) {
      if (map.get(key).size() > num_vote / 2) {
        return true;
      }
    }
    return false;
  }


  //get the name of the loser candidate

  /**
   * Get loser name
   *
   * @param map  Database of candidate
   * @param file Audit file
   * @return Name of the loser
   * @throws IOException IOException handling for generate_file class
   */
  public static String loser_name(HashMap<String, ArrayList<String[]>> map, File file) throws IOException {
    String s = "";
    int minimum_ballot = Integer.MAX_VALUE;
    for (String key : map.keySet()) {
      if (map.get(key).size() < minimum_ballot) {
        s = key;
        minimum_ballot = map.get(key).size();
      }
    }
    // handle if two candidates have same lowest ballots
    for (String key : map.keySet()) {
      if (map.get(key).size() == minimum_ballot && !key.equals(s)) {
        String[] cand_list = new String[2];
        cand_list[0] = key;
        cand_list[1] = s;
        Random rand = new Random();
        int rand_int = rand.nextInt(2);
        return cand_list[rand_int];
      }
    }
    generate_file.writeFile(file, s + " is eliminated");
    generate_file.writeFile(file, "--->" + String.valueOf(minimum_ballot) + " ballots will be transferred");
    // if no tie happened for loser, return loser's name
    return s;

  }

  //transfer ballots from loser

  /**
   * Transfer the votes of the loser to new candidate
   *
   * @param map   Database of candidates
   * @param cand  Candidate names
   * @param loser Loser name
   * @return Database of candidates
   */
  public static HashMap<String, ArrayList<String[]>> transfer_ballots(HashMap<String, ArrayList<String[]>> map,
                                                                      String[] cand, String loser) {

    ArrayList<String[]> vote_be_transferred = map.get(loser);
    map.remove(loser);
    for (String[] x : vote_be_transferred) {
      boolean check_transferred = false;
      int preference = 2;
      for (int i = 0; i < x.length; i++) {
        if (Integer.parseInt(x[i]) == preference * 10 && map.containsKey(cand[i])) {
          map.get(cand[i]).add(x);
          check_transferred = true;
        }
      }
      // if 2nd candidate of this ballot has been eliminated, move to 3rd ...
      if (check_transferred == false) {
        while (preference <= cand.length) {
          for (int i = 0; i < x.length; i++) {
            if (Integer.parseInt(x[i]) == preference * 10 && map.containsKey(cand[i])) {
              map.get(cand[i]).add(x);
            }
          }
          preference += 1;
        }
      }
    }
    return map;
  }


  //get name(key in HashMap) for winner

  /**
   * Get the name of the winner
   *
   * @param map Database of candidates
   * @return Winner name
   */
  public static String name_of_winner(HashMap<String, ArrayList<String[]>> map) {
    String s = "";
    int max = 0;
    for (String key : map.keySet()) {
      if (map.get(key).size() > max) {
        s = key;
        max = map.get(key).size();
      }
    }
    return s;
  }

  //flip coins for winner

  /**
   * Flip the coin and decide the winner and loser
   *
   * @param map Database of candidates
   * @return Winner name
   */
  public static String flip_coin_winner(HashMap<String, ArrayList<String[]>> map) {
    String[] cand_list = new String[2];
    int i = 0;
    for (String key : map.keySet()) {
      cand_list[i] = key;
      i += 1;
    }
    Random rand = new Random();
    int rand_int = rand.nextInt(2);
    return cand_list[rand_int];
  }
}