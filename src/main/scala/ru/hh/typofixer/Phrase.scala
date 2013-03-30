package ru.hh.typofixer

class Phrase(val values: List[Token]) extends Token {
  def weight(dict: Map[String, Int]) = values.map(_.weight(dict)).sum

  def value(dict: Map[String, Int], lowBound: Int) = values.map(_.value(dict, lowBound)).mkString(" ")

  override def toString = "Phrase(" + values.map(_.toString).mkString(" ") + ")"
}
