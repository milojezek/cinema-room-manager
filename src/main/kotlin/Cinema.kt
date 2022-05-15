package cinema

object Cinema {
    var roomManagerIsOn = true
    private var numberOfRows = 0
    private var seatsPerRow = 0
    private var totalSeats = 0
    private var frontRows = 0
    private var room = mutableListOf<CharArray>()

    private var purchasedTickets = 0
    private var currentIncome = 0

    init {
        println("Enter the number of rows:")
        numberOfRows = readln().toInt() // Can't be higher than 9
        println("Enter the number of seats in each row:")
        seatsPerRow = readln().toInt()  // Can't be higher than 9

        createRoom(numberOfRows, seatsPerRow)
    }

    private fun createRoom(rows: Int, seatsInRow: Int) {
        for (row in 0 until rows) {
            room.add(CharArray(seatsInRow))
            room[row].fill('S')
        }

        totalSeats += rows * seatsInRow
        frontRows += numberOfRows / 2
    }

    fun selectAction() {
        println("\n1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit")

        when (readln().toInt()) {
            1 -> printRoom()
            2 -> bookSeat()
            3 -> showStatistics()
            0 -> roomManagerIsOn = false
        }
    }

    private fun printRoom() {
        println("\nCinema:")
        print("  ")
        for (seat in 1..seatsPerRow) {
            if (seat == seatsPerRow) {
                print("$seat\n")
            } else {
                print("$seat ")
            }
        }

        for (line in room) {
            println("${room.indexOf(line) + 1} " + line.joinToString(" "))
        }
    }

    private fun bookSeat() {
        println("\nEnter a row number:")
        val row = readln().toInt() - 1
        println("Enter a seat number in that row:")
        val seat = readln().toInt() - 1


        if (row >= numberOfRows || seat >= seatsPerRow) {
            println("\nWrong input!")
            bookSeat()
        } else if (room[row][seat] != 'S') {
            println("\nThat ticket has already been purchased!")
            bookSeat()
        } else {
            val ticketPrice = calculateTicketPrice(row + 1)
            println("\nTicket price: $$ticketPrice")
            room[row][seat] = 'B'
            purchasedTickets++
        }
    }

    private fun calculateTicketPrice(rowNumber: Int): Int {
        return if (totalSeats <= 60) {
            currentIncome += 10
            10
        } else {
            if (rowNumber <= frontRows) {
                currentIncome += 10
                10
            } else {
                currentIncome += 8
                8
            }
        }
    }

    private fun showStatistics() {
        val percentage: Double = purchasedTickets.toDouble() / (totalSeats.toDouble() / 100)
        val totalIncome = calculateTotalIncome()
        println("\nNumber of purchased tickets: $purchasedTickets\n" +
                "Percentage: %.2f".format(percentage) + "%\n" +
                "Current income: $$currentIncome\n" +
                "Total income: $$totalIncome")
    }

    private fun calculateTotalIncome(): Int {
        var totalIncome = 0
        for (index in 0 until room.size) {
            totalIncome += if (totalSeats > 60) {
                if (index <= frontRows - 1) {
                    room[index].size * 10
                } else {
                    room[index].size * 8
                }
            } else {
                room[index].size * 10
            }
        }
        return totalIncome
    }
}