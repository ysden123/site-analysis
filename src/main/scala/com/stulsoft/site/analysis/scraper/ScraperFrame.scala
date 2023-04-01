/*
 * Copyright (c) 2023. StulSoft
 */

package com.stulsoft.site.analysis.scraper

import java.awt.Cursor
import javax.swing.SwingUtilities
import scala.concurrent.Future
import scala.swing.Swing.EtchedBorder
import scala.swing.event.ButtonClicked
import scala.swing.*
import scala.util.{Failure, Success}

given ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

class ScraperFrame extends BorderPanel {
  private val thePanel: Panel = this
  private val runButton: Button = new Button("Run scraper") {
    enabled = true
    reactions += {
      case ButtonClicked(_) =>
        thePanel.cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
        resultList.text = "Please wait..."
        enabled = false
        SwingUtilities.invokeLater(() => {
          NewsStartPageScraper.buildWordDefinition().onComplete {
            case Success(wordsDistribution) =>
              resultList.text = wordsDistribution
              thePanel.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
              enabled = true
            case Failure(exception) =>
              resultList.text = exception.getMessage
              thePanel.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)
              enabled = true
          }
        })
    }
  }

  private val resultList = new TextArea(10, 20) {
    editable = false
  }

  private val result = new ScrollPane {
    border = Swing.TitledBorder(EtchedBorder, "Result")
    contents = resultList
  }

  private val controlPanel = new FlowPanel(FlowPanel.Alignment.Left)(runButton)

  layout(controlPanel) = BorderPanel.Position.North
  layout(result) = BorderPanel.Position.Center
  border = Swing.TitledBorder(EtchedBorder, "Word distribution on news.startpage")
}
