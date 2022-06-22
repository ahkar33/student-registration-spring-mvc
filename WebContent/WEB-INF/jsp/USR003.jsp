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
	<!-- <div id="testsidebar">Hello World </div> -->
	<div class="container">
		<%@ include file="sidenav.html"%>
		<div class="main_contents">
			<div id="sub_content">
				<form action="/SpringMvcStudentRegistration/searchUser"
					class="row g-3 mt-3 ms-2">
					<div class="col-auto">
						<label for="staticEmail2" class="visually-hidden">User Id</label>
						<input type="text" class="form-control" id="staticEmail2"
							name="id" placeholder="User ID">
					</div>
					<div class="col-auto">
						<label for="inputPassword2" class="visually-hidden"> User
							Name</label> <input type="text" class="form-control" id="inputPassword2"
							placeholder="User Name" name="name">
					</div>

					<div class="col-auto">
						<button type="submit" class="btn btn-primary mb-3">
							Search</button>
					</div>
					<div class="col-auto">
						<button type="button" class="btn btn-secondary "
							onclick="location.href = '/SpringMvcStudentRegistration/addUser';">Add</button>

					</div>
					<div class="col-auto">
						<button type="reset" class="btn btn-danger mb-3">Reset</button>
					</div>
				</form>

				<table class="table table-striped" id="stduentTable">
					<thead>
						<tr>

							<th scope="col">User ID</th>
							<th scope="col">User Name</th>
							<th scope="col">Details</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${userList}">
							<tr>
								<td>${user.id}</td>
								<td>${user.name}</td>

								<td>
									<button type="button" class="btn btn-success"
									onclick="location.href = '/SpringMvcStudentRegistration/updateUser/${user.id}';">Update</button>
								</td>
								<td><button type="submit"
										class="btn btn-secondary mb-3 <c:if test="${user.id eq userInfo.id}"><c:out value="disabled"/></c:if>"
										onclick="location.href = '/SpringMvcStudentRegistration/deleteUser/${user.id}'"
										data-bs-toggle="modal" data-bs-target="#exampleModal">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<!-- 				<div class="modal fade" id="exampleModal" tabindex="-1"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">Student
									Deletion</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">

								<h5 style="color: rgb(127, 209, 131);">Are you sure want to
									delete !</h5>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-success col-md-2"
									data-bs-dismiss="modal">Ok</button>
							</div>
						</div>
					</div>
				</div> -->
			</div>
		</div>
	</div>
	<%@ include file="footer.html"%>
</body>

</html>



