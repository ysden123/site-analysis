/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis.scraper

import com.typesafe.scalalogging.StrictLogging

import java.io.File
import scala.collection.mutable
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
  def extractWords(lines: Iterable[String], wordsToExclude:Set[String]): Iterable[WordInfo] =
    val wordInfoMap = new mutable.HashMap[String, WordInfo]
    lines.foreach(line =>
      line.split("""[ ,()\[\];:&".#-]""").toList
        .map(w => w.replaceAll(""""""", ""))
        .map(w => w.replaceAll("""«""", ""))
        .map(w => w.replaceAll("""»""", ""))
        .map(w => w.replaceAll("""“""", ""))
        .map(w => w.replaceAll("""”""", ""))
        .filter(i => !i.isBlank && i.nonEmpty)
        .filter(i => !i.forall(Character.isDigit))
        .filter(i => i.length > 1)
        .filter(i => !wordsToExclude.contains(i.toLowerCase()))
        .foreach(word => wordInfoMap.getOrElseUpdate(word, WordInfo(word)).increaseCount())
    )
    wordInfoMap
      .values
      .toList
      .sortBy(_.count)
      .reverse
