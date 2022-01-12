//Name of the file: OPL_Obj.java
//What the code does: It is OPL_Obj class;
//                    it will be a helper class for OPL
//Authors: Moyan Zhou

package vote;

/**
 * Database for OPL
 */
public class OPL_Obj {
  private String election_type;
  private int number_candidates;
  private String[] candidates;
  private int number_seats;
  private int number_ballots;
  private String[] ballots;

  /**
   * Constructor of OPL_Obj
   *
   * @param type       election type
   * @param number_can number of candidate
   * @param candidates candidates name
   * @param num_seats  number of available seats
   * @param num_bal    total number of ballot
   * @param ballots    ballots data
   */
  public OPL_Obj(String type, int number_can, String[] candidates, int num_seats, int num_bal, String[] ballots) {
    this.election_type = type;
    this.number_candidates = number_can;
    this.candidates = candidates;
    this.number_seats = num_seats;
    this.number_ballots = num_bal;
    this.ballots = ballots;
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
   * Set election type
   *
   * @param election_type election type
   */
  public void setElection_type(String election_type) {
    this.election_type = election_type;
  }

  /**
   * Get number of candidate
   *
   * @return number of candidate
   */
  public int getNumber_candidates() {
    return number_candidates;
  }

  /**
   * Set number of candidate
   *
   * @param number_candidates number of candidate
   */
  public void setNumber_candidates(int number_candidates) {
    this.number_candidates = number_candidates;
  }

  /**
   * Get candidates name
   *
   * @return candidates name
   */
  public String[] getCandidates() {
    return candidates;
  }

  /**
   * Set candidates name
   *
   * @param candidates candidates name
   */
  public void setCandidates(String[] candidates) {
    this.candidates = candidates;
  }

  /**
   * Get number of available seats
   *
   * @return number of available seats
   */
  public int getNumber_seats() {
    return number_seats;
  }

  /**
   * Set number of available seats
   *
   * @param number_seats number of available seats
   */
  public void setNumber_seats(int number_seats) {
    this.number_seats = number_seats;
  }

  /**
   * Get number of ballots
   *
   * @return number of ballots
   */
  public int getNumber_ballots() {
    return number_ballots;
  }

  /**
   * Set number of ballots
   *
   * @param number_ballots number of ballots
   */
  public void setNumber_ballots(int number_ballots) {
    this.number_ballots = number_ballots;
  }

  /**
   * Get ballots data
   *
   * @return ballots data
   */
  public String[] getBallots() {
    return ballots;
  }

  /**
   * Set ballots data
   *
   * @param ballots ballots data
   */
  public void setBallots(String[] ballots) {
    this.ballots = ballots;
  }
}
