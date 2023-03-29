/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import com.typesafe.scalalogging.StrictLogging
import org.apache.hc.core5.http.HttpEntity
import org.apache.hc.core5.http.io.entity.EntityUtils

import scala.util.{Failure, Try}

object SiteScanner:
  def scanSite(): Try[Iterable[WordInfo]] =
    Try {
      var result: Iterable[WordInfo] = Nil

      def f(httpEntity: HttpEntity): Iterable[WordInfo] =
        result = ContentScanner.extractWords(EntityUtils.toString(httpEntity))
        EntityUtils.consume(httpEntity)
        result

      HttpTransport.get("https://news.startpage.co.il/russian/")(f)

      result
    }