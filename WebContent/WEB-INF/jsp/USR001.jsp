<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Course Registration</title>
</head>

<body>
 	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if (session.getAttribute("userInfo") == null) {
			response.sendRedirect("/SpringMvcStudentRegistration/login");
	}
	%> 
	<%@ include file="header.jsp"%>

	<div class="container">
		<%@ include file="sidenav.html"%>
		<div class="main_contents">
			<div id="sub_content">
				<form:form action="/SpringMvcStudentRegistration/addUser" method="post" modelAttribute="data">
					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>
					<h3 style="color: red; text-align: center;">${error}</h3>
					<h3 style="color: green; text-align: center;">${message}</h3>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="email" class="col-md-2 col-form-label">Email</label>
						<div class="col-md-4">
							<form:input type="email" class="form-control" id="email" path="email"
								value="${data.email}"/>
						</div>
					</div>
					<div class="row mb-3">
						<div class="col-md-2"></div>
						<label for="Name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" id="name" path="name"
								value="${data.name}"/>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="Passowrd" class="col-md-2 col-form-label">Password</label>
						<div class="col-md-4">
							<form:input type="password" class="form-control" id="password"
								path="password" value="${data.password}"/>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="confirmPassword" class="col-md-2 col-form-label">Confirm
							Password</label>
						<div class="col-md-4">
							<form:input type="password" class="form-control" id="confirmPassword"
								path="confirmPassword" value="${data.confirmPassword}"/>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="userRole" class="col-md-2 col-form-label">User
							Role</label>
						<div class="col-md-4">
							<form:select class="form-select" aria-label="Education" id="userRole"
								path="userRole">
								<c:if test="${not empty data.userRole}">
									<form:option value="${data.userRole}">${data.userRole}</form:option>
								</c:if>
								<c:if test="${data.userRole != \"Admin\"}">
									<form:option value="Admin">Admin</form:option>
								</c:if>
								<c:if test="${data.userRole != \"User\"}">
									<form:option value="User">User</form:option>
								</c:if>
							</form:select>
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="col-md-6">


							<button type="submit" class="btn btn-secondary col-md-2">Add</button>
							<div class="modal fade" id="exampleModal" tabindex="-1"
								aria-labelledby="exampleModalLabel" aria-hidden="true">
								<div class="modal-dialog modal-dialog-centered">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">Student
												Registration</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">

											<h5 style="color: rgb(127, 209, 131);">Registered
												Succesfully !</h5>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-success col-md-2"
												data-bs-dismiss="modal">Ok</button>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<%@ include file="footer.html"%>
</body>

</html>