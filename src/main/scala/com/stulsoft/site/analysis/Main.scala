/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis

import com.stulsoft.site.analysis.scraper.{NewsStartPageScraper, ScraperFrame}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser

import scala.swing.{Action, Dimension, Frame, MainFrame, Menu, MenuBar, MenuItem, SimpleSwingApplication}
import scala.util.{Failure, Success}

object Main extends SimpleSwingApplication:
  override def top: Frame = new MainFrame {
    val mainFrame: MainFrame = this
    val version: String = ManifestInfo("com.stulsoft", "site-analysis").version() match
      case Some(version) =>
        version
      case None =>
        ""
    title = "Site Analysis " + version

    menuBar = new MenuBar {
      contents += new Menu("Actions") {
        contents += new MenuItem(Action("Analyze news.startpage") {
          mainFrame.contents = new ScraperFrame
        })
        contents += new MenuItem(Action("Close") {
          dispose()
        })
      }
    }

    size = new Dimension(600, 400)
    centerOnScreen()
  }

/*
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
*/
