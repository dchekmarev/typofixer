package ru.hh.typofixer.morphers;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import ru.hh.typofixer.Morph;
import ru.hh.typofixer.Variant;
import ru.hh.typofixer.Word;
import java.util.List;

public class WrongKeyTable extends Morph {
  private static final String ENGLISH = "qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>?";
  private static final String RUSSIAN = "йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,";

  private static final List<String> TABLES = ImmutableList.of(
     ENGLISH,
     RUSSIAN
  );

  @Override
  public Variant apply(final Word input) {
    return new Variant(Iterables.transform(TABLES, new Function<String, Word>() {
      @Override
      public Word apply(String toTable) {
        String value = input.getValue();
        char[] result = new char[value.length()];
        for (int i = 0; i < value.length(); i++) {
          boolean found = false;
          for (String fromTable : TABLES) {
            int idx = fromTable.indexOf(value.charAt(i));
            if (idx != -1) {
              result[i] = toTable.charAt(idx);
              found = true;
              break;
            }
          }
          if (!found) {
            result[i] = value.charAt(i);
          }
        }
        return new Word(new String(result));
      }
    }));
  }
}
