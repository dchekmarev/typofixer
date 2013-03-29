package ru.hh.typofixer;

import org.junit.Test;
import ru.hh.typofixer.morphers.DoubleChars;
import ru.hh.typofixer.morphers.NoMorph;
import ru.hh.typofixer.morphers.SkipChar;
import ru.hh.typofixer.morphers.SplitWords;
import ru.hh.typofixer.morphers.WrongKeyTable;

import static org.junit.Assert.assertEquals;

public class MorpherTest {
  private static final Word EMPTY_WORD = new Word("");

  @Test
  public void testEmpty() {
    Morpher.create().add(new NoMorph()).apply(EMPTY_WORD);
    Morpher.create().add(new WrongKeyTable()).apply(EMPTY_WORD);
    Morpher.create().add(new DoubleChars()).apply(EMPTY_WORD);
    Morpher.create().add(new SkipChar()).apply(EMPTY_WORD);
    Morpher.create().add(new SplitWords()).apply(EMPTY_WORD);
  }

  @Test
  public void testSimple() {
    testMorph(Morpher.create().add(new NoMorph()), "test", "test");
    testMorph(Morpher.create().add(new WrongKeyTable()), "ghbdtn", "привет");
    testMorph(Morpher.create().add(new DoubleChars()), "cofee", "coffee");
    testMorph(Morpher.create().add(new SplitWords()), "helloworld", "hello world");
    testMorph(Morpher.create().add(new SplitWords()), "hello world", "hello world");
    testMorph(Morpher.create().add(new SkipChar()), new Phrase("hello", "worrld"), "hello world");
  }

  @Test
  public void testDouble() {
    testMorph(Morpher.create().add(new SplitWords()).pipe(new WrongKeyTable()), "javaghjuhfvvbcn", "java программист");
  }

  @Test
  public void testReal() {
    testMorph(Morpher.configured(), "", "");
    testMorph(Morpher.configured(), "javaghjuhfvvbcn", "java программист");
    testMorph(Morpher.configured(), "ghjuhfvvbcnjava", "программист java");
  }

  private void testMorph(Morpher morpher, String source, String target) {
    testMorph(morpher, new Word(source), target);
  }
  private void testMorph(Morpher morpher, Token source, String target) {
    Token result = morpher.apply(source);
    System.out.println(source + " -> " + result);
    String resultValue = result.getValue(Dictionary.dict);
    System.out.println(source + " -> " + resultValue);
    System.out.println("expect: " + target);
    assertEquals(resultValue, target);
    System.out.println("ok");
  }
}
