// Name of the file: PO_Obj.java
// What the code does: It is PO_Obj class. It will be a helper class for PO.
// Authors: Moyan Zhou

package vote;

import java.io.IOException;

/**
 * Database for PO
 */
public class PO_Obj {
  private String election_type;
  private int number_of_candidates;
  private String[] candidates_and_party;
  private int number_of_ballots;
  private String[] ballots;

  /**
   * Constructor for PO_Obj
   *
   * @param election_type        type of election
   * @param number_of_candidates number of candidate
   * @param candidates_and_party candidate names
   * @param number_of_ballots    number of ballots
   * @param ballots              ballot data
   */
  public PO_Obj(String election_type, int number_of_candidates, String[] candidates_and_party, int number_of_ballots, String[] ballots) {
    this.election_type = election_type;
    this.number_of_candidates = number_of_candidates;
    this.candidates_and_party = candidates_and_party;
    this.number_of_ballots = number_of_ballots;
    this.ballots = ballots;
  }

  /**
   * Set election type
   *
   * @param election_type Election type
   */
  public void setElection_type(String election_type) {
    this.election_type = election_type;
  }

  /**
   * Set number of candidate
   *
   * @param num_candidate Number of candidate
   */
  public void SetNum_candidate(int num_candidate) {
    this.number_of_candidates = num_candidate;
  }

  /**
   * Set candidates names
   *
   * @param candidate candidate names
   */
  public void setCandidate(String[] candidate) {
    this.candidates_and_party = candidate;
  }

  /**
   * Set number of ballots
   *
   * @param num_ballot number of ballots
   */
  public void setNum_ballot(int num_ballot) {
    this.number_of_ballots = num_ballot;
  }

  /**
   * Set ballot data
   *
   * @param ballot ballot data
   */
  public void setBallot(String[] ballot) {
    this.ballots = ballot;
  }

  /**
   * Get election type
   *
   * @return election type
   */
  public String getElection_type() {
    return election_type;
  }

  /**
   * Get number of candidates
   *
   * @return number of candidate
   */
  public int getNum_candidate() {
    return number_of_candidates;
  }

  /**
   * Get candidates name
   *
   * @return candidates name
   */
  public String[] getCandidate() {
    return candidates_and_party;
  }

  /**
   * Get number of ballots
   *
   * @return number of ballots
   */
  public int getNum_ballot() {
    return number_of_ballots;
  }

  /**
   * Get ballot data
   *
   * @return ballot data
   */
  public String[] getBallot() {
    return ballots;
  }

}
