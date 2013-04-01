package ru.hh.typofixer

class Phrase(val values: Seq[Token]) extends Token {
  def weight(dict: Dictionary) = values.map(_.weight(dict)).sum

  def value(dict: Dictionary, lowBound: Int) = values.map(_.value(dict, lowBound)).mkString(" ")

  override def toString = "Phrase(" + values.map(_.toString).mkString(" ") + ")"
}
