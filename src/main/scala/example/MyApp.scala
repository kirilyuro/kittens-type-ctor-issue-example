package example

import cats.Show
import cats.implicits._
import io.scalaland.catnip.Semi

object MyApp extends App {
  case class MyContainer[A](item: A)
  implicit def showMyContainer[A: Show]: Show[MyContainer[A]] =
    container => s"Wrapped ${container.item.show}"

  def semiAuto: String = {
    case class Bar(a: String)
    case class Foo(bar: MyContainer[Bar])

    implicit val semiAutoShowFoo: Show[Foo] = {
      import cats.derived.auto.show._
      cats.derived.semi.show
    }

    Foo(MyContainer(Bar("baz"))).show
  }

  def manualSemi: String = {
    case class Bar(a: String)
    case class Foo(bar: MyContainer[Bar])

    implicit val manualSemiShowFoo: Show[Foo] = cats.derived.semi.show

    Foo(MyContainer(Bar("baz"))).show
  }

  def manualSemiCatnipAnnotation: String = {
    case class Bar(a: String)
    @Semi(Show) case class Foo(bar: MyContainer[Bar])

    Foo(MyContainer(Bar("baz"))).show
  }

  def fixedManualSemiCatnipAnnotation: String = {
    @Semi(Show) case class Bar(a: String)
    @Semi(Show) case class Foo(bar: MyContainer[Bar])

    Foo(MyContainer(Bar("baz"))).show
  }

  println(semiAuto)
  println(manualSemi)
  println(manualSemiCatnipAnnotation)
  println(fixedManualSemiCatnipAnnotation)

  /**
   * Output:
   *
   *    Foo(bar = Wrapped Bar(a = baz))
   *    Foo(bar = MyContainer(item = Bar(a = baz)))
   *    Foo(bar = MyContainer(item = Bar(a = baz)))
   *    Foo(bar = Wrapped Bar(a = baz))
   */
}
