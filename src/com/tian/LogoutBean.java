package com.tian;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="logoutBean")
@SessionScoped
public class LogoutBean {
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

	     return "index.jsp";
	}


}
