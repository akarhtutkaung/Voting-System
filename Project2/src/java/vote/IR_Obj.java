//Name of the file: IR_Obj.java
//What the code does: It is IR_Obj class;
//                    it will be a helper class for IR
//Authors: Hao Wu
package vote;

/**
 * Database for IR
 */
public class IR_Obj {
  private String election_type;
  private int num_candidate;
  private String[] candidate;
  private int num_ballot;
  private String[] ballot;

  /**
   * Constructor for IR_OBJ
   *
   * @param election_type type of election
   * @param num_candidate number of candidate
   * @param candidate     candidate names
   * @param num_ballot    number of ballots
   * @param ballot        ballot data
   */
  public IR_Obj(String election_type, int num_candidate, String[] candidate, int num_ballot, String[] ballot) {
    this.election_type = election_type;
    this.num_candidate = num_candidate;
    this.num_ballot = num_ballot;
    this.candidate = candidate;
    this.ballot = ballot;
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
    this.num_candidate = num_candidate;
  }

  /**
   * Set candidates names
   *
   * @param candidate candidate names
   */
  public void setCandidate(String[] candidate) {
    this.candidate = candidate;
  }

  /**
   * Set number of ballots
   *
   * @param num_ballot number of ballots
   */
  public void setNum_ballot(int num_ballot) {
    this.num_ballot = num_ballot;
  }

  /**
   * Set ballot data
   *
   * @param ballot ballot data
   */
  public void setBallot(String[] ballot) {
    this.ballot = ballot;
  }

  /**
   * Get election type
   *
   * @return election type
   */
  public String GetElection_type() {
    return election_type;
  }

  /**
   * Get number of candidates
   *
   * @return number of candidate
   */
  public int GetNum_candidate() {
    return num_candidate;
  }

  /**
   * Get candidates name
   *
   * @return candidates name
   */
  public String[] getCandidate() {
    return candidate;
  }

  /**
   * Get number of ballots
   *
   * @return number of ballots
   */
  public int getNum_ballot() {
    return num_ballot;
  }

  /**
   * Get ballot data
   *
   * @return ballot data
   */
  public String[] getBallot() {
    return ballot;
  }
}
