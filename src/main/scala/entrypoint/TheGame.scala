package entrypoint

import indigo._

class TheGame(inputData: Int) extends IndigoSandbox[Int, TheGame.Model] {

  override def config: GameConfig = GameConfig.default

  val assetName = AssetName("dots")

  override def assets: Set[AssetType] = Set(
    AssetType.Image(assetName, AssetPath("/indigo-web/dots.png"))
  )

  override def fonts: Set[FontInfo] = Set.empty

  override def animations: Set[Animation] = Set.empty

  override def shaders: Set[Shader] = Set.empty

  override def setup(assetCollection: AssetCollection, dice: Dice): Outcome[Startup[Int]] =
    Outcome(Startup.Success(inputData))

  override def initialModel(startupData: Int): Outcome[TheGame.Model] =
    Outcome(TheGame.Model(s"The input data is $startupData"))

  override def updateModel(context: FrameContext[Int], model: TheGame.Model): GlobalEvent => Outcome[TheGame.Model] =
    _ => Outcome(model)

  override def present(context: FrameContext[Int], model: TheGame.Model): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        Shape.Box(
          Rectangle(Point(100, 50), Size(50, 50)),
          Fill.Color(RGBA.Black),
          Stroke(4, RGBA.Blue)
        ),
        TextBox(model.message, 200, 30)
          .withFontFamily(FontFamily.cursive)
          .withColor(RGBA.White)
          .withFontSize(Pixels(16))
          .withStroke(TextStroke(RGBA.Red, Pixels(1)))
          .withPosition(Point(10, 100)),
        Graphic(Rectangle(0, 0, 32, 32), 1, Material.Bitmap(assetName))
      )
    )
}

object TheGame {

  case class Model(message: String)

}
