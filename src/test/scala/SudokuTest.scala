import cats.syntax.all._
import sudoku._

class SudokuTests extends munit.FunSuite {

  val sudoku = Sudoku.form(
    "435269781" +
    "682571493" +
    "197834562" +
    "826195347" +
    "374682915" +
    "951743628" +
    "519326874" +
    "248957136" +
    "763418259"
  ).getOrElse(throw new IllegalArgumentException("Invalid Sudoku"))

    /* Tests here! */
    test("Sudoku.get(x,y) should extract the number at (x,y) (0 based, from top left)") {
      assertEquals(sudoku.get(0)(0), 4)
      assertEquals(sudoku.get(1)(0), 3)
      assertEquals(sudoku.get(2)(7), 8)
    }

    test("Sudoku.getRow(n) should extract nth row from top") { 
      assertEquals(sudoku.getRow(0), Vector(4, 3, 5, 2, 6, 9, 7, 8, 1))
      assertEquals(sudoku.getRow(6), Vector(5, 1, 9, 3, 2, 6, 8, 7, 4))
    }

    test("Sudoku.getColumn(n) should extract nth column from left") {
      assertEquals(sudoku.getCol(0), Vector(4, 6, 1, 8, 3, 9, 5, 2, 7))
      assertEquals(sudoku.getCol(6), Vector(7, 4, 5, 3, 9, 6, 8, 1, 2))
    }

    test("Sudoku.getCellOf(n) should extract the correct cell") { 
      assert(sudoku.getCell(1)(1).forall((1 to 9).contains))
      assert(sudoku.getCell(7)(3).forall((1 to 9).contains))
    }



}