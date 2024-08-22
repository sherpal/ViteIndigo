package entrypoint

import com.raquo.laminar.api.L._
import org.scalajs.dom

@main def run(): Unit = {
  println("Hello, world!")

  val canvasId = "the-canvas"

  val inputVar          = Var(0)
  val startGameBus      = new EventBus[Unit]
  val gameStartedSignal = startGameBus.events.mapTo(true).startWith(false)

  val elem = div(
    h1("Hello, Laminar!"),
    p("This is a simple example of Laminar."),
    input(
      tpe    := "number",
      value <-- inputVar.signal.map(_.toString),
      onChange.mapToValue.map(_.toInt) --> inputVar.writer
    ),
    button(
      "Start Game",
      onClick.mapToUnit --> startGameBus.writer,
      disabled <-- gameStartedSignal
    ),
    div(
      idAttr    := canvasId,
      maxHeight := "500px"
    ),
    startGameBus.events.sample(inputVar.signal) --> Observer[Int] { inputData =>
      val theGame = new TheGame(inputData)

      theGame.launch(
        canvasId,
        "width"  -> "200",
        "height" -> "200"
      )
    }
  )

  render(dom.document.getElementById("root"), elem)

}
