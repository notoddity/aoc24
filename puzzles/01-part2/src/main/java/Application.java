import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Application {
  public static void main(String[] args) throws IOException {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;

    final InputStream inputStream = classLoader.getResourceAsStream("input.txt");
    assert inputStream != null;

    final List<Integer> aValues = new ArrayList<>();
    final List<Integer> bValues = new ArrayList<>();
    try (final var inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         final var bufferedReader = new BufferedReader(inputStreamReader)) {

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        final String[] values = line.split(" {3}");
        assert values.length == 2;

        aValues.add(Integer.parseInt(values[0]));
        bValues.add(Integer.parseInt(values[1]));
      }
    } finally {
      inputStream.close();
    }

    int[] aLocations = new int[aValues.size()];
    for (int i = 0; i < aLocations.length; i++) {
      assert aValues.get(i) != null;
      aLocations[i] = aValues.get(i);
    }

    int[] bLocations = new int[bValues.size()];
    for (int i = 0; i < bLocations.length; i++) {
      assert bValues.get(i) != null;
      bLocations[i] = bValues.get(i);
    }

    int bMax = 0;
    for (int i = 0; i < bLocations.length; i++) {
      // noinspection ManualMinMaxCalculation
      bMax = bLocations[i] < bMax ? bMax : bLocations[i];
    }

    int[] bCount = new int[bMax];
    for (int i = 0; i < bLocations.length; i++) {
      bCount[bLocations[i] - 1]++;
    }

    int difference = 0;
    for (int i = 0; i < aLocations.length; i++) {
      if (aLocations[i] - 1 > bCount.length) continue;
      difference += aLocations[i] * bCount[aLocations[i] - 1];
    }

    System.out.println("Difference: " + difference);
  }
}
