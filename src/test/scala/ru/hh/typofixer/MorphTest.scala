package ru.hh.typofixer

import morphers._
import org.testng.annotations.Test
import org.testng.Assert.assertEquals

class MorphTest {
  val EMPTY_WORD: Word = new Word("")

  @Test
  def testEmpty {
    Morpher.create.add(new NoMorph).apply(EMPTY_WORD)
    Morpher.create.add(new WrongKeyTable).apply(EMPTY_WORD)
    Morpher.create.add(new DoubleChars).apply(EMPTY_WORD)
    Morpher.create.add(new SkipChar).apply(EMPTY_WORD)
    Morpher.create.add(new SplitWords).apply(EMPTY_WORD)
  }

  @Test
  def testWrongKeyTable {
    val morph = new WrongKeyTable
    assertEquals(morph.apply(new Word("руддщ")).value(Map("hello" -> 1)), "hello")
    assertEquals(morph.apply(new Word("ntcn")).value(Map("тест" -> 1)), "тест")
    assertEquals(morph.apply(new Word("сай")).value(Map("сай" -> 1, "cfq" -> 2)), "cfq")
    assertEquals(morph.apply(new Word("сай")).value(Map("сай" -> 2, "cfq" -> 1)), "сай")
  }

  @Test
  def testReal {
    testMorph(Morpher.configured, "", "")
    testMorph(Morpher.configured, "javaghjuhfvvbcn", "java программист")
    testMorph(Morpher.configured, "ghjuhfvvbcnjavva", "программист java")
  }

  def testMorph(morpher: Morpher, source: String, target: String) {
    testMorph(morpher, new Word(source), target)
  }

  def testMorph(morpher: Morpher, source: Token, target: String) {
    val result: Token = morpher.apply(source)
    println(source + " -> " + result)
    val resultValue: String = result.value(Dictionary.dict)
    println(source + " -> " + resultValue)
    println("expect: " + target)
    assertEquals(resultValue, target)
    println("ok")
  }

  @Test(enabled = false)
  def perfTest {
    1 to 5 foreach (x =>
      time {
        1 to 10000 foreach (i => {
          Morpher.configured.apply(new Word("ghjuhfvvbcnjava"))
        })
      }
    )
  }

  def time[R](block: => R): R = {
    val t0 = System.currentTimeMillis()
    val result = block
    val t1 = System.currentTimeMillis()
    println("Elapsed time: " + (t1 - t0) + "ms")
    result
  }
}
