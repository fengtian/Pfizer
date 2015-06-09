package com.tian;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PageController {
	public String moveToAddApp(){
	    return "AddApp"; 
	}

}
