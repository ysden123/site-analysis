/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import com.stulsoft.site.analysis.scraper.NewsStartPageScraper
import net.ruippeixotog.scalascraper.browser.JsoupBrowser

import scala.util.{Failure, Success}

object Main:
  def main(args: Array[String]): Unit =
    val document = NewsStartPageScraper.getDocument
    NewsStartPageScraper.extractTextLines(document) match
      case Success(lines) =>
        println("Word distribution:")
        ContentScanner.extractWords(lines, ExcludeManager.wordsToExclude_rus)
          .filter(wi => wi.count > 1)
          .foreach(wi => println(s"${wi.name} - ${wi.count}"))
      case Failure(exception) =>
        exception.printStackTrace()
