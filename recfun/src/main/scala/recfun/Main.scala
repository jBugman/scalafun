package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Pascal's triangle
   */
  def pascal(c: Int, r: Int): Int = {
    if (r < 0 || c < 0 || c > r) throw new IllegalArgumentException
    if (c == 0 || c == r) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Parentheses balance
   */
  def balance(chars: List[Char]): Boolean = {
    def balanced(chars: List[Char], balance: Int): Boolean = {
      if (chars.isEmpty)
        balance == 0
      else
        if (chars.head == '(')
          balanced(chars.tail, balance + 1)
        else if (chars.head == ')')
          if (balance > 0)
            balanced(chars.tail, balance - 1)
          else
            false
        else
          balanced(chars.tail, balance)
    }
    balanced(chars, 0)
  }

  /**
   * Change variants count
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
