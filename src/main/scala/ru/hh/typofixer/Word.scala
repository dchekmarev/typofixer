package ru.hh.typofixer

class Word(val string: String) extends Token {
  val weight = Dictionary.prod.weight(string)
  val found = weight > 0

  def weight(dict: Dictionary) = weight

  def value(dict: Dictionary, lowBound: Int) = string

  override def toString = "w!" + string
}
