package ru.hh.typofixer.morphers

import ru.hh.typofixer.{Morph, Variant, Word}

class WrongKeyTable extends Morph {
  val ENGLISH = "qwertyuiop[]asdfghjkl;'zxcvbnm,./QWERTYUIOP{}ASDFGHJKL:\"ZXCVBNM<>?"

  val RUSSIAN = "йцукенгшщзхъфывапролджэячсмитьбю.ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ,"

  val TABLES = List(ENGLISH, RUSSIAN)
  val INDEXES = TABLES.map(table => table -> table.map(ch => ch -> table.indexOf(ch)).toMap).toMap

  override def apply(token: Word) = {
    val value = token.string
    new Variant(TABLES.map(toTable =>
      new Word(value.map(ch =>
        TABLES.map(fromTable => {
          val idx = INDEXES(fromTable).getOrElse(ch, -1)
          if (idx > -1)
            toTable.charAt(idx)
          else
            null
        }).filter(_ != null).headOption.getOrElse(ch)
      ).mkString)
    ))
  }
}
