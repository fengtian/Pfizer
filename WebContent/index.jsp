<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<html>
<head>
<title>Home</title>

  <meta charset="utf-8"></meta>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<head>
<body>
<div class="container">

<f:view>
<h2>Welcome to Pfizer Online System</h2><br>
<h:form>
  <div class="form-group">
    <label for="Username">Username</label>

<h:inputText id="Username" value="#{user.username}"></h:inputText>
</div>
  <div class="form-group">
    <label for="Password">Password</label>

<h:inputSecret id="Password" value="#{user.password}"></h:inputSecret>
</div>
<div class="checkbox">
      <label><input type="checkbox"> Remember me</label>
    </div>
<h:commandButton value="Login" action ="#{user.login()}"></h:commandButton>
</h:form>
</f:view>
</div>
</body>
</html>
