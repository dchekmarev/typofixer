package ru.hh.typofixer;

import java.util.Map;

abstract public class Token {
  abstract public int weight(Map<String, Integer> dict);
  abstract public String getValue(Map<String, Integer> dict, int lowBound);

  public String getValue(Map<String, Integer> dict) {
    return getValue(dict, -1);
  }
}
