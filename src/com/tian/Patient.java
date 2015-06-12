package com.tian;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.SelectEvent;

import com.mysql.jdbc.PreparedStatement;


@ManagedBean(name="p")
@ViewScoped
public class Patient implements Serializable{
	private String fname;
	private String lname;
	private String dob;
	private String history;
	private String dobSimple;
	public String getDobSimple() {
		return dobSimple;
	}
	public void setDobSimple(String dobSimple) {
		this.dobSimple = dobSimple;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public void fnameValueChangeMethod(ValueChangeEvent e){
		fname = e.getNewValue().toString();
		System.out.println(fname);
	}
	
	public void lnameValueChangeMethod(ValueChangeEvent e){
		lname = e.getNewValue().toString();
		System.out.println(lname);
	}
	
	public void historyValueChangeMethod(ValueChangeEvent e){
		history = e.getNewValue().toString();
		System.out.println(history);
	}
	
	public void dobValueChangeMethod(ValueChangeEvent e){
		dob = e.getNewValue().toString();
		System.out.println(dob);
	}
	public void submitButtonListner() throws ClassNotFoundException, SQLException, ParseException{

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
	
    	PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("INSERT INTO Patient (fname, sname, birthdate, history) VALUES (?, ?, ?, ?);");
    	pstmt.setString(1, fname);
    	pstmt.setString(2, lname);
    	DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date myDate = formatter.parse(dobSimple);
		java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());    	
		pstmt.setDate(3, sqlDate);
    	pstmt.setString(4, history);
    	
    	pstmt.executeUpdate();

	}
	
	public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        dobSimple = format.format(event.getObject());
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", dobSimple));
    }
	
	

}
