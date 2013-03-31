package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Phrase, Variant, Word, Morph}

class SplitChars extends Morph {
  override def apply(word: Word) = {
    val value = word.string
    if (value.length < 2)
      word
    else
      new Variant(
        1 until value.length map(idx =>
          new Phrase(List(substrToWord(value, 0, idx), substrToWord(value, idx, value.length))))
      )
  }
}
