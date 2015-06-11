<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<f:view>
<h:form>
<h:commandButton value="Appointment" action ="Appointment.xhtml"></h:commandButton><br>
<h:commandButton value="Patient" action ="Patients.xhtml"></h:commandButton><br>
<h:commandButton value="Logout" action ="#{logoutBean.logout()}"></h:commandButton>

</h:form>
</f:view>
</body>
</html>