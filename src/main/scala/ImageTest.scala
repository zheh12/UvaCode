import java.io._
import java.nio.ByteBuffer
import java.util.Scanner
import javafx.geometry.Point2D
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.{Image, PixelWriter, WritableImage}
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

/**
 * Created by yang on 2015/8/20.
 */
class ImageTest extends javafx.application.Application{
  val dir = "C:\\Users\\yang\\Documents\\Documents\\"

  val imageView: Canvas = new Canvas(640,480)
  val markerView: Canvas = new Canvas(640,480)

  override def start(primaryStage: Stage)= {
    primaryStage.setTitle("Image Test")

    val rootPane = new StackPane()
    val marker = readPoints(dir + "marker.txt")

    rootPane.getChildren.addAll(imageView,markerView);
    drawmark(marker)
    markerView.toFront()

    new Thread(new Runnable {
      override def run(): Unit = scannerImage()
    }).start();



    val scene: Scene = new Scene(rootPane,640,480)
    primaryStage.setScene(scene)
    primaryStage.show
  }

  def drawmark(marker: Array[Point2D]): Unit = {
    val gc = markerView.getGraphicsContext2D
    gc.setStroke(Color.BLUE)
    gc.setLineWidth(3)

    gc.beginPath()
    gc.moveTo(marker(0).getX,marker(0).getY)
    gc.lineTo(marker(1).getX,marker(1).getY)
    gc.lineTo(marker(2).getX,marker(2).getY)
    gc.lineTo(marker(3).getX,marker(3).getY)
    gc.closePath()
    gc.stroke()
    printPoints(marker)


  }

  def scannerImage(): Unit = {
    val gc = imageView.getGraphicsContext2D

    val filenameFileter = new FilenameFilter {
      override def accept(dir: File, name: String): Boolean = name.contains("image_")
    }

    //val images = new File(dir).list(filenameFileter).foldLeft(List[Image]())((images,path) => {
    //  readImage(dir + path) :: images
    //})
    new File(dir).list(filenameFileter).foreach{ path =>
      val image = readImage(dir + path)
      //val image = new Image("file:" + dir + "image.png")
      gc.drawImage(image,0,0)
    }

  }


  def readImage(path: String): Image = {
    val input: FileInputStream = new FileInputStream(path)
    val width: Int = readInt(input)
    val height: Int = readInt(input)

    val writableImage: WritableImage = new WritableImage(width, height)
    val writer: PixelWriter = writableImage.getPixelWriter

    val bytes = readFrameData(input,width,height)
    (0 until width).foreach(col => (0 until height).foreach{row =>
      val gray = unsignedByteToInt(bytes(row*width + col))
      // println(gray)
      writer.setColor(col,row,Color.grayRgb(gray))
    })
    return writableImage
  }

  def readDouble(input: InputStream): Double = {
    val bytes: Array[Byte] = Array.ofDim(8);
    input.read(bytes)
    ByteBuffer.wrap(bytes.reverse).getDouble
  }

  def readInt(input: InputStream): Int = {
    val bytes: Array[Byte] = Array.ofDim(4);
    input.read(bytes)
    ByteBuffer.wrap(bytes.reverse).getInt
  }

  def readByte(input: InputStream): Byte = {
    val bytes: Array[Byte] = Array.ofDim(1)
    input.read(bytes)
    return bytes(0)
  }

  def readFrameData(input: InputStream,width: Int,height: Int): Array[Byte] = {
    val bytes: Array[Byte] = Array.ofDim(width * height)
    input.read(bytes);
    return bytes;
  }

  def unsignedByteToInt(byte: Byte): Int = {
    return byte & 0xFF;
  }

  def readPoints(path: String): Array[Point2D] = {
    val scanner: Scanner = new Scanner(new FileReader(path))
    (0 until 4).foldLeft(List[Point2D]())((list,i) => {
      val x = scanner.nextFloat();
      val y = scanner.nextFloat();

      new Point2D(x,y) :: list
    }).toArray
  }

  def printPoints(points: Array[Point2D]): Unit ={
    points.foreach(point => println(point.getX+" "+point.getY))
  }
}

object ImageTest  {

  def main(args: Array[String]) {
    javafx.application.Application.launch(classOf[ImageTest],args: _*)
  }
}
