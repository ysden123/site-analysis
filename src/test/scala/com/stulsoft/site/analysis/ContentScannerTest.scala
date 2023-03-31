/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import org.scalatest.flatspec.AnyFlatSpec

class ContentScannerTest extends AnyFlatSpec:
  "ContentScanner" should "extract words" in {
    val lines = List(
      "aaa bbb ccc"
    )
    val result = ContentScanner.extractWords(lines, Set())
    assertResult(3)(result.size)
  }

  it should "process special symbols" in {
    val lines = List(
      """w1,w2:w3.w4-w5"w6;w7#w8&w9[w10]w11(w12)"""
    )
    val result = ContentScanner.extractWords(lines, Set())
    assertResult(12)(result.size)
  }

  it should "process duplicated special symbols" in {
    val lines = List(
      """w1 ,w2;:w3,.w4-w5"w6;w7"""
    )
    val result = ContentScanner.extractWords(lines, Set())
    assertResult(7)(result.size)
  }
