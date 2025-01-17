package oz.practice.fromjava

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.StdIn

/**
 * Implement simple cache with max capacity and eviction policy as follows:
 * LFU (least frequently used) with ties resolved by LRU (least recently accessed)
 */
@main def main(cap: Int): Unit =
  extension (cache:  LfuCache[Int, String])
    def offer(kv: Array[String]): Unit =
      kv match {
        case Array(k, v) => cache.put(k.toInt, v)
        case _ => println("invalid input")
      }

  val cache = new LfuCache[Int, String](cap)

  while
    StdIn.readLine() match {
      case "q" | "quit"  | "exit" | null => false
      case "p" | "print" | "all"  | "a"  => println(cache.showAll); true
      case "f" | "freq"                  => println(cache.showFreqs); true
      case "c" | "cache"                 => println(cache.show); true
      case key 
        if (key.toIntOption.isDefined)   => println(cache.get(key.trim.toInt).getOrElse("No such entry")); true
      case line                          => cache.offer(line.trim.split("\\s+")); true
    }
  do println(usage)

private val usage =
  """
    |Usage:
    |"p" | "print" | "all"  | "a"  - print all elements in cache with their access frequencies
    |"f" | "freq"                  - print all frequencies
    |"c" | "cache"                 - print cache
    |<number>                      - get element by key
    |<number> AnyString            - offer element to cache
    |"q" | "quit"  | "exit"        - quit
    |""".stripMargin
sealed class LfuCache[K, V](val cap: Int):
  //main map, contains cached elements in order of insertion
  protected val lruMap: mutable.Map[K, V] = mutable.LinkedHashMap.empty[K, V]
  //aux map to keep track of frequencies for each key
  private val lfuMap = mutable.HashMap.empty[K, Int]

  def put(k: K, v: V):  Option[V] =
    if lruMap.size >= cap then//time to vacate some elements from cache
      val minFreq = lfuMap.values.minOption.getOrElse(0)
      lfuMap.filter(_._2 == minFreq).keys match {
        case Nil =>//cache hadn't been used at all
          lruMap.remove(lruMap.keys.last)
        case key :: Nil =>//no ties, remove single element
          lruMap.remove(key)
          lfuMap.remove(key)
        case lfuKeys =>//tie on LFU, lets find LRU element
          @tailrec
          def findLru(lruKeys: Seq[K]): K =
            val (head::tail) = lruKeys
            if lfuKeys.exists(_ == head) then head else findLru(tail)

          val key = findLru(lruMap.keys.toSeq)
          lruMap.remove(key)
          lfuMap.remove(key)
      }
    end if
    lruMap.put(k, v)

  def get(k: K): Option[V] =
    lruMap.get(k).map { v =>
      lfuMap.put(k, lfuMap.getOrElse(k, 0) + 1)
      v
    }

  def show: String = lruMap.toString
  def showFreqs: String = lfuMap.toString
  def showAll: String = lruMap.map((k, v) => s"$k(${lfuMap.getOrElse(k, 0)})\t-> $v").mkString("\n", "\n", "\n")
