package ru.hh.typofixer

class Word(val value: String) extends Token {
  def weight(dict: Map[String, Int]) = dict.getOrElse(value, 0)

  def value(dict: Map[String, Int], lowBound: Int) = value

  override def toString = value
}
