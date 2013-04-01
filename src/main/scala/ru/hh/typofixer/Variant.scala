package ru.hh.typofixer

class Variant(val values: Seq[Token]) extends Token {
  def weight(dict: Dictionary) = 0 +: values.map(_.weight(dict)) max

  def value(dict: Dictionary, lowBound: Int) = values.sortBy(- _.weight(dict)).head.value(dict)

  override def toString = "Variant(" + values.map(_.toString).mkString("|") + ")"
}
