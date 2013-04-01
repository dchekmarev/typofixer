package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Variant, Word, Morph}

class Permutation(val times: Int) extends Morph {
  def this() = this(1)

  override def apply(token: Word) = new Variant(apply(token.string, 0, times).map(new Word(_)))

  def apply(value: String, offset: Int, times: Int): Seq[String] = {
    if (times == 0)
      List(value)
    else
      List(value) ++
      (offset until (value.length - 1) map (i =>
        apply(value.substring(0, i) + value.charAt(i + 1) + value.charAt(i) + value.substring(i + 2), i + 2, times - 1)
      )).flatten
  }
}
