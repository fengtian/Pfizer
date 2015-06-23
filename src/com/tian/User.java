package com.tian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.context.RequestContext;

@ManagedBean(name="user")
@SessionScoped
public class User {
	private String username;
	private String password;
    private UIComponent mybutton;


	public UIComponent getMybutton() {
		return mybutton;
	}
	public void setMybutton(UIComponent mybutton) {
		this.mybutton = mybutton;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() throws SQLException, ClassNotFoundException{
		 
		Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
			PreparedStatement ps = con.prepareStatement("select Recep_id from Recep where Password = ? and Username = ?");
			ps.setString(1, password);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				return  "System.jsp";
			}else{
				FacesMessage message = new FacesMessage("Wrong username or password");
	            FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(mybutton.getClientId(context), message);
			return null;
			}
			
		
		
	}

}
