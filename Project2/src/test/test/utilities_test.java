package test;

import org.junit.Test;
import vote.utilities;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing file for utilities.java
 */
public class utilities_test {

  /**
   * Check whether the file exists or not with a valid file
   */
  @Test
  public void checkFileExistsTrue() {
    Path path = Paths.get(System.getProperty("user.dir"));
    String filePath = path + "/src/ballots/6_OPL.csv";
    System.out.println(filePath);
    assertTrue(utilities.checkFileExist(filePath));
  }

  /**
   * Check whether the file exists or not with a invalid file
   */
  @Test
  public void checkFileExistsFalse() {
    Path path = Paths.get(System.getProperty("user.dir"));
    String filePath = path + "/src/ballots/randomFile.csv";
    System.out.println(filePath);
    assertFalse(utilities.checkFileExist(filePath));
  }

  /**
   * Check whether the file type is right with a correct file type
   */
  @Test
  public void checkFileTypeTrueCSV() {
    Path path = Paths.get(System.getProperty("user.dir"));
    String filePath = path + "/src/ballots/6_OPL.csv";
    System.out.println(filePath);
    assertTrue(utilities.checkFileType(filePath));
  }

  /**
   * Check whether the file type is right with a wrong file type
   */
  @Test
  public void checkFileTypeFalseCSV() {
    Path path = Paths.get(System.getProperty("user.dir"));
    String filePath = path + "/src/ballots/fakeFile.txt";
    System.out.println(filePath);
    assertFalse(utilities.checkFileType(filePath));
  }

}
