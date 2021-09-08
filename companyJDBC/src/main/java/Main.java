import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        CompanyDb companyDb = new CompanyDb();
        printMainMenu();
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("0")) break;
            companyDb.queryExecuter(Integer.parseInt(userInput));
            printMainMenu();
        }
    }

    private static void printMainMenu() {
        System.out.println(
                "Welcome to Software Company database manager ! \n \n" +
                        "  - Main menu - \n" +
                        "1.List all company projects \n" +
                        "2.List all employees info \n" +
                        "3.List all departments \n" +
                        "4.Projects that we have started to develop since 'spec date' \n" +
                        "5.'n' people of the company's oldest employees \n" +
                        "6.The number of documents that have modified since the start of the 'spec project' \n" +
                        "7.Total energy consumption of 'online/offline' servers \n" +
                        "8.List of tasks from the 'spec project' project that have not yet been completed \n" +
                        "9.List of 'spec brand' Core i5 computers in the company owned by an Android developer: \n" +
                        "10.List of 'n' people project managers who have assigned the most tasks this year \n" +
                        "11.Create new table \n" +
                        "12.Rename a column/table \n" +
                        "13.Drop a table \n" +
                        "14.Add new column to existing table \n" +
                        "15.Run your own SQLite query on company database   \n" +
                        "17.List all tables in company database \n" +
                        "0.Exit"
        );
    }
}