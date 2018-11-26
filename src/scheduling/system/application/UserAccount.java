/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.system.application;

/**
 *
 * @author christianbaiza
 */
public class UserAccount {
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String pass) {
		password = pass;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String mail) {
		email = mail;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String fName) {
		firstName = fName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lName) {
		lastName = lName;
	}

}

