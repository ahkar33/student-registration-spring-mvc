<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value="/resources/stylesheets/test.css" />">
<title> Student Registration LGN001 </title>
</head>
<body class="login-page-body"> 
    <div class="login-page">
      <div class="form">
        <div class="login">
          <div class="login-header">
            <h1>Welcome!</h1>
            <h3 style="color: red; text-align: center;">${error}</h3>
          </div>
        </div>
        <form:form class="login-form" action="/SpringMvcStudentRegistration/login" method="post" name="confirm" modelAttribute="data">
          <form:input type="email" placeholder="Email" value="${data.email}" path="email" required="required"/>
          <form:input type="password" placeholder="Password" value="${data.password}" path="password" required="required"/>
          <button>login</button>
        </form:form>
      </div>
    </div>
</body>

</html>