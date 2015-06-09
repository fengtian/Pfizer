package com.tian;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
 
@ManagedBean(name="scheduleViewss")
@SessionScoped
public class ScheduleView implements Serializable  {
 
    private ScheduleModel eventModel;
     
    private ScheduleModel lazyEventModel;
 
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    private String currentDoctorName;
    
    


	@PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        eventModel.addEvent(new DefaultScheduleEvent("Champions League Match", previousDay8Pm(), previousDay11Pm()));
        eventModel.addEvent(new DefaultScheduleEvent("Birthday Party", today1Pm(), today6Pm()));
        eventModel.addEvent(new DefaultScheduleEvent("Breakfast at Tiffanys", nextDay9Am(), nextDay11Am()));
        eventModel.addEvent(new DefaultScheduleEvent("Plant the new garden stuff", theDayAfter3Pm(), fourDaysLater3pm()));        
    }
     
	public String getCurrentDoctorName() {
		return currentDoctorName;
	}

	public void setCurrentDoctorName(String currentDoctorName) {
		this.currentDoctorName = currentDoctorName;
	}

    
    public void setupDoctorModel() throws ClassNotFoundException, SQLException, ParseException{
        eventModel = new DefaultScheduleModel();
        ArrayList<DefaultScheduleEvent> appointments = getCurrentDoctorAppoint();
        for(DefaultScheduleEvent e : appointments){
        	eventModel.addEvent(e);
        }

    }
    
    private ArrayList<DefaultScheduleEvent> getCurrentDoctorAppoint() throws ClassNotFoundException, SQLException, ParseException {
    	ArrayList<DefaultScheduleEvent> appointments = new ArrayList<DefaultScheduleEvent>();
    	Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/Pfizer","root","");
    	//Statement stmt = con.createStatement();
    	//ResultSet rows = (ResultSet) stmt.executeQuery("select * from Appointment, Doctor where Doctor.name = ? and Doctor.id = Appointment.doctor");
    	PreparedStatement pstmt = (PreparedStatement) con.prepareStatement("select * from Appointment, Doctor where Doctor.name = ? and Doctor.id = Appointment.doctor");
    	pstmt.setString(1, currentDoctorName);
    	ResultSet rows = (ResultSet) pstmt.executeQuery();
		String synoppsis = "";
    	String stime = null;
    	String etime = null;
    	while(rows.next()){
    		synoppsis = rows.getString(3);
    		stime = rows.getString(6);
    		etime = rows.getString(7);
    	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    
    		Date stimeDate = df.parse(stime);
    		Date etimeDate = df.parse(etime);
            eventModel.addEvent(new DefaultScheduleEvent(synoppsis, stimeDate, etimeDate));
    	}
    	
    	return appointments;
    }
    
   
    
    /*public Date getStringTime(String stringTime){
        Calendar t = (Calendar) today().clone();
        int hour = Integer.parseInt((String) stringTime.subSequence(12, 14));
        
        if(hour < 12){
        	t.set(Calendar.AM_PM, Calendar.AM);
        	t.set(Calendar.DATE, t.get(java.sql.Date.valueOf(stringTime.substring(0, 11))));
        }

    }*/
    
    
    

	public void doctorNameValueChangeMethod(ValueChangeEvent e) throws ClassNotFoundException, SQLException, ParseException{
	
    	currentDoctorName = e.getNewValue().toString();
    	setupDoctorModel();
    }
    
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
 
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
     
    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
         
        return t.getTime();
    }
     
    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date today1Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 1);
         
        return t.getTime();
    }
     
    private Date theDayAfter3Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 2);     
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
 
    private Date today6Pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.HOUR, 6);
         
        return t.getTime();
    }
     
    private Date nextDay9Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 9);
         
        return t.getTime();
    }
     
    private Date nextDay11Am() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.AM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
     
    private Date fourDaysLater3pm() {
        Calendar t = (Calendar) today().clone(); 
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) + 4);
        t.set(Calendar.HOUR, 3);
         
        return t.getTime();
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


	
}
