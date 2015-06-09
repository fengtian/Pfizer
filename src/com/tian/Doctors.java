package com.tian;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name="doctorssBean")
@SessionScoped
public class Doctors {
	private ArrayList<Doctor> doctors;
	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}

	private String currentDoctorName;
	
		
	

	public String getCurrentDoctorName() {
		return currentDoctorName;
	}

	public void setCurrentDoctorName(String currentDoctorName) {
		this.currentDoctorName = currentDoctorName;
	}

	public void addDoctor(Doctor doctor){
		doctors.add(doctor);
	}
	
	public Doctor getDoctor(String name){
		for(Doctor d : doctors){
			if(d.getName().equals(name)){
				return d;
				}
			}
		return null;
	}

	public void doctorNameValueChangeMethod(ValueChangeEvent e){
		currentDoctorName = e.getNewValue().toString();
	}

	
	
	
	}
