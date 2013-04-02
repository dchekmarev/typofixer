package ru.hh.typofixer

import java.lang.IllegalArgumentException

abstract class Morph {
  def apply(token: Token): Token =
    token match {
      case token: Word => if (token.found) token else apply(token)
      case token: Phrase => apply(token)
      case token: Variant => apply(token)
      case _ => throw new IllegalArgumentException(token.getClass.getCanonicalName)
    }

  def apply(token: Word): Token = token

  def apply(token: Variant): Token = new Variant(token.values.map(apply))

  def apply(token: Phrase): Token = new Phrase(token.values.map(apply))

  def substrToWord(value: String, from: Int, to: Int): Word = {
    new Word(value.substring(from, to))
  }
}
