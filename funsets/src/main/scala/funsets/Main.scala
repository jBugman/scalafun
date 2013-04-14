package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  printSet(singletonSet(1))
  val s1 = (x: Int) => x > 0 && x < 5
  printSet(s1)
  printSet(map(s1, (x: Int) => x + 2))
  printSet(map(s1, (x: Int) => 3))
}
