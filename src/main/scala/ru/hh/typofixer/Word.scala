package ru.hh.typofixer

class Word(val string: String) extends Token {
  def weight(dict: Dictionary) = dict.weight(string)

  def value(dict: Dictionary, lowBound: Int) = string

  override def toString = string
}
