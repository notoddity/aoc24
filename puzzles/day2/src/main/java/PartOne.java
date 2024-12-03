import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
  private static final int[][] EXAMPLE = new int[][] {
    {7, 6, 4, 2, 1},
    {1, 2, 7, 8, 9},
    {9, 7, 6, 2, 1},
    {1, 3, 2, 4, 5},
    {8, 6, 4, 4, 1},
    {1, 3, 6, 7, 9},
  };

  public static int count(final int[][] reports) {
    final boolean[] safe = new boolean[reports.length];

    outer:
    for (int i = 0; i < reports.length; i++) {
      final int[] report = reports[i];
      assert report.length >= 2;

      final Boolean increasing = report[0] < report[1];

      for (int j = 1; j < report.length; j++) {
        final int diff = report[j-1] - report[j];
        final int abs = diff < 0 ? -diff : diff;
        if (abs < 1) continue outer;
        if (abs > 3) continue outer;
        if (increasing && report[j-1] > report[j]) continue outer;
        if (!increasing && report[j-1] < report[j]) continue outer;
      }

      safe[i] = true;
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
