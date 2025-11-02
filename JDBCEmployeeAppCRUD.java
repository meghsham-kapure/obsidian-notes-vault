package codex;

import java.sql.*;
import java.util.Scanner;

public class JDBCEmployeeAppCRUD {

    public static void main(String[] args) throws SQLException,ClassCastException {
        try {
            // Load and register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LearningJDBC", "root", "p@ss");

            Scanner sc = new Scanner(System.in);
            Statement stmt = con.createStatement();

            while (true) {
                System.out.println("System menu for employee record management");
                System.out.println("1. Create Employee Record");
                System.out.println("2. Read Employee Record");
                System.out.println("3. Update Employee Record");
                System.out.println("4. Delete Employee Record");
                System.out.println("5. Exit");

                System.out.print("Please enter your choice ==> ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter details of employee:");

                        System.out.print("Enter fullname of employee ==> ");
                        String fullname = sc.nextLine();

                        System.out.print("Enter salary of employee ==> ");
                        Double salary = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Enter job title of employee ==> ");
                        String jobTitle = sc.nextLine();

                        System.out.print("Enter residential area of employee ==> ");
                        String residentialArea = sc.nextLine();

                        String queryStr = String.format(
                                "INSERT INTO employee (fullname, salary, jobTitle, residentialArea) VALUES ('%s', %f, '%s', '%s')",
                                fullname, salary, jobTitle, residentialArea
                        );
                        System.out.println(stmt.executeUpdate(queryStr) == 1 ? "Successful Insertion" : "Unsuccessful Insertion");
                        break;

                    case 2:
                        System.out.println("1. Show all available records");
                        System.out.println("2. Show record specified by ID");
                        int subChoice = sc.nextInt();
                        sc.nextLine();

                        ResultSet rs;
                        switch (subChoice) {
                            case 1:
                                queryStr = "SELECT * FROM employee";
                                rs = stmt.executeQuery(queryStr);
                                while (rs.next()) {
                                    System.out.println();
                                    System.out.println("Employee Id: " + rs.getInt("eid"));
                                    System.out.println("Employee Fullname: " + rs.getString("fullname"));
                                    System.out.println("Employee Salary: " + rs.getDouble("salary"));
                                    System.out.println("Employee Job Title: " + rs.getString("jobTitle"));
                                    System.out.println("Employee Residential Area: " + rs.getString("residentialArea"));
                                    System.out.println();
                                }
                                break;

                            case 2:
                                System.out.print("Enter ID to be searched ==> ");
                                int eid = sc.nextInt();
                                sc.nextLine();
                                queryStr = String.format("SELECT * FROM employee WHERE eid = %d", eid);
                                rs = stmt.executeQuery(queryStr);
                                if (rs.next()) {
                                    System.out.println();
                                    System.out.println("Employee Id: " + rs.getInt("eid"));
                                    System.out.println("Employee Fullname: " + rs.getString("fullname"));
                                    System.out.println("Employee Salary: " + rs.getDouble("salary"));
                                    System.out.println("Employee Job Title: " + rs.getString("jobTitle"));
                                    System.out.println("Employee Residential Area: " + rs.getString("residentialArea"));
                                    System.out.println();
                                } else {
                                    System.out.println("No records found for the specified ID");
                                }
                                break;

                            default:
                                System.out.println("Invalid choice");
                                break;
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID of employee record to update ==> ");
                        int eid = sc.nextInt();
                        sc.nextLine();

                        queryStr = String.format("SELECT * FROM employee WHERE eid = %d", eid);
                        rs = stmt.executeQuery(queryStr);
                        if (rs.next()) {
                            fullname = rs.getString("fullname");
                            salary = rs.getDouble("salary");
                            jobTitle = rs.getString("jobTitle");
                            residentialArea = rs.getString("residentialArea");

                            System.out.println("Current Fullname: " + fullname);
                            System.out.print("Enter new fullname (leave empty to keep current) ==> ");
                            String newFullname = sc.nextLine();
                            if (!newFullname.isEmpty()) {
                                fullname = newFullname;
                            }

                            System.out.println("Current Salary: " + salary);
                            System.out.print("Enter new salary (leave empty to keep current) ==> ");
                            String newSalary = sc.nextLine();
                            if (!newSalary.isEmpty()) {
                                salary = Double.parseDouble(newSalary);
                            }

                            System.out.println("Current Job Title: " + jobTitle);
                            System.out.print("Enter new job title (leave empty to keep current) ==> ");
                            String newJobTitle = sc.nextLine();
                            if (!newJobTitle.isEmpty()) {
                                jobTitle = newJobTitle;
                            }

                            System.out.println("Current Residential Area: " + residentialArea);
                            System.out.print("Enter new residential area (leave empty to keep current) ==> ");
                            String newResidentialArea = sc.nextLine();
                            if (!newResidentialArea.isEmpty()) {
                                residentialArea = newResidentialArea;
                            }

                            queryStr = String.format(
                                    "UPDATE employee SET fullname = '%s', salary = %f, jobTitle = '%s', residentialArea = '%s' WHERE eid = %d",
                                    fullname, salary, jobTitle, residentialArea, eid
                            );
                            System.out.println(stmt.executeUpdate(queryStr) == 1 ? "Successful Update" : "Unsuccessful Update");
                        } else {
                            System.out.println("No records found for the specified ID");
                        }
                        break;

                    case 4:
                        System.out.print("Enter ID of employee record to delete ==> ");
                        int deleteEid = sc.nextInt();
                        sc.nextLine();
                        queryStr = String.format("DELETE FROM employee WHERE eid = %d", deleteEid);
                        System.out.println(stmt.executeUpdate(queryStr) == 1 ? "Successful Deletion" : "Unsuccessful Deletion");
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        con.close();
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
