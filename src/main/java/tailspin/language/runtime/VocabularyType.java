package tailspin.language.runtime;

public class VocabularyType implements Comparable<VocabularyType> {
  private final String key;

  public VocabularyType(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }

  @Override
  public int compareTo(VocabularyType o) {
    return key.compareTo(o.key);
  }
}
