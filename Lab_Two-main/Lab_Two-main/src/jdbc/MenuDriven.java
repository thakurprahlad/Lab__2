package jdbc;

import java.sql.*;
import java.util.Scanner;

public class MenuDriven {
	Scanner sc = new Scanner(System.in);
	Connection con;

	public void runMenu() throws SQLException {
		connectToDatabase();

		int choice;
		do {
			displayMenu();
			choice = sc.nextInt();
			switch (choice) {
			case 1 -> insertCustomer();
			case 2 -> updateCustomer();
			case 3 -> readCustomer();
			case 4 -> removeCustomer();
			case 5 -> System.out.println("Exiting program. Goodbye!");
			default -> System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 5);

		closeDatabasecon();
		sc.close();
	}

	private void connectToDatabase() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/bank";
		String user = "root";
		String password = "akash123";
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connected to the database.");
	}

	private void closeDatabasecon() throws SQLException {
		if (con != null && !con.isClosed()) {
			con.close();
		}
	}

	private void displayMenu() {
		System.out.println("\nMenu:");
		System.out.println("1. Insert Customer");
		System.out.println("2. Update Customer");
		System.out.println("3. Read Customer");
		System.out.println("4. Remove Customer");
		System.out.println("5. Exit");
		System.out.print("Enter your choice: ");
	}

	private void insertCustomer() throws SQLException {
		System.out.print("Enter customer ID: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter customer name: ");
		String name = sc.nextLine();
		System.out.print("Enter account type (savings/current): ");
		String type = sc.nextLine();
		System.out.print("Enter  Balance: ");
		double bal = sc.nextDouble();
		sc.nextLine();
		System.out.print("Enter status: ");
		String status = sc.nextLine();

		String query = String.format("INSERT INTO accounts VALUES (%d, '%s', '%s', '%f', '%s')", id, name, type, bal,
				status);
		Statement statement = con.createStatement();
		statement.executeUpdate(query);
		System.out.println("Customer added successfully.");
	}

	private void updateCustomer() throws SQLException {
		System.out.print("Enter customer ID to update: ");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.print("Enter new name for customer: ");
		String newName = sc.nextLine();
		System.out.print("Enter new account type: ");
		String newType = sc.nextLine();
		System.out.print("Enter new balance: ");
		double newBal = sc.nextDouble();
		sc.nextLine();
		System.out.print("Enter new status: ");
		String newStatus = sc.nextLine();

		String query = String.format(
				"UPDATE accounts SET CustomerName='%s', AccountType='%s', Balance=%f, Status='%s' WHERE AccountNumber=%d",
				newName, newType, newBal, newStatus, id);

		Statement statement = con.createStatement();
		int rowsAffected = statement.executeUpdate(query);

		if (rowsAffected > 0) {
			System.out.println("Customer updated successfully.");
		} else {
			System.out.println("Customer with ID " + id + " not found for updating.");
		}
	}

	private void readCustomer() throws SQLException {
		String query = "SELECT * FROM accounts;";
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(query);

		// Process and display the result set
		while(rs.next()) {
        	System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+ rs.getString(3)+" "+rs.getDouble(4)+" "+rs.getString(5));
        }

		// Close resources
		rs.close();
		statement.close();
	}

	private void removeCustomer() throws SQLException {
		System.out.print("Enter Customer ID to remove: ");
		int id = sc.nextInt();

		String query = String.format("DELETE FROM accounts WHERE AccountNumber=%d", id);

		Statement statement = con.createStatement();
		int rowsAffected = statement.executeUpdate(query);

		if (rowsAffected > 0) {
			System.out.println("Customer removed successfully.");
		} else {
			System.out.println("Customer with ID " + id + " not found.");
		}
	}
}
