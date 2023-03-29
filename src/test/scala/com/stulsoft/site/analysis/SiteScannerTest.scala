/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import org.scalatest.flatspec.AnyFlatSpec

import scala.util.{Failure, Success}

class SiteScannerTest extends AnyFlatSpec:
  "SiteScanner" should "extract word usage" in {
    SiteScanner.scanSite() match
      case Success(wordInfo) =>
        wordInfo.foreach(wi=>println(s"${wi.name} - ${wi.count}"))
      case Failure(exception) =>
        fail(exception.getMessage)
  }
