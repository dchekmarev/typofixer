package ru.hh.typofixer;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import static java.util.Arrays.asList;
import java.util.Map;

public class Phrase extends Token {
  private final Iterable<? extends Token> values;

  public Phrase(Iterable<? extends Token> values) {
    this.values = ImmutableList.copyOf(values);
  }

  public Phrase(String... values) {
    this(Iterables.transform(asList(values), new Function<String, Word>() {
      @Override
      public Word apply(String input) {
        return new Word(input);
      }
    }));
  }

  @Override
  public int weight(Map<String, Integer> dict) {
    int res = 0;
    for (Token value : values) {
      res += value.weight(dict);
    }
    return res;
  }

  @Override
  public String getValue(final Map<String, Integer> dict, final int lowBound) {
    return Joiner.on(' ').join(Iterables.transform(values, new Function<Token, String>() {
      @Override
      public String apply(Token input) {
        return input.getValue(dict, lowBound);
      }
    }));
  }

  public Iterable<? extends Token> getValues() {
    return values;
  }

  @Override
  public String toString() {
    return "Phrase(" + Joiner.on(' ').join(values) + ")";
  }
}
