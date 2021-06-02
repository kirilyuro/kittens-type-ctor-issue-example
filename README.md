# kittens-type-ctor-issue-example

This project provides an example of the type constructor field issue mentioned in the [typelevel/kittens documentation](https://github.com/typelevel/kittens#three-modes-of-derivation) of the manual semi derivation mode.

The example code is in [src/main/scala/example/MyApp.scala](https://github.com/kirilyuro/kittens-type-ctor-issue-example/blob/main/src/main/scala/example/MyApp.scala).

The code contains 4 examples:
1. Correct derivation behaviour when using Kittens' semi auto derivation mode.
2. Incorrect behaviour when using manual semi derivation mode (due to the type constructor field issue).
3. Incorrect behaviour when deriving using [Catnip](https://github.com/scalalandio/catnip) annotations (which rely on Kittens' manunal semi derivation internally).
4. A workaround for getting the correct behaviour when using Catnip annotations (or equivalently - Kittens' manual semi derivation directly).

The example consists of 3 models:
1. A simple data type:
```scala
case class Bar(a: String)
```

2. A type constructor:
```scala
case class MyContainer[A](item: A)
```
with a custom implementation of the `Show` type class:
```scala
implicit def showMyContainer[A: Show]: Show[MyContainer[A]] =
  container => s"Wrapped ${container.item.show}"
```

3. A type with a type constructor field:
```scala
case class Foo(bar: MyContainer[Bar])
```

The example prints the result of:
```scala
Foo(MyContainer(Bar("baz"))).show
```

The expected output is `Foo(bar = Wrapped Bar(a = baz))`, i.e. using a derived `Show[Foo]`, the custom `Show[MyContainer[A]]` (for `A = Bar`) and a derived `Show[Bar]`.
