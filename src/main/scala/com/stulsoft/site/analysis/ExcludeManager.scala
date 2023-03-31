/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

object ExcludeManager:
  def wordsToExclude_rus:Set[String]=
    Set("на", "по", "не", "от", "из", "как", "что", "за", "раз", "под")
