import exception.TransformException;
import transform.Transformers;

import java.util.Arrays;

public class Application {
  private static final String INPUT_EXAMPLE = """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
  """;

  public static char[] getForward(char[][] arr, int x, int y) {
    if (arr[y].length - x < 4) return new char[0];
    return new char[] {
      arr[y][x],
      arr[y][x+1],
      arr[y][x+2],
      arr[y][x+3],
    };
  }

  public static char[] getReverse(char[][] arr, int x, int y) {
    if (x - 4 <= 0) return new char[0];
    return new char[] {
      arr[y][x],
      arr[y][x-1],
      arr[y][x-2],
      arr[y][x-3],
    };
  }

  public static char[] getUp(char[][] arr, int x, int y) {
    if (y - 4 <= 0) return new char[0];
    return new char[] {
      arr[y][x],
      arr[y-1][x],
      arr[y-2][x],
      arr[y-3][x],
    };
  }

  public static char[] getDown(char[][] arr, int x, int y) {
    return new char[0];
  }

  public static char[] getForwardUp(char[][] arr, int x, int y) {
    return new char[0];
  }

  public static char[] getForwardDown(char[][] arr, int x, int y) {
    return new char[0];
  }

  public static char[] getReverseUp(char[][] arr, int x, int y) {
    return new char[0];
  }

  public static char[] getReverseDown(char[][] arr, int x, int y) {
    return new char[0];
  }

  public static void main(final String[] args) throws TransformException {
    final String input = Transformers.NORMALIZE_INPUT.transform(INPUT_EXAMPLE);
    assert input != null;
    assert !input.isBlank();

    final char[][] chars = Transformers.DESERIALIZE_INPUT.transform(input);
    assert chars.length != 0;

    final int length = chars[0].length;
    assert length != 0;
    for (int i = 0; i < chars.length; i++) {
      assert chars[i].length == length;
    }

    System.out.println("chars length: " + chars.length);
    System.out.println("line length: " + length);

    int sum = 0;
    for (int i = 0; i < chars.length; i++) {
      for (int j = 0; j < chars[i].length; j++) {
        final char[] match = new char[]{'X', 'M', 'A', 'S'};
        if (Arrays.equals(getForward(chars, i, j), match)) sum++;
        if (Arrays.equals(getReverse(chars, i, j), match)) sum++;
        if (Arrays.equals(getUp(chars, i, j), match)) sum++;
        if (Arrays.equals(getDown(chars, i, j), match)) sum++;
        if (Arrays.equals(getForwardUp(chars, i, j), match)) sum++;
        if (Arrays.equals(getForwardDown(chars, i, j), match)) sum++;
        if (Arrays.equals(getReverseUp(chars, i, j), match)) sum++;
        if (Arrays.equals(getReverseDown(chars, i, j), match)) sum++;
      }
    }

    System.out.println("XMAS found " + sum + " times");
  }
}
