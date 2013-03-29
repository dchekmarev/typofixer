package ru.hh.typofixer.morphers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import ru.hh.typofixer.IntRange;
import ru.hh.typofixer.Morph;
import ru.hh.typofixer.Token;
import ru.hh.typofixer.Variant;
import ru.hh.typofixer.Word;

public class SkipChar extends Morph {
  @Override
  public Token apply(final Word input) {
    final String value = input.getValue();
    if (value.length() == 0) {
      return input;
    }
    return new Variant(Iterables.transform(IntRange.of(0, value.length() - 1),
       new Function<Integer, Word>() {
         @Override
         public Word apply(Integer offset) {
           return new Word(value.substring(0, offset).concat(value.substring(offset + 1)));
         }
       }));
  }
}
