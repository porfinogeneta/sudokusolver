import pprint._
import sudoku._


// to run use -> sbt "run 004300209005009001070060043006002087190007400050083000600000105003508690042910300"

@main def solver(args: String*): Unit = {
  if (args.isEmpty) {
    println("Usage: scala <program> <sudoku-string>")
    println("Example: scala <program> 004300209005009001070060043006002087190007400050083000600000105003508690042910300")
  } else {
    val sudokuStr = args.head
    val sudoku = Sudoku.form(sudokuStr)

    sudoku match {
      case Right(parsedSudoku) =>
        parsedSudoku.prettyPrint
        pprintln(parsedSudoku.getCol(4))
        pprintln(parsedSudoku.getRow(5))
        pprintln(parsedSudoku.getCell(4)(5))
        val solved = parsedSudoku.solve
        solved match {
          case Some(puzzle) => puzzle.prettyPrint
          case None => println("No solution found :(")
        }
      case Left(error) =>
        println(s"Failed to parse Sudoku: $error")
    }
  }
}
  
