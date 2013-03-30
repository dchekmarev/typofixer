package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Variant, Word, Morph}

class DoubleChars extends Morph {
  override def apply(token: Word) = {
    val value: String = token.value
    new Variant(
      (1 to value.length toList) map(idx => new Word(value.substring(0, idx).concat(value.substring(idx - 1))))
    )
  }
}
