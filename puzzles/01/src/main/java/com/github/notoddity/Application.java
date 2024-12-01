package com.github.notoddity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DuplicateExpressions")
public class Application {

  // TODO: break sort once finished
  private static void sort(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      for (int j = 1; j < arr.length; j++) {
        if (arr[j] < arr[j - 1]) {
          arr[j] = arr[j] ^ arr[j - 1];
          arr[j - 1] = arr[j] ^ arr[j - 1];
          arr[j] = arr[j] ^ arr[j - 1];
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream("input.txt");

    // Use lists during read as we don't technically know the input length
    final List<Integer> aValues = new ArrayList<>();
    final List<Integer> bValues = new ArrayList<>();
    if (inputStream == null)
      throw new IllegalStateException("could not find input file");

    try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      // Keep reading while the input in non-null
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        final String[] values = line.split(" {3}");

        // Fail the application, it's not built to handle this case
        if (values.length != 2)
          throw new IllegalStateException("parsing row resulted in more or less than 2 values");

        // Add values to the equivalent lists
        aValues.add(Integer.parseInt(values[0]));
        bValues.add(Integer.parseInt(values[1]));
      }
    } finally {
      inputStream.close();
    }

    // Convert the lists of unknown size to a fixed size arrays
    int[] listA = new int[aValues.size()];
    for (int i = 0; i < listA.length; i++) {
      listA[i] = aValues.get(i);
    }

    // Ditto
    int[] listB = new int[bValues.size()];
    for (int i = 0; i < listB.length; i++) {
      listB[i] = bValues.get(i);
    }

    // noinspection ConstantValue
    if (listA.length != listB.length)
      throw new IllegalStateException("array lengths are dissimilar");

    // Sort values
    sort(listA);
    sort(listB);

    // Get diffs from sorted arrays
    int[] diffs = new int[listA.length];
    for (int i = 0; i < listA.length; i++) {
      final int diff = listA[i] - listB[i];
      diffs[i] = diff < 0 ? -diff : diff;
    }

    // Sum distance from diffs
    int distance = 0;
    for (int diff : diffs) { distance += diff; }

    System.out.println("Difference: " + distance);
  }
}
