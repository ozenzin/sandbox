package oz.practice.fromjava

import scala.annotation.tailrec

@main def gfg(a: Int, b: Int): Unit =
  @tailrec
  def gcd(x: Int, y: Int): Int =
    if y == 0 then x else gcd(y, x % y)

  println(s"Greatest Common Divisor for $a and $b is ${gcd(a, b)}")