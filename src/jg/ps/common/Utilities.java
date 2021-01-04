package jg.ps.common;

public class Utilities {

  /**
   * Returns the name of a file, without its extension
   * @param rawFileName - the name of the file, potentially having its extension
   * @return the name of a file, without its extension
   */
  public static String getBareFileName(String rawFileName) {
    int dotIndex = rawFileName.indexOf('.');
    if (dotIndex == -1) {
      return rawFileName;
    }
    else {
      return rawFileName.substring(0, dotIndex);
    }
  }
  
}
