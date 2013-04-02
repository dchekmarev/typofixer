package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Phrase, Variant, Word, Morph}

class SplitCharsets extends Morph {
  override def apply(token: Word) = {
    val value = token.string
    if (value.length < 2)
      token
    else
      apply(value, 0)
  }

  def apply(value: String, offset: Int): Variant = {
    new Variant(new Word(value) +: (
      offset until (value.length - 1)
        filter(idx => Character.UnicodeBlock.of(value.charAt(idx)) != Character.UnicodeBlock.of(value.charAt(idx + 1)))
        map(idx =>
          new Variant(
            List(
              new Phrase(List(substrToWord(value, 0, idx + 1), substrToWord(value, idx + 1, value.length))),
              apply(value, idx + 1)
            )
          )
        )
    ))
  }
}
