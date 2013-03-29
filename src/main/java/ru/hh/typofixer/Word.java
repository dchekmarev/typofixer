package ru.hh.typofixer;

import java.util.Map;

public class Word extends Token {
  private final String value;

  public Word(String value) {
    this.value = value;
  }

  @Override
  public int weight(Map<String, Integer> dict) {
    Integer res = dict.get(value);
    return res == null ? 0 : res;
  }

  @Override
  public String getValue(Map<String, Integer> dict, int lowBound) {
    return value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
