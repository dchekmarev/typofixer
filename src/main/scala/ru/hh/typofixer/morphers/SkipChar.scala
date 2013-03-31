package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Variant, Word, Morph}

class SkipChar extends Morph {
  override def apply(token: Word) = {
    val value = token.string
    if (value.length == 0)
      token
    else
      new Variant(
        0 until (value.length - 1) map(idx =>
          new Word(value.substring(0, idx).concat(value.substring(idx + 1))))
      )
  }
}
