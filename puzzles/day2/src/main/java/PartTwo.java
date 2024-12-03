import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PartTwo {
  private static final int[][] EXAMPLE = new int[][] {
    {7, 6, 4, 2, 1},
    {1, 2, 7, 8, 9},
    {9, 7, 6, 2, 1},
    {1, 3, 2, 4, 5},
    {8, 6, 4, 4, 1},
    {1, 3, 6, 7, 9},
  };

  public static int safe(final int[] report) {
    assert report.length >= 2;
    final boolean increasing = report[0] < report[1];

    for (int i = 1; i < report.length; i++) {
      final int diff = report[i-1] - report[i];
      final int abs = diff < 0 ? -diff : diff;

      if (increasing && report[i-1] > report[i] ||
          !increasing && report[i-1] < report[i] ||
          abs < 1 ||
          abs > 3)
        return i-1;
    }

    return -1;
  }

  public static int[] delete(final int[] arr, final int index) {
    final int[] newArr = new int[arr.length - 1];

    if (index != 0) {
      System.arraycopy(arr, 0, newArr, 0, index);
    }

    if (index != arr.length - 1) {
      final int srcPos = index + 1;
      final int length = arr.length - index - 1;
      System.arraycopy(arr, srcPos, newArr, index, length);
    }

    return newArr;
  }

  public static int count(final int[][] reports) {
    final boolean[] safe = new boolean[reports.length];

    outer:
    for (int i = 0; i < reports.length; i++) {
      final int unsafeIndex = safe(reports[i]);
      if (unsafeIndex < 0) {
        safe[i] = true;
        continue;
      }

      for (int j = 0; j < reports[i].length; j++) {
        if (safe(delete(reports[i], j)) < 0) {
          safe[i] = true;
          continue outer;
        }
      }
    }

    int safeReports = 0;
    for (int i = 0; i < safe.length; i++) {
      if (safe[i]) safeReports++;
    }

    return safeReports;
  }

  public static void main(String[] args) throws IOException {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream("input.txt");

    final List<String> rawReports = new ArrayList<>();
    try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      String line;
      while ((line = bufferedReader.readLine()) != null) rawReports.add(line);
    }

    final int[][] reports = new int[rawReports.size()][];
    for (int i = 0; i < rawReports.size(); i++) {
      assert rawReports.get(i) != null;

      final String[] rawValues = rawReports.get(i).split(" ");
      final int[] values = new int[rawValues.length];

      for (int j = 0; j < rawValues.length; j++) {
        values[j] = Integer.parseInt(rawValues[j]);
      }

      reports[i] = values;
    }

    System.out.println("There are " + count(EXAMPLE) + " safe reports in the example.");
    System.out.println("There are " + count(reports) + " safe reports.");
  }
}
