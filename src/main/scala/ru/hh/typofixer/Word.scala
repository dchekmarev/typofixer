package ru.hh.typofixer

class Word(val string: String) extends Token {
  def weight(dict: Map[String, Int]) = dict.getOrElse(string, 0)

  def value(dict: Map[String, Int], lowBound: Int) = string

  override def toString = string
}
