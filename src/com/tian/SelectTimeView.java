package com.tian;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.model.DefaultScheduleEvent;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
 
 
@ManagedBean
@ViewScoped
public class SelectTimeView implements Serializable {
     
    private String selectedDoctor;
	private String selectedSlot;  
	private String type = "Company Medical";
	private String synopsis = "";
	private String selectedPatient = "Susan Jordan";
	
	
	@ManagedProperty(value="#{doctorssBean}")
    private Doctors injectedDoctors;
	
    public Doctors getInjectedDoctors() {
		return injectedDoctors;
	}

	public void setInjectedDoctors(Doctors injectedDoctors) {
		this.injectedDoctors = injectedDoctors;
	}

	public String getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(String selectedPatient) {
		this.selectedPatient = selectedPatient;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectedDoctor() {
		return selectedDoctor;
	}

	public void setSelectedDoctor(String selectedDoctor) {
		this.selectedDoctor = selectedDoctor;
	}
    public String getSelectedSlot() {
		return selectedSlot;
	}

	public void setSelectedSlot(String selectedSlot) {
		this.selectedSlot = selectedSlot;
	}

	private Map<String, String> doctors = new HashMap<String, String>();
    private Map<String, String> slots = new LinkedHashMap<String, String>();
    private Map<String, String> patients = new HashMap<String, String>();
   

	@PostConstruct
    public void init()  {
    	setUpDoctors();
    	
    	try {
			setUpPatient();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
	
	public void setUpDoctors(){
		doctors = new LinkedHashMap();
		doctors.put("Philip", "Philip");
		doctors.put("Sarah", "Sarah");
	}
 
    private void setUpPatient() throws ClassNotFoundException, SQLException{
    	patients = new LinkedHashMap<String, String>();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
		Statement stmt = (Statement) con.createStatement();
    	ResultSet rows = (ResultSet) stmt.executeQuery("select * from Patient");
		String firstName = null;
		String lastName = null;
    	while(rows.next()){
    		firstName = rows.getString("fname");
    		lastName = rows.getString("sname");
    		String name = firstName + " " + lastName;
    		patients.put(name, name);
			
    	}
    }
    
  
 

	public void docNameValueChangeMethod(ValueChangeEvent e) throws ClassNotFoundException, SQLException, ParseException{
	
    	selectedDoctor = (String) e.getNewValue();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
		slots = new LinkedHashMap<String, String>();
    	fillSlots(2);
    	PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select stime from Appointment, Doctor where Doctor.name = ? and Doctor.id = Appointment.doctor");
    	pstmt.setString(1, selectedDoctor);
    	ResultSet rows = (ResultSet) pstmt.executeQuery();
		String stime = null;
    	while(rows.next()){
    		stime = rows.getString("stime");
    		String stime1 = stime.substring(0, 19);

			
    		if(slots.get(stime1) != null){
    			slots.remove(stime1);
    		}
    	}
    }
	
	public void slotValueChangeMethod(ValueChangeEvent e){
		selectedSlot = e.getNewValue().toString();
		
	}
	
	public void typeValueChangeMethod(ValueChangeEvent e){
		type = e.getNewValue().toString();
		System.out.println(type);
	}
	
	public void synopsisValueChangeMethod(ValueChangeEvent e){
		synopsis = e.getNewValue().toString();
		System.out.println(synopsis);
	}
	
	public void patientValueChangeMethod(ValueChangeEvent e){
		selectedPatient = e.getNewValue().toString();
		System.out.println(selectedPatient);
	}
	
	public void submitButtonListner() throws ClassNotFoundException, SQLException{
		

	}
	private void fillSlots(int numOfWeeks) throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String today = dateFormat.format(date); //2014/08/06 15:59:48
		String year = today.substring(0, 4);
		String month = today.substring(5, 7);
		String day = today.substring(8, 10);
		for(int w = 0; w < numOfWeeks; w++){
			//day = incrementBySevenString(day);
			for(int d = 0; d < 7; d++){
				
				day = incrementByOneString(day);
				if(Integer.parseInt(day) > 30){
					day = "01";
					incrementByOneString(month);
				}
				if(Integer.parseInt(month) > 12){
					month = "01";
					incrementByOneString(year);
				}
				for(Integer i = 9; i <= 17; i++){
					String hour = i.toString();
					if(i < 10){
						hour = "09";
					}
					
					
		    	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	    Date tempDate = sdf.parse(year + "-" + month + "-" + day + " " + hour + ":01:01");

					slots.put(sdf.format(tempDate), sdf.format(tempDate));
				}
				
			}
		}
	}
	private String incrementByOneString(String s){
		Integer temp = Integer.parseInt(s);
		Integer temp2 = temp + 1;
		return (temp2).toString();
	}
	
	
    
     
    public Map<String, String> getdoctors() {
        return doctors;
    }
 
    public Map<String, String> getslots() {
        return slots;
    }
    
    public Map<String, String> getpatients() {
        return patients;
    }
 
   
}