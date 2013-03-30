package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Token, Morph}

class NoMorph extends Morph {
  override def apply(token: Token) = token
}
