package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(
        Fork(
            Leaf('a',2),
            Leaf('b',3),
            List('a','b'),
            5
        ),
        Leaf('d',4), List('a','b','d'), 9
      )
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(
      Fork(
        Leaf('e',1),
        Leaf('t',2),
        List('e', 't'),
        3
      ),
      Leaf('x',4))
    )
  }

  test("makeOrderedLeafList of times") {
    val chars = "asdasazzzzzzzza".toList
    val list = List(Leaf('d', 1), Leaf('s', 2), Leaf('a', 4), Leaf('z', 8))
    assert(makeOrderedLeafList(times(chars)) === list)
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("decode and encode secret") {
    new TestTrees {
      assert(encode(Huffman.frenchCode)("huffmanestcool".toList) === Huffman.secret)
    }
  }

  test("singleton") {
    new TestTrees {
      assert(singleton(List(t1)) === true)
      assert(singleton(List(t1, t2)) === false)
      assert(singleton(Nil) === false)
    }
  }

  test("codebits") {
    val table = List(
      ('a', List(0, 0)),
      ('b', List(0, 1)),
      ('d', List(1))
    )
    assert(codeBits(table)('b') === List(0, 1))
    assert(codeBits(table)('d') === List(1))
    assert(codeBits(table)('a') === List(0, 0))
  }

  test("convert") {
    new TestTrees {
      val tbl1 = convert(t1)
      val tbl2 = convert(t2)
      assert(codeBits(tbl1)('a') === List(0))
      assert(codeBits(tbl1)('b') === List(1))
      assert(codeBits(tbl1)('c') === Nil)
      assert(codeBits(tbl2)('a') === List(0, 0))
      assert(codeBits(tbl2)('d') === List(1))
      assert(codeBits(tbl2)('b') === List(0, 1))
    }
  }
  
  test("quick encode") {
    new TestTrees {
      assert(quickEncode(Huffman.frenchCode)("huffmanestcool".toList) === Huffman.secret)
    }
  }

  test("times") {
    assert(times(List('a', 'b', 'a')) === List(('a', 2), ('b', 1)))
    assert(times("abrkdabrkabraba".toList) === List(('a',5), ('b',4), ('r',3), ('k',2), ('d',1)))
  }

  test("optimal encode") {
    new TestTrees {
      val tList1 = "aaaaaaaabbbcdefgh".toList
      assert(encode(createCodeTree(tList1))(tList1).length === 41)
      val tList2 = "beep boop beer!".toList
      assert(encode(createCodeTree(tList2))(tList2).length === 40)
    }
  }
}
