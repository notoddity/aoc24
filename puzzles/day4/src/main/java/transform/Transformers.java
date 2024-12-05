package transform;

public class Transformers {
  public static final Transformer<String, String> NORMALIZE_INPUT = (in) -> in
    .replace(" ", "")
    .replace("\t", "");

  public static final Transformer<String, char[][]> DESERIALIZE_INPUT = (in) -> {
    final String[] lines = in.split("\n");
    final char[][] chars = new char[lines.length][];
    for (int i = 0; i < lines.length; i++) {
      chars[i] = lines[i].toCharArray();
    }
    return chars;
  };
}
