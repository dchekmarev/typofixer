package ru.hh.typofixer.morphers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import ru.hh.typofixer.IntRange;
import ru.hh.typofixer.Morph;
import ru.hh.typofixer.Variant;
import ru.hh.typofixer.Word;

public class DoubleChars extends Morph {
  @Override
  public Variant apply(final Word input) {
    return new Variant(Iterables.transform(IntRange.of(1, input.getValue().length() + 1),
       new Function<Integer, Word>() {
         @Override
         public Word apply(Integer index) {
           return new Word(input.getValue().substring(0, index).concat(input.getValue().substring(index - 1)));
         }
       }));
  }
}
