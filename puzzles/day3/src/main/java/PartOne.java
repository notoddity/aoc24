import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PartOne {
  public static final String EXAMPLE = "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))";
  public static final String EXAMPLE_2 = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
  public static char[] DIGITS = new char[] {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };

  public static void main(String[] args) throws IOException {
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final InputStream inputStream = classLoader.getResourceAsStream("input.txt");

    StringBuilder input = new StringBuilder();
    try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) input.append(line);
    }

    //final char[] characters = EXAMPLE.toCharArray();
    //final char[] characters = EXAMPLE_2.toCharArray();
    final char[] characters = input.toString().toCharArray();
    long sum = 0;

    boolean enabled = true;

    outer:
    for (int i = 0; i < characters.length; i++) {
      if (i+3 < characters.length - 1 &&
          characters[i] == 'd' &&
          characters[i+1] == 'o' &&
          characters[i+2] == '(' &&
          characters[i+3] == ')') {
        i += 3;
        enabled = true;
        continue;
      }

      if (i+6 < characters.length - 1 &&
          characters[i] == 'd' &&
          characters[i+1] == 'o' &&
          characters[i+2] == 'n' &&
          characters[i+3] == 39 &&
          characters[i+4] == 't' &&
          characters[i+5] == '(' &&
          characters[i+6] == ')') {
        i += 6;
        enabled = false;
        continue;
      }

      if (enabled &&
          characters[i] == 'm' &&
          characters[++i] == 'u' &&
          characters[++i] == 'l' &&
          characters[++i] == '(') {

        i++;
        StringBuilder argumentOne = new StringBuilder();

        inner:
        for (int j = 0; j < characters.length - i; j++) {
          if (characters[i] == ',') {
            i++;
            break;
          }

          for (char digit : DIGITS) {
            if (characters[i] == digit) {
              argumentOne.append(characters[i++]);
              continue inner;
            }
          }

          continue outer;
        }

        StringBuilder argumentTwo = new StringBuilder();

        inner:
        for (int j = 0; j < characters.length - i; j++) {
          if (characters[i] == ')') {
            break;
          }

          for (char digit : DIGITS) {
            if (characters[i] == digit) {
              argumentTwo.append(characters[i++]);
              continue inner;
            }
          }

          continue outer;
        }

        sum += Long.parseLong(argumentOne.toString()) * Long.parseLong(argumentTwo.toString());
        System.out.println("Current sum: " + sum);
      }
    }

    System.out.println("Final sum: " + sum);
  }
}
