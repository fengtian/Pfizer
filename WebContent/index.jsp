<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<head>
<body>
<f:view>
Welcome to Pfizer online system<br>
<h:form>
Username  <h:inputText value="#{user.username}"></h:inputText><br>
Password <h:inputSecret value="#{user.password}"></h:inputSecret><br>
<h:commandButton value="Login" action ="#{user.login()}"></h:commandButton>
</h:form>
</f:view>
</body>
</html>
