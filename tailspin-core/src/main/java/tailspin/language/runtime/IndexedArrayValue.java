package tailspin.language.runtime;

public record IndexedArrayValue(Reference indexVar, Object index, Object value) {

  public IndexedArrayValue withValue(Object result) {
    return new IndexedArrayValue(indexVar, index, result);
  }
}
