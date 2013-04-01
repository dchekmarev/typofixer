package ru.hh.typofixer

import io.Source
import java.util

class Dictionary(val words: Array[String]) {
  def weight(word: String) = {
    val idx = util.Arrays.binarySearch(words.asInstanceOf[Array[AnyRef]], word)
    if (idx >= 0 && words(idx) == word) 10 + word.length else 0
  }
}

object Dictionary {
  def main(s:Array[String]) {
    println(prod.weight("разработчик"))
    println(prod.weight("сайтов"))
    println(prod.weight("менеджер"))
    println(prod.weight("проектов"))
    println(prod.weight("разработчик"))
  }
  def apply(words: String*) = new Dictionary(words.toArray.sorted)

  val prod = new Dictionary(Source.fromURL(getClass.getResource("/words.txt")).getLines().toArray.sorted)

  val test = Dictionary(
    "hello",
    "world",
    "привет",
    "coffee",
    "java",
    "scala",
    "php",
    "программист",
    "бухгалтер",
    "менеджер"
  )
}
