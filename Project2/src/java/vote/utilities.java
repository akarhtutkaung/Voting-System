//Name of the file: utilities.java
//What the code does: Utilities to help the main class
//Authors: Akar Kaung

package vote;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Utilities that helps the main class to check the files
 */
public class utilities {

  /**
   * Check whether the file exist or not.
   *
   * @param path File path
   * @return true if file exist and false if it does not
   */
  public static boolean checkFileExist(String path) {
    File temp = new File(path);
    boolean exists = temp.exists();
    if (exists) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check whether file type is .csv
   *
   * @param path File path
   * @return true if file type is .csv, false if it isn't
   */
  public static boolean checkFileType(String path) {
    File file = new File(path);
    String fileType = "";
    try {
      fileType = Files.probeContentType(file.toPath());
    } catch (IOException err) {
      // file does not exist
      return false;
    }
    if (fileType.equals("text/csv")) {
      return true;
    } else {
      return false;
    }
  }
}
