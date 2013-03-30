package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Phrase, Variant, Word, Morph}

class SplitWords extends Morph {
  override def apply(word: Word) = {
    val value = word.value
    if (value.length < 2)
      word
    else
      new Variant(
        1 until value.length map(idx =>
          new Phrase(List(substrToWord(value, 0, idx), substrToWord(value, idx, value.length))))
      )
  }

  def substrToWord(value: String, from: Int, to: Int): Word = {
    new Word(value.substring(from, to))
  }
}
