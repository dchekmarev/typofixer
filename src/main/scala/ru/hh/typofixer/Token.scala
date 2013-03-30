package ru.hh.typofixer

abstract class Token {
  def weight(dict: Map[String, Int]): Int

  def value(dict: Map[String, Int], lowBound: Int): String

  def value(dict: Map[String, Int]): String = value(dict, -1)

  def toString: String
}
