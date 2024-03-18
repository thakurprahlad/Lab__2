package drive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import jdbc.MenuDriven;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//1 TODO Auto-generated method stub
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/bank";
		String username = "root";
	 	String pwd = "akash123";
		//2
		Connection con = DriverManager.getConnection(url, username, pwd);
		Scanner sc = new Scanner(System.in);
		
		MenuDriven stud = new MenuDriven();
		
		stud.runMenu();
		
		
		
	}

}