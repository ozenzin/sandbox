package oz

import scala.quoted.*

class Utils


transparent inline def oddEvenMacroTransparent(inline number: Int): "even" | "odd" = {
  inline number % 2 match {
    case 0 =>
      "even"
    case _ =>
      "odd"
  }
}

def getQuoted()(using Quotes): Expr[Int] = '{1}
inline def getSliced(): Int = ${ getQuoted() }

def oddEvenQuotes(n: Expr[Int])(using Quotes): Expr[String] = '{
  $n % 2 match {
    case 0 => "even"
    case _ => "odd"
  }
}

def back(s: Expr[String])(using Quotes): Expr[Int] = '{
  $s match {
    case "even" => 22
    case "odd" => 111
    case _ => -1
  }
}

inline def oddEven(n: Int): String = ${oddEvenQuotes('{n})}

inline def oddEvenBack(s: String): Int = ${back('{s})}

def unrolledPowerCode(x: Expr[Double], n: Int)(using Quotes): Expr[Double] =
  if n == 0 then '{ 1.0 }
  else if n == 1 then x
  else '{ $x * ${ unrolledPowerCode(x, n-1) } }

def unrolledPower(x: Expr[Int], n: Int)(using Quotes): Expr[Int] =
  if n == 0 then '{ 1 }
  else if n == 1 then x
  else '{ $x * ${ unrolledPower(x, n-1) } }

inline def power2(x: Double): Double = ${ unrolledPowerCode('x, 2) }
inline def power3(x: Double): Double = ${ unrolledPowerCode('x, 3) }
inline def power4(x: Double): Double = ${ unrolledPowerCode('x, 4) }

def foreach[T](arr: Expr[Array[T]], f: Expr[T] => Expr[Unit])
              (using Type[T], Quotes): Expr[Unit] = '{
  var i: Int = 0
  while i < ($arr).length do
    val element: T = ($arr)(i)
    ${f('element)}
    i += 1
}

def sum(arr: Expr[Array[Int]])(using Quotes): Expr[Int] = '{
  var sum = 0
  ${ foreach(arr, x => '{ sum += $x }) }
  sum
}

def product(arr: Expr[Array[Int]])(using Quotes): Expr[Int] = '{
  var prd = 1
  ${ foreach(arr, x => '{ prd *= $x }) }
  prd
}
