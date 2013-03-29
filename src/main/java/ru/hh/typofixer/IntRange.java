package ru.hh.typofixer;

import com.google.common.collect.DiscreteDomains;
import com.google.common.collect.Ranges;
import java.util.NavigableSet;

public class IntRange {
  public static NavigableSet<Integer> of(int from, int to) {
    return Ranges.closedOpen(from, to).asSet(DiscreteDomains.integers());
  }
}
