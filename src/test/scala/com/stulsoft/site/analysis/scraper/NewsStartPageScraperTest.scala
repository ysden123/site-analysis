/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis.scraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.scalatest.flatspec.AnyFlatSpec

class NewsStartPageScraperTest extends AnyFlatSpec:
  "NewsStartPageScraper" should "extract text Lines" in {
    val browser = JsoupBrowser()
    val document = browser.parseFile("src/test/resources/test1.html")

    val linesTry = NewsStartPageScraper.extractTextLines(document)
    assert(linesTry.isSuccess)
    val lines = linesTry.get
    assert(lines.nonEmpty)
    assertResult(40)(lines.size)
  }
