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
    Morpher.create.add(new SplitChars).apply(EMPTY_WORD)
  }

  @Test
  def testWrongKeyTable {
    val morph = new WrongKeyTable
    assertEquals(morph.apply(new Word("руддщ")).value(Dictionary("hello")), "hello")
    assertEquals(morph.apply(new Word("ntcn")).value(Dictionary("тест")), "тест")
  }

  @Test
  def testPermutation {
    assertEquals(new Permutation().apply(new Word("ehllo")).value(Dictionary("hello")), "hello")
    assertEquals(new Permutation(2).apply(new Word("sotfwaer")).value(Dictionary("software")), "software")
  }

  @Test
  def testSplitWords {
    assertEquals(new SplitWords().apply(new Word("hello,world")).value(Dictionary("hello")), "hello world")
  }

  @Test
  def testReal {
    realMorph(Morpher.configured, "", "")
    realMorph(Morpher.configured, "java-ghjuhfvvbcn", "java программист")
    realMorph(Morpher.configured, "ghjuhfvvbcn javva", "программист java")
    realMorph(Morpher.configured, "vtytl;thghjtrnjd", "менеджер проектов")
  }

  def testMorph(morpher: Morpher, source: String, target: String) {
    testMorph(morpher, new Word(source), target, Dictionary.test)
  }

  def realMorph(morpher: Morpher, source: String, target: String) {
    testMorph(morpher, new Word(source), target, Dictionary.prod)
  }

  def testMorph(morpher: Morpher, source: Token, target: String, dict: Dictionary) {
    val result: Token = morpher.apply(source)
    println(source + " -> " + result)
    val resultValue: String = result.value(dict)
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
