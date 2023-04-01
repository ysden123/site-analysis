/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis.scraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Document

import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success, Try}

object NewsStartPageScraper:

  private def getDocument: Document =
    val browser = JsoupBrowser()
    browser.get("https://news.startpage.co.il/russian/")

  def extractTextLines(document: Document): Try[Iterable[String]] =
    Try {
      document
        .body
        .select("#content").head
        .children
        .filter(element => "div" == element.tagName && "colum" == element.attr("class"))
        .head
        .children
        .filter(element => "div" == element.tagName && "box" == element.attr("class"))
        .head
        .children
        .filter(element => "ul" == element.tagName && element.hasAttr("class") && "menu" == element.attr("class"))
        .flatMap(element => element.children)
        .filter(element => "li" == element.tagName)
        .flatMap(element => element.children)
        .filter(element => "span" == element.tagName)
        .flatMap(element => element.children)
        .filter(element => "a" == element.tagName)
        .map(element => element.text)
    }

  def buildWordDefinition(): Future[String] =
    val promise = Promise[String]()
    val document = NewsStartPageScraper.getDocument
    NewsStartPageScraper.extractTextLines(document) match
      case Success(lines) =>
        val result = ContentScanner.extractWords(lines, ExcludeManager.wordsToExclude_rus)
          .filter(wi => wi.count > 1)
          .map(wi => s"${wi.name} - ${wi.count}")
          .mkString("\n")
        promise.success(result)
      case Failure(exception) =>
        promise.failure(exception)
    promise.future