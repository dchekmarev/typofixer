package ru.hh.typofixer

class Variant(val values: List[Token]) extends Token {
  def weight(dict: Map[String, Int]) = 0 +: values.map(_.weight(dict)) max

  def value(dict: Map[String, Int], lowBound: Int) = values.sortBy(- _.weight(dict)).head.value(dict)

  override def toString = "Variant(" + values.map(_.toString).mkString("|") + ")"
}
