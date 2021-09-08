import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CompanyDb {
    Connection connection = null;
    Statement statement = null;
    String query;
    ResultSet rs;

    void queryExecuter(int operation) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:F:\\sqlite\\company.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(100);
            switch (operation) {
                case 1: {
                    query = "SELECT * FROM Project";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        String ID = String.valueOf(rs.getInt("id"));
                        String name = rs.getString("Name");
                        String start = rs.getString("StartDate");
                        String release = rs.getString("ReleaseDate");
                        String isPublic = rs.getInt("IsPublic") == 1 ? "true" : "false";
                        String pmId = String.valueOf(rs.getInt("PmId"));
                        String lang = String.valueOf(rs.getInt("LanguageId"));
                        String platform = String.valueOf(rs.getInt("PlatformId"));
                        String log = "id ="
                                .concat(ID)
                                .concat("\n")
                                .concat("name =")
                                .concat(name)
                                .concat("\n")
                                .concat("start date =")
                                .concat(start)
                                .concat("\n")
                                .concat("release date  = ")
                                .concat(release)
                                .concat("\n")
                                .concat("is Public =")
                                .concat(isPublic)
                                .concat("\n")
                                .concat("project manager id =")
                                .concat(pmId)
                                .concat("\n")
                                .concat("language id=")
                                .concat(lang)
                                .concat("\n")
                                .concat("platform id")
                                .concat(platform)
                                .concat("\n");
                        System.out.println(log);
                    }
                }
                break;
                case 2: {
                    query = "SELECT * FROM Employee";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        String ID = String.valueOf(rs.getInt("PersonalID"));
                        String name = rs.getString("Name");
                        String skill = rs.getString("Skill");
                        String age = String.valueOf(rs.getInt("Age"));
                        String depId = String.valueOf(rs.getInt("DepartmentId"));
                        String log = String.format("id = %s \n" +
                                "name = %s \n" +
                                "skill = %s \n" +
                                "age= %s \n" +
                                "depId= %s \n", ID, name, skill, age, depId);
                        System.out.println(log);
                    }
                }
                break;
                case 3: {
                    query = "SELECT * FROM Department";
                    rs = statement.executeQuery(query);
                    while (rs.next()) {
                        String ID = String.valueOf(rs.getInt("Id"));
                        String name = rs.getString("Name");
                        String log = String.format("id = %s \n" +
                                "name = %s \n", ID, name);
                        System.out.println(log);
                    }
                }
                break;
                case 4: {
                    while (true) {
                        System.out.println("enter a date ( like 1395/01/01 ) or 0 to exit : ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("0")) break;
                        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            sdfrmt.parse(input);
                        } catch (Exception e) {
                            System.out.println("enter only a date format");
                            continue;
                        }
                        query = "SELECT Name,StartDate " +
                                "FROM Project " +
                                "WHERE StartDate > ?;";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String name = rs.getString("Name");
                            String date = rs.getString("StartDate");
                            String log = String.format("name = %s \n" +
                                    "date = %s \n", name, date);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 5: {
                    while (true) {
                        System.out.println("enter number to show or 0 to exit : ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("0")) break;
                        query = "SELECT Name,Age FROM Employee ORDER BY Age DESC LIMIT ?;";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String name = rs.getString("Name");
                            String age = String.valueOf(rs.getInt("Age"));
                            String log = String.format("name = %s \n" +
                                    "age = %s \n", name, age);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 6: {
                    while (true) {
                        System.out.println("enter project name or 0 to exit (like browser , editor, ide ,....): ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("0")) break;
                        query = "SELECT Count(*) AS count " +
                                "FROM Document " +
                                "WHERE LastModified > " +
                                "( SELECT StartDate FROM Project WHERE Name = ?)";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String count = String.valueOf(rs.getInt("count"));
                            String log = String.format("count = %s \n", count);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 7: {
                    while (true) {
                        System.out.println("Enter '1' for Online or '0' for Offline ? or 'e' to exit : ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("e")) break;
                        if (!input.equals("1") && !input.equals("0")) continue;
                        query = "SELECT SUM(Power) AS Power " +
                                "FROM Server " +
                                "WHERE IsOnline = ?;";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        rs = statement.executeQuery(query);
                        while (rs.next()) {
                            String power = String.valueOf(rs.getInt("Power"));
                            String log = String.format("Power = %s \n", power);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 8: {
                    while (true) {
                        System.out.println("enter project name or 0 to exit (like browser , editor, ide ,....): ");
                        String input1 = new Scanner(System.in).nextLine();
                        System.out.println("enter today date or 0 to exit ");
                        String input2 = new Scanner(System.in).nextLine();
                        if (input1.equals("0") || input2.equals("0")) break;
                        query = "SELECT Task.Desc AS desc " +
                                "FROM Task " +
                                "WHERE Task.ProjectId =" +
                                "(SELECT Id FROM Project WHERE Name = ?)" +
                                "AND " +
                                "Task.DeadLine > ?";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input1);
                        ps.setString(2, input2);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String desc = rs.getString("desc");
                            String log = String.format("Task description = %s \n", desc);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 9: {
                    while (true) {
                        System.out.println("enter brand or 0 to exit (like Hp , Asus , Acer , Apple ,....): ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("0")) break;
                        query = "SELECT * " +
                                "FROM Computer " +
                                "WHERE Brand = ? AND Config = 'i5' AND " +
                                "DeveloperId IN " +
                                "(SELECT PersonalId FROM Employee WHERE Skill = 'android')";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String id = String.valueOf(rs.getInt("id"));
                            String brand = rs.getString("Brand");
                            String config = rs.getString("Config");
                            String devId = String.valueOf(rs.getInt("DeveloperId"));
                            String log = String.format("id = %s \n" +
                                            "brand = %s \n" +
                                            "config = %s \n" +
                                            "developerId = %s \n"
                                    , id, brand, config, devId);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 10: {
                    while (true) {
                        System.out.println("enter brand or 0 to exit (like Hp , Asus , Acer , Apple ,....): ");
                        String input = new Scanner(System.in).nextLine();
                        if (input.equals("0")) break;
                        query = "SELECT PmId , COUNT(*) AS count " +
                                "FROM Task " +
                                "GROUP BY PmId " +
                                "ORDER BY count DESC " +
                                "LIMIT ?;";
                        PreparedStatement ps = connection.prepareStatement(query);
                        ps.setString(1, input);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String pmId = String.valueOf(rs.getInt("PmId"));
                            String count = String.valueOf(rs.getString("count"));
                            String log = String.format("projectManagerId = %s \n" +
                                    "tasksCount= %s \n", pmId, count);
                            System.out.println(log);
                        }
                    }
                }
                break;
                case 11: {
                    System.out.println("Enter table name or 'q' for quit :");
                    Scanner input = new Scanner(System.in);
                    String name;
                    boolean exist = false;
                    while (true) {
                        name = input.nextLine();
                        if (name.equals("")) {
                            System.err.println("Empty Input...");
                            continue;
                        }
                        if (name.equals("q")) {
                            break;
                        }

                        DatabaseMetaData dbm = connection.getMetaData();
                        rs = dbm.getTables(null, null, name, null);
                        if (rs.next()) {
                            exist = true;
                            System.err.println("duplicated table, try another...");
                        }

                        if (!exist) {

                            query = "CREATE TABLE IF NOT EXISTS " + name + " (Id INTEGER PRIMARY KEY AUTOINCREMENT ";
                            StringBuilder builder = new StringBuilder(query);

                            System.out.println("Please enter an attribute with its type with Space Blank OR enter 'c' for continue  :");
                            System.out.println("Example : name TEXT");

                            while (true) {

                                String line = input.nextLine();
                                if (line.equalsIgnoreCase("c")) {
                                    break;
                                }
                                if (line.trim().equals("")) {
                                    System.err.println("No attributes entered...");
                                    continue;
                                }
                                builder.append(",");
                                String[] value = line.split(" ");
                                builder.append(value[0]).append(" ").append(value[1]);
                                System.out.println("More attributes ? enter again :");
                            }
                            builder.append(")");
                            int row = statement.executeUpdate(builder.toString());
                            System.out.println("Table successfully created and " + row + " rows affected.");
                            System.out.println("Anymore tables ? enter new table name...");
                        }
                    }
                    statement.close();
                }
                break;
                case 13: {

                    System.out.println("Please enter the table name or 'q' to quit :");
                    Scanner input = new Scanner(System.in);
                    boolean toDelete;

                    while (true) {

                        String name;
                        toDelete = false;
                        name = input.nextLine();

                        if (name.equals("")) {
                            System.err.println("No Input...");
                            continue;
                        }
                        if (name.equals("q")) {
                            break;
                        }


                        query = "DROP TABLE " + name;
                        statement.executeUpdate(query);

                        System.out.println("successfully deleted the table.");
                        System.out.println("Anymore ? type your table name...");


                          /* DatabaseMetaData dbm = connection.getMetaData();
                           rs = dbm.getTables(null, null, name, null);

                           if (rs.next()) {
                               toDelete = true;
                           }


                           if (toDelete) {
                               query = "DROP TABLE " + name;
                               statement.executeUpdate(query);
                               System.out.println("successfully deleted the table.");

                           } else {
                               System.err.println("table not exists..");
                           }
                           System.out.println("Anymore ? type your table name...");*/


                    }
                    statement.close();
                }
                break;
                case 12: {

                    System.out.println("Please enter 'c' for a column or 't' for a table to rename or 'q' to quit :");
                    Scanner in = new Scanner(System.in);
                    boolean toColumnRename;
                    boolean toTableRename;
                    boolean flag;

                    while (true) {

                        String input;
                        input = in.nextLine();

                        if (input.equals("")) {
                            System.err.println("No Input...");
                            continue;
                        }
                        if (input.equals("q")) {
                            break;
                        }

                        if (input.equals("c")) {

                            System.out.println("Enter table name,old column and new column name with Space Blank :");
                            System.out.println("Example : Game GameName GName");
                            String[] item = in.nextLine().split(" ");
                            toColumnRename = false;
                            flag = false;

                            try {

                                DatabaseMetaData dbm = connection.getMetaData();
                                rs = dbm.getTables(null, null, item[0], null);

                                if (rs.next()) {
                                    flag = true;
                                }

                                if (flag) {

                                    query = "SELECT * FROM " + item[0];
                                    rs = statement.executeQuery(query);
                                    ResultSetMetaData rsm = rs.getMetaData();
                                    int columnCount = rsm.getColumnCount();

                                    for (int i = 1; i <= columnCount; i++) {
                                        if (item[1].equalsIgnoreCase(rsm.getColumnName(i))) {
                                            toColumnRename = true;
                                            break;
                                        }
                                    }

                                    if (toColumnRename) {
                                        query = "ALTER TABLE " + item[0] + " " + "RENAME COLUMN " + item[1] + " " + "TO " + item[2];
                                        statement.executeUpdate(query);
                                        System.out.println("successfully renamed the column.");
                                    } else {
                                        System.err.println("column not found..");
                                    }

                                } else {
                                    System.err.println("table not found..");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        } else if (input.equals("t")) {

                            System.out.println("Please enter table name and new table name with Space Blank :");
                            System.out.println("Example : GameEngine Engine");
                            String[] value = in.nextLine().split(" ");
                            toTableRename = false;

                            try {

                                DatabaseMetaData dbm = connection.getMetaData();
                                rs = dbm.getTables(null, null, value[0], null);

                                if (rs.next()) {
                                    toTableRename = true;
                                }

                                if (toTableRename) {

                                    query = "ALTER TABLE " + value[0] + " " + "RENAME TO " + value[1];
                                    statement.executeUpdate(query);
                                    System.out.println("successfully renamed table.");

                                } else {
                                    System.err.println("table not found..");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.err.println("Wrong Input...");
                        }
                        System.out.println("Anymore ? type your new command...");
                    }
                    statement.close();
                }
                break;
                case 14: {

                    System.out.println("Please enter table name or 'q' to quit :");
                    Scanner input = new Scanner(System.in);
                    boolean found;

                    while (true) {

                        String tableName;
                        found = false;
                        tableName = input.nextLine();

                        if (tableName.equals("")) {
                            System.err.println("No Input...");
                            continue;
                        }

                        if (tableName.equals("q")) {
                            break;
                        }

                        DatabaseMetaData dbm = connection.getMetaData();
                        rs = dbm.getTables(null, null, tableName, null);

                        if (rs.next()) {
                            found = true;
                        }

                        if (found) {

                            System.out.println("Please enter attribute and its type with Space Blank :");
                            System.out.println("Example : Gender VARCHAR(6)");
                            String[] value = input.nextLine().split(" ");

                            query = "ALTER TABLE " + tableName + " " + "ADD COLUMN " + value[0] + " " + value[1];
                            statement.executeUpdate(query);
                            System.out.println("successfully added column to table.");

                        } else {
                            System.err.println("table not found..");
                        }
                        System.out.println("Anymore ? enter new table name...");
                    }
                    statement.close();
                }
                break;
                case 15: {

                    System.out.println("Please enter your custom SQLite (query) command or 'q' to quit :");
                    Scanner input = new Scanner(System.in);

                    while (true) {

                        query = input.nextLine();

                        if (query.equals("")) {
                            System.err.println("No Input...");
                            continue;
                        }

                        if (query.equals("q")) {
                            break;
                        }

                        if (query.startsWith("SELECT") | query.startsWith("select")) {
                            rs = statement.executeQuery(query);
                            ResultSetMetaData rsm = rs.getMetaData();
                            int count = rsm.getColumnCount();

                            while (rs.next()) {
                                for (int i = 1; i <= count; i++) {
                                    if (rs.getObject(i) != null) {
                                        System.out.println(rsm.getColumnName(i) + " = " + rs.getObject(i).toString());
                                    } else {
                                        System.out.println(rsm.getColumnName(i) + " = " + "null");
                                    }
                                }
                                System.out.println();
                            }
                        } else {
                            statement.executeUpdate(query);
                            System.out.println("successfully performed the command.");
                        }
                        System.out.println("Anymore ? type your new command...");
                    }
                    statement.close();
                }
                break;
                case 17: {

                    DatabaseMetaData dbm = connection.getMetaData();
                    rs = dbm.getTables(null, null, null, null);

                    while (rs.next()) {
                        System.out.println(rs.getString("TABLE_NAME"));
                    }
                }
                default:
                    System.out.println("There is no operation for input , try again...");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (
                ClassNotFoundException e) {
            System.out.println("Class not found");
        } finally {
            try {
                if (connection != null)
                    connection.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}