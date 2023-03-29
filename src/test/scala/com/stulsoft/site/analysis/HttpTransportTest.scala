/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import org.apache.hc.core5.http.HttpEntity
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.{Failure, Success}

class HttpTransportTest extends AnyFlatSpec:
  "HttpTransport" should "return body" in {
    def processEntity(httpEntity: HttpEntity): Int =
      val result = ContentScanner.extractWords(EntityUtils.toString(httpEntity)).size
      EntityUtils.consume(httpEntity)
      result

    HttpTransport.get("https://news.startpage.co.il/russian/")(processEntity) match
      case Success(size) => assert(size > 0)
      case Failure(exception) => fail(exception.getMessage)
  }