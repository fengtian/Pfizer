package com.tian;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
 
 
@ManagedBean
public class SelectTimeView {
     
    private String selectedDoctor;
	private String selectedSlot;  
	private String type;
	private String synopsis;
	private String selectedPatient;
	
	
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
    private Map<String, String> slots = new HashMap<String, String>();
    private Map<String, String> patients = new HashMap<String, String>();

    
     
    @PostConstruct
    public void init() {
        
         
        //doctors
    	setUpDoctors();
      
         
        //slots
    	slots = new HashMap<String, String>();
    	slots.put("9am", "9am");
    	slots.put("10am", "10am");
    	
    	//patients
    	patients = new HashMap<String, String>();
    	patients.put("Calvin", "Calvin");
    	patients.put("Adam", "Adam");
    	
    }
 
    private void setUpDoctors(){
    	doctors = new HashMap<String, String>();
    	doctors.put("Philip", "Philip");
    	doctors.put("Sarah", "Sarah");
    }
    
    private void setUpSlots(){
    	
    	
    }
    
    private void setUpPatients(){
    	
    }
 

	public void docNameValueChangeMethod(ValueChangeEvent e) throws ClassNotFoundException, SQLException, ParseException{
	
    	selectedDoctor = e.getNewValue().toString();
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