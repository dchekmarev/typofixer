package ru.hh.typofixer

import morphers._

class Morpher(val morphs: List[Morph], val pipe: Morph) extends Morph {
  def this() = this(List(), null)

  def add(morph: Morph) = new Morpher(morphs :+ morph, pipe)

  def pipe(pipe: Morph) = new Morpher(morphs, pipe)

  override def apply(source: Word) =
    new Variant(
      if (pipe == null) morphs.map(_(source))
      else morphs.map(_(source)).map(pipe.apply)
    )
}

object Morpher {
  def create = new Morpher

  val configured =
    create.
      add(new NoMorph).
      add(new SplitWords).
      pipe(create.
        add(new WrongKeyTable).
        pipe(create.
          add(new NoMorph).
          add(new SkipChar)
          add(new DoubleChars)
    ))
}