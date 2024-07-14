package sudoku
// import cats.syntax.all.*

final case class Sudoku private (sudoku: Vector[Int]) {
    def prettyPrint: Unit =
        sudoku.grouped(3).zipWithIndex.foreach({ case (group, index) =>
            print(group.mkString(" ") 
            + (if ((index % 3) == 2) "\n" else " | ")
            + (if ((index % 9) == 8 && index > 1) "---------------------\n" else "")
            )    
        })
    // rzędy po y, kolumny po x
    
    def get(x: Int)(y: Int) =
        sudoku(9*y + x)
    
    def getRow(y: Int) =
        sudoku.slice(9*y, (9*(y + 1)))

    def getCol(x: Int): Vector[Int] =
        (0 to 8).map(get(x)).toVector

    def getCell(x: Int)(y: Int): Vector[Int] =
        // funkcja, która dla danego x albo y zwróci
        // początkowy indeks wiersza (x) albo kolumny (y)
        // dla danej komórki
        // dla x=4 i y=7 => <3,4,5> i <6,7,8>
        def getIndices(n : Int): Vector[Int] = 
            val fistIdx = 3 * (n / 3)
            return Vector(fistIdx, fistIdx + 1, fistIdx + 2)
        
        // uzywamy for comprehension (mamy dwie pętle for w sobie)
        // potrzebujemy dostać element np (3,6), (3,7), (3,8), (4,6), itd.
        for {
            list1 <- getIndices(x)
            list2 <- getIndices(y)
        } yield get(list1)(list2)
        

    // funkcja do sprawdzania czy dana liczba została juz dodana
    def isFirst(x: Int, y: Int)(value: Int): Boolean =
        !(getCell(x)(y).contains(value) || getRow(y).contains(value) || getCol(x).contains(value))

    // funkcja do znajdowania pierwszego punktu z dostępnym 0, zwracamy None jak nie ma takiego pola
    def findZero: Option[(Int, Int)] =
        val idx = sudoku.indexWhere(_ == 0)
        if (idx == -1){
            None
        }else {
            Some(idx % 9, idx / 9)
        }

    def set(x: Int, y: Int)(value: Int): Sudoku = 
        Sudoku(sudoku.updated(y*9 + x, value)) // zwraca nowy wektor z zaktualizowaną wartością

    def solve: Option[Sudoku] =
        findZero match {
            case None => Some(this)
            case Some(x, y) => 
                for (num <- 1 to 9) {
                    if (isFirst(x, y)(num)){
                        val updatedSudoku = set(x, y)(num)
                        val solution = updatedSudoku.solve
                        // mamy sensowane rozwiązanie
                        if (solution.isDefined){
                            return solution
                        }
                        set(x, y)(0) // cofamy się, jak się nie udało znaleźć rozwiązania
                    }
                }
                None
        }
    
}




// teraz nie zrobimy Sudoku, niespełniającego wymagań inputu
object Sudoku {
    def form(s: String): Either[String, Sudoku] =
        val replaced = s.replace('.', '0') // replace . with 0

        if (replaced.length != 81){
            Left("Valid Sudoku is 81 characters!")
        } 
        else if (!replaced.forall(_.isDigit)) {
            Left("All Values Should be digits!")
        }else {
            Right(Sudoku.apply(replaced.map(_.asDigit).toVector))
        }
            
}
