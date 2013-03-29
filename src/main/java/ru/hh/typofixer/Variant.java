package ru.hh.typofixer;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Variant extends Token {
  private final Iterable<? extends Token> values;

  public Variant(Iterable<? extends Token> values) {
    this.values = ImmutableList.copyOf(values);
  }

  @Override
  public int weight(Map<String, Integer> dict) {
    int res = 0;
    for (Token value : values) {
      Integer tres = value.weight(dict);
      res += tres == null ? 0 : tres;
    }
    return res;
  }

  @Override
  public String getValue(final Map<String, Integer> dict, final int lowBound) {
    List<? extends Token> weightOrderedTokens = Ordering.from(new Comparator<Token>() {
      @Override
      public int compare(Token o1, Token o2) {
        return Ints.compare(o1.weight(dict), o2.weight(dict));
      }
    }).reverse().sortedCopy(Iterables.filter(values, new Predicate<Token>() {
      @Override
      public boolean apply(Token input) {
        return input.weight(dict) > lowBound;
      }
    }));
    return weightOrderedTokens.size() > 0 ? weightOrderedTokens.get(0).getValue(dict, lowBound) : "";
  }

  public Iterable<? extends Token> getValues() {
    return values;
  }

  @Override
  public String toString() {
    return "Variant(" + Joiner.on('|').join(values) + ")";
  }
}
