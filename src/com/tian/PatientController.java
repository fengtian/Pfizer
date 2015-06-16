package com.tian;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.w3c.dom.Document;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

@ManagedBean(name="pc")
@SessionScoped
public class PatientController implements Serializable {
    private String input;  
	private Patient selectedPatient;
	private List<Patient> patients;
	private String selectedOption;
	private List<Appointment> apps;
	private Appointment selectedApp;
	private String appInput;

	public String getAppInput() {
		return appInput;
	}

	public void setAppInput(String appInput) {
		this.appInput = appInput;
	}

	public Appointment getSelectedApp() {
		return selectedApp;
	}

	public void setSelectedApp(Appointment selectedApp) {
		this.selectedApp = selectedApp;
	}

	public List<Appointment> getApps() {
		return apps;
	}

	public void setApps(List<Appointment> apps) {
		this.apps = apps;
	}

	public void delete() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
    	PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("DELETE FROM Patient WHERE Patient.id = ?");//TO-DO: should be appointment id
    	Integer patientId = selectedPatient.getId();
    	pstmt.setString(1, patientId.toString());
    	pstmt.executeUpdate();
    	selectedPatient = new Patient();
    	RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage("Patient deleted successfully"));
    	
	}
	
	public void optionValueChangeMethod(ValueChangeEvent e){
		selectedOption = e.getNewValue().toString();
	}
	
	public void searchApp(){
		try {
			fillApp();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void fillApp() throws ClassNotFoundException, SQLException{
		apps = new ArrayList<Appointment>();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
		PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select * from Appointment where Appointment.patient = ?");
	    //pstmt.setString(1, selectedPatient.getId().toString());
    	pstmt.setInt(1, selectedPatient.getId());

    	ResultSet rows = (ResultSet) pstmt.executeQuery();
    	while(rows.next()){
    		
    		Appointment a = new Appointment();
    		Integer id = rows.getInt("id");
    		String type = rows.getString("type");
    		String synopsis = rows.getString("synopsis");
    		Integer doctorId  = rows.getInt("doctor");
    		String stime = rows.getString("stime");
    		String etime = rows.getString("etime");
    		a.setId(id);
    		a.setStime(stime);
    		a.setSynopsis(synopsis);
    		a.setType(type);
    		a.setDoctorId(doctorId);
    		a.setEtime(etime);
    		apps.add(a);
    	} 
	}
	
	public void searchBy(){
		try {
			fill();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void fill() throws ClassNotFoundException, SQLException{
		patients = new ArrayList<Patient>();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
		PreparedStatement pstmt = null;
		if(selectedOption.equals("lname")){
			pstmt = (PreparedStatement) con.prepareStatement("select * from Patient where Patient.sname = ?");
	    	pstmt.setString(1, input);
		}else{
			pstmt = (PreparedStatement) con.prepareStatement("select * from Patient where Patient.id = ?");
	    	pstmt.setInt(1, Integer.parseInt(input));
		}
    	ResultSet rows = (ResultSet) pstmt.executeQuery();
    	while(rows.next()){
    		Patient p = new Patient();
    		Integer id = rows.getInt("id");
    		String fname = rows.getString("fname");
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
	}
	
	public String getInput() {
		return input;
	}



	public void setInput(String input) {
		this.input = input;
	}



	public Patient getSelectedPatient() {
		return selectedPatient;
	}



	public void setSelectedPatient(Patient selectedPatient) {
		this.selectedPatient = selectedPatient;
	}



	public List<Patient> getPatients() {
		return patients;
	}



	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}



	public String getSelectedOption() {
		return selectedOption;
	}



	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}



	public void selectedDetail(Patient patient){
		RequestContext.getCurrentInstance().closeDialog(patient);
	}
	
	public void selectedApp(Appointment app){
		
		RequestContext.getCurrentInstance().closeDialog(app);
	}
	
 
	public void searchPatientsForm(){
		RequestContext.getCurrentInstance().openDialog("SearchPatients");
	}
	
	public void searchAppForm(){
		RequestContext.getCurrentInstance().openDialog("SearchApp");

	}
	
	
	public void handleSelectedPatient(SelectEvent event){
		this.selectedPatient = (Patient) event.getObject();
	}

	public void handleSeletedApp(SelectEvent event){
		this.selectedApp = (Appointment) event.getObject();
	}
	
	

}
