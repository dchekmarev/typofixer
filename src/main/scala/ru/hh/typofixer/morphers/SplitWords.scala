package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Phrase, Variant, Word, Morph}

class SplitWords(val separators: Array[Char]) extends Morph {
  def this() = this(Array(' ', ',', ';', '.', '-', '=', '/'))

  override def apply(token: Word) = {
    val value = token.string
    if (value.length < 2)
      token
    else
      apply(value, 0)
  }

  def apply(value: String, offset: Int): Variant = {
    new Variant(new Word(value) +: (
      offset until (value.length) filter(idx => separators.contains(value.charAt(idx))) map(idx =>
        new Variant(
          List(
            new Phrase(List(substrToWord(value, 0, idx), substrToWord(value, idx + 1, value.length))),
            apply(value, idx + 1)
          )
        )
      )
    ))
  }
}
