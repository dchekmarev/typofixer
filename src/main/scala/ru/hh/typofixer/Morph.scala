package ru.hh.typofixer

import java.lang.IllegalArgumentException

abstract class Morph {
  def apply(token: Token): Token =
    token match {
      case token: Word => apply(token)
      case token: Phrase => apply(token)
      case token: Variant => apply(token)
      case _ => throw new IllegalArgumentException(token.getClass.getCanonicalName)
    }

  def apply(token: Word): Token = token

  def apply(token: Variant): Token = new Variant(applyToList(token.values))

  def apply(token: Phrase): Token = new Phrase(applyToList(token.values))

  def applyToList(tokens: List[Token]): List[Token] = tokens.map(apply)
}
