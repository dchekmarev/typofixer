package ru.hh.typofixer.morphers;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.hh.typofixer.IntRange;
import ru.hh.typofixer.Morph;
import ru.hh.typofixer.Phrase;
import ru.hh.typofixer.Token;
import ru.hh.typofixer.Variant;
import ru.hh.typofixer.Word;

public class SplitWords extends Morph {
  @Override
  public Variant apply(final Word input) {
    final String value = input.getValue();
    if (value.length() < 2) {
      return new Variant(ImmutableList.<Token>of());
    }
    return new Variant(Iterables.transform(IntRange.of(1, value.length() - 1).descendingSet(),
       new Function<Integer, Phrase>() {
         @Override
         public Phrase apply(Integer offset) {
           return new Phrase(Iterables.transform(ImmutableList.of(value.substring(0, offset).trim(), value.substring(offset).trim()), new Function<String, Word>() {
             @Override
             public Word apply(String input) {
               return new Word(input);
             }
           }));
         }
       }));
  }
}
