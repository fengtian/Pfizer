package com.tian;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

@ManagedBean(name="sv")
@ViewScoped
public class SearchView implements Serializable{
     
    private String input;  
	private String selectedOption;
	private Patient searchedPatient;
	private Patient selectedPatient;
	public Patient getSelectedPatient() {
		return selectedPatient;
	}
	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}
	public Patient getSearchedPatient() {
		return searchedPatient;
	}
	public void setSearchedPatient(Patient searchedPatient) {
		this.searchedPatient = searchedPatient;
	}
	private List<Patient> patients;
    
	
     

    public List<Patient> getPatients() {
		return patients;
	}
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	public void inputValueChangeMethod(ValueChangeEvent e){
		input = e.getNewValue().toString();
		System.out.println(input);
	}
	
	public void optionValueChangeMethod(ValueChangeEvent e){
		selectedOption = e.getNewValue().toString();
		System.out.println(selectedOption);
	}
	
	public void selectedDetail(Patient patient){
		RequestContext.getCurrentInstance().closeDialog(patient);
	}
	
	
 
	public void searchPatientsForm(){
		RequestContext.getCurrentInstance().openDialog("SearchPatients");
	}
	
	public void handleSelectedPatient(SelectEvent event){
		this.selectedPatient = (Patient) event.getObject();
	}
    public void searchButtonListener(ValueChangeEvent e) throws ClassNotFoundException, SQLException{
    	
    	patients = new ArrayList<Patient>();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
		PreparedStatement pstmt = null;
		if(selectedOption.equals("lname")){
			pstmt = (PreparedStatement) con.prepareStatement("select * from Patient where Patient.sname = ?");
	    	pstmt.setString(1, input);
			System.out.println(pstmt.toString());

		}else{
			pstmt = (PreparedStatement) con.prepareStatement("select * from Patient where Patient.id = ?");
	    	pstmt.setInt(1, Integer.parseInt(input));
			System.out.println(pstmt.toString());


		}

		

    	ResultSet rows = (ResultSet) pstmt.executeQuery();
    
    	while(rows.next()){
    		Patient p = new Patient();
    		Integer id = rows.getInt("id");
    		String fname = rows.getString("fname");
    		System.out.println(fname);
    		String lname = rows.getString("sname");
    		String dob = rows.getString("birthdate");
    		String history = rows.getString("history");
    		p.setDob(dob);
    		p.setFname(fname);
    		p.setHistory(history);
    		p.setId(id);
    		p.setLname(lname);
    		patients.add(p);
    	}
    	System.out.println(patients.isEmpty());
    	for(Patient p : patients){
    		System.out.println(p.getFname());
    	}
    
    	
    }
	
	
}