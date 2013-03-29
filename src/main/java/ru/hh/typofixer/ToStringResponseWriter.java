package ru.hh.typofixer;

import java.util.Map;

public class ToStringResponseWriter implements ResponseWriter {
  @Override
  public String apply(Map<? extends Token, Integer> input) {
    return input.toString();
  }
}
