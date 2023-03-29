/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import com.typesafe.scalalogging.StrictLogging

import scala.collection.mutable
import java.io.File
import scala.io.Source
import scala.util.{Failure, Success, Using}

class WordInfo(val name: String, var count: Int = 0) {
  def increaseCount(): Unit =
    count += 1

  override def toString: String = s"WordInfo{" +
    s"$name" +
    s", $count" +
    s"}"
}

object ContentScanner extends StrictLogging:
  def extractWords(text: String): Iterable[WordInfo] =
    Using(Source.fromString(text)) {
      source => {
        val wordInfoMap = new mutable.HashMap[String, WordInfo]
        var count = 0
        for (line <- source.getLines()) {
          if line.startsWith("""<ul class="menu">""") then
            count += 1
            val startText1 = """target='_blank'"""
            val startText2 = """target="_blank"""
            var start = line.indexOf(startText1)
            if start < 0 then
              start = line.indexOf(startText2)

            if start < 0 then
              logger.warn("Can't define text - start text is missing")
            else
              start += startText1.length + 1
              val end = line.indexOf("""</a>""")
              if end > start then
                val text = line.substring(start, end)
                text.split("""[ ,:".-]""")
                  .toList
                  .map(w => w.replaceAll(""""""", ""))
                  .map(w => w.replaceAll("""«""", ""))
                  .map(w => w.replaceAll("""»""", ""))
                  .map(w => w.replaceAll("""“""", ""))
                  .map(w => w.replaceAll("""”""", ""))
                  .filter(i => !i.isBlank && i.nonEmpty)
                  .filter(i => !i.forall(Character.isDigit))
                  .filter(i => i.length > 1)
                  .filter(i => !List("на", "по", "не", "от", "как", "что").contains(i))
                  .foreach(word => wordInfoMap.getOrElseUpdate(word, WordInfo(word)).increaseCount())
              else
                logger.warn("Can't define text - end text is missing")
        }
        wordInfoMap
          .values
          .toList
          .sortBy(_.count)
          .reverse
      }
    } match
      case Success(words) =>
        words
      case Failure(exception) =>
        logger.error(exception.getMessage, exception)
        Nil
