package transform;

import exception.TransformException;

public interface Transformer <T, R> {
  R transform (T in) throws TransformException;
}
