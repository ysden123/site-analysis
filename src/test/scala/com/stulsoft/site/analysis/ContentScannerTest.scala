/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import org.scalatest.flatspec.AnyFlatSpec

import scala.io.Source
import scala.util.Using

class ContentScannerTest extends AnyFlatSpec:
  "ContentScanner" should "extract words" in {
    Using(Source.fromFile("src/test/resources/test1.html")) {
      source => {
        ContentScanner
          .extractWords(source.mkString)
          .filter(wi => wi.count > 1)
          .foreach(wi => println(s"${wi.name} - ${wi.count}"))
      }
    }
  }

  it should "return empty collection" in {
    assertResult(0)(ContentScanner
      .extractWords("hghghjgjhghj")
      .size)
  }