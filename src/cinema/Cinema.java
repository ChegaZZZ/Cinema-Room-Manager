package cinema;

import java.util.Scanner;

public class Cinema {

    private static final int PERCENTAGE_OF_TOTAL_SEATS = 100;
    private static final int TOTAL_NUMBER_OF_SEATS = 60;
    private static final int PRICE_OF_EACH_TICKET = 10;
    private static final int PRICE_FOR_THE_FRONT_HALF = 10;
    private static final int PRICE_FOR_THE_BACK_HALF = 8;

    private static int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows:\n> ");
        int rows = scanner.nextInt();
        System.out.print("Enter the number of seats in each row:\n> ");
        int seats = scanner.nextInt();

        String[][] cinemaHall = new String[rows + 1][seats + 1];
        createTheCinemaHall(cinemaHall);

        boolean isTrue = false;
        while (!isTrue) {
            int option = selectTheOption(scanner);
            switch (option) {
                case 1 -> showTheSeats(cinemaHall);
                case 2 -> buyTicket(scanner, cinemaHall, rows, seats);
                case 3 -> showStatistics(cinemaHall, rows, seats);
                case 0 -> isTrue = true;
                default -> {
                }
            }
        }
        scanner.close();
    }

    private static void createTheCinemaHall(String[][] cinemaHall) {
        cinemaHall[0][0] = " ";

        for (int i = 0; i < cinemaHall.length - 1; i++) {
            int row = i + 1;

            for (int j = 0; j < cinemaHall[i].length - 1; j++) {
                int seat = j + 1;

                if (cinemaHall[i + 1][j + 1] == null) {
                    cinemaHall[i + 1][j + 1] = "S";
                }

                if (cinemaHall[i][j + 1] == null) {
                    cinemaHall[i][j + 1] = String.valueOf(seat);
                }

                if (cinemaHall[i + 1][j] == null) {
                    cinemaHall[i + 1][j] = String.valueOf(row);
                }
            }
        }
    }

    private static int selectTheOption(Scanner scanner) {
        System.out.println("\n1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.print("0. Exit\n> ");
        return scanner.nextInt();
    }

    private static void showTheSeats(String[][] cinemaHall) {
        System.out.println("\nCinema:");

        for (String[] string : cinemaHall) {
            for (String value : string) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private static void buyTicket(Scanner scanner, String[][] cinemaHall, int rows, int seats) {
        while (true) {
            System.out.print("\nEnter a row number:\n> ");
            int rowNum = scanner.nextInt();
            System.out.print("Enter a seat number in that row:\n> ");
            int seatNum = scanner.nextInt();

            if (rowNum > rows || seatNum > seats) {
                System.out.println("\nWrong input!");
                continue;
            }

            if (checkSeat(cinemaHall, rowNum, seatNum)) {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                System.out.println("\nTicket price: $" + calculateTicketPrice(rows, seats, rowNum));
                reserveSeat(cinemaHall, rowNum, seatNum);
                currentIncome += calculateTicketPrice(rows, seats, rowNum);
                break;
            }
        }
    }

    private static void showStatistics(String[][] cinemaHall, int rows, int seats) {
        System.out.println("\nNumber of purchased tickets: " + showPurchasedTickets(cinemaHall));

        String string = String.format("Percentage: %.2f", showThePercentageOfTicketsSold(cinemaHall, rows, seats));
        System.out.println(string.replace(",", ".") + "%");

        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + showTotalIncome(rows, seats));
    }

    private static int calculateTicketPrice(int rows, int seats, int rowNum) {
        int price;
        int firstPartHalL = rows / 2;

        if (rows * seats <= TOTAL_NUMBER_OF_SEATS) {
            price = PRICE_OF_EACH_TICKET;
        } else if (rows * seats > TOTAL_NUMBER_OF_SEATS && rowNum <= firstPartHalL) {
            price = PRICE_FOR_THE_FRONT_HALF;
        } else {
            price = PRICE_FOR_THE_BACK_HALF;
        }
        return price;
    }

    private static void reserveSeat(String[][] cinemaHall, int rowNum, int seatNum) {
        for (int i = 0; i <= cinemaHall.length - 1; i++) {
            for (int j = 0; j <= cinemaHall[i].length - 1; j++) {
                if (i == rowNum && j == seatNum) {
                    cinemaHall[i][j] = "B";
                }
            }
        }
    }

    private static int showPurchasedTickets(String[][] cinemaHall) {
        int numOfPurchasedTickets = 0;

        for (String[] array : cinemaHall) {
            for (String value : array) {
                if ("B".equals(value)) {
                    numOfPurchasedTickets++;
                }
            }
        }
        return numOfPurchasedTickets;
    }

    private static double showThePercentageOfTicketsSold(String[][] cinemaHall, int rows, int seats) {
        int result = showPurchasedTickets(cinemaHall);
        return 1.0 * result / (rows * seats) * PERCENTAGE_OF_TOTAL_SEATS;
    }

    private static int showTotalIncome(int rows, int seats) {
        int firstPart = rows / 2;
        int secondPart = rows - firstPart;
        int sum = 0;

        if (rows * seats < TOTAL_NUMBER_OF_SEATS) {
            sum = rows * seats * PRICE_OF_EACH_TICKET;
        } else {
            for (int i = 0; i < firstPart; i++) {
                for (int j = 0; j < seats; j++) {
                    sum += PRICE_FOR_THE_FRONT_HALF;
                }
            }

            for (int i = 0; i < secondPart; i++) {
                for (int j = 0; j < seats; j++) {
                    sum += PRICE_FOR_THE_BACK_HALF;
                }
            }
        }
        return sum;
    }

    private static boolean checkSeat(String[][] cinemaHall, int rowNum, int seatNum) {
        return "B".equals(cinemaHall[rowNum][seatNum]);
    }
}