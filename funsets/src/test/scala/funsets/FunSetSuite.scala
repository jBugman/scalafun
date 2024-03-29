package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
      assert(!contains(s1, 2), "Singleton not contains")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  trait TestPairSets {
    val s1 = pairSet(1, 2)
    val s2 = pairSet(2, 3)
    val s3 = pairSet(1, 3)
  }

  test("pair union") {
    new TestPairSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 4")
      assert(contains(s, 2), "Union 5")
      assert(contains(s, 3), "Union 6")
      assert(!contains(s, 4), "Union 7")
      assert(!contains(s, 0), "Union 7")
    }
  }

  test("intersect tests") {
    new TestPairSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1), "intersect 1")
      assert(contains(s, 2), "intersect 2")
      assert(!contains(s, 3), "intersect 3")
      assert(!contains(s, 4), "intersect 4")
      assert(!contains(s, 0), "intersect 5")
    }
  }

  test("diff tests") {
    new TestPairSets {
      val s = diff(s1, s2)
      assert(contains(s, 1), "diff 1")
      assert(!contains(s, 2), "diff 2")
      assert(!contains(s, 3), "diff 3")
      assert(!contains(s, 4), "diff 4")
      assert(!contains(s, 0), "diff 5")
      val s4 = diff(s1, s3)
      assert(!contains(s4, 1), "diff 6")
      assert(contains(s4, 2), "diff 7")
      assert(!contains(s4, 3), "diff 8")
      assert(!contains(s4, 4), "diff 9")
      assert(!contains(s4, 0), "diff 10")
    }
  }

  test("filter tests") {
    new TestPairSets {
      val s = filter(s1, (x: Int) => x == 2)
      assert(!contains(s, 1), "filter 1")
      assert(contains(s, 2), "filter 2")
      assert(!contains(s, 3), "filter 3")
      assert(!contains(s, 4), "filter 4")
      assert(!contains(s, 0), "filter 5")
      val s4 = filter(union(s1, s2), (x: Int) => x != 2 || x == 4)
      assert(contains(s4, 1), "filter 6")
      assert(!contains(s4, 2), "filter 7")
      assert(contains(s4, 3), "filter 8")
      assert(!contains(s4, 4), "filter 9")
      assert(!contains(s4, 0), "filter 10")
    }
  }

  test("forall tests") {
    new TestPairSets {
      val p1 = (x: Int) => x > 0
      val p2 = (x: Int) => x == 1
      val s4 = (x: Int) => x == 1
      val s5 = singletonSet(512)
      assert(forall(s1, p1), "forall 1")
      assert(!forall(s1, p2), "forall 2")
      assert(forall(s2, p1), "forall 3")
      assert(!forall(s2, p2), "forall 4")
      assert(forall(s4, p1), "forall 5")
      assert(forall(s4, p2), "forall 6")
      assert(forall(s5, p1), "forall 7")
      assert(!forall(s5, p2), "forall 8")
    }
  }

  test("exists tests") {
    new TestPairSets {
      val p1 = (x: Int) => x > 0
      val p2 = (x: Int) => x == 1
      val s4 = (x: Int) => x == 1
      val s5 = singletonSet(512)
      assert(exists(s1, p1), "exists 1")
      assert(exists(s1, p2), "exists 2")
      assert(exists(s2, p1), "exists 3")
      assert(!exists(s2, p2), "exists 4")
      assert(exists(s4, p1), "exists 5")
      assert(exists(s4, p2), "exists 6")
      assert(exists(s5, p1), "exists 7")
      assert(!exists(s5, p2), "exists 8")
    }
  }

  test("map tests") {
    new TestPairSets {
      val s = map(s1, (x: Int) => x + 2)
      assert(!contains(s, 1), "map 1")
      assert(!contains(s, 2), "map 2")
      assert(contains(s, 3), "map 3")
      assert(contains(s, 4), "map 4")
      assert(!contains(s, 0), "map 5")
    }
  }
}
