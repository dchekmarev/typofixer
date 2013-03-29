package ru.hh.typofixer;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;

public class Dictionary {
  public static final Map<String, Integer> dict = ImmutableMap.<String, Integer>builder().
     put("hello", 1).
     put("world", 1).
     put("привет", 1).
     put("coffee", 1).
     put("java", 1).
     put("scala", 1).
     put("php", 1).
     put("программист", 1).
     put("бухгалтер", 1).
     build();

  public static Map<? extends Token, Integer> match(Iterable<? extends Token> tokens, int limit) {
    Map<Token, Integer> map = Maps.newHashMap();
    for (Token token : tokens) {
      map.put(token, token.weight(dict));
    }
    return Maps.filterValues(map, new Predicate<Integer>() {
      @Override
      public boolean apply(Integer input) {
        return input > 0;
      }
    });
  }
}
