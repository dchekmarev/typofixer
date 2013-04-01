package ru.hh.typofixer

abstract class Token {
  def weight(dict: Dictionary): Int

  def value(dict: Dictionary, lowBound: Int): String

  def value(dict: Dictionary): String = value(dict, -1)

  def toString: String
}
