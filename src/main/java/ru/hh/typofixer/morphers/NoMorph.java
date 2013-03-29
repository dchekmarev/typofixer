package ru.hh.typofixer.morphers;

import ru.hh.typofixer.Morph;
import ru.hh.typofixer.Word;

public class NoMorph extends Morph {
  @Override
  public Word apply(Word input) {
    return input;
  }
}
