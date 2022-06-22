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
	<%@ include file="./layouts/header.jsp"%>
	<!-- <div id="testsidebar">Hello World </div> -->
	<div class="container">
		<%@ include file="./layouts/sidenav.html"%>
		<div class="main_contents">
			<div id="sub_content">
				<form action="SearchStudentServlet" class="row g-3 mt-3 ms-2">
					<div class="col-auto">
						<label for="staticEmail2" class="visually-hidden">studentID</label>
						<input type="text" class="form-control" id="staticEmail2"
							name="id" placeholder="Student ID">
					</div>
					<div class="col-auto">
						<label for="inputPassword2" class="visually-hidden">studentName</label>
						<input type="text" class="form-control" id="inputPassword2"
							name="name" placeholder="Student Name">
					</div>
					<div class="col-auto">
						<label for="inputPassword2" class="visually-hidden">Course</label>
						<input type="text" class="form-control" id="inputPassword2"
							name="course" placeholder="Course">
					</div>
					<div class="col-auto">
						<button type="submit" class="btn btn-success mb-3">Search</button>
					</div>
					<div class="col-auto">
						<button type="reset" class="btn btn-secondary mb-3">Reset</button>
					</div>
				</form>
				<div>
					<table class="table table-striped" id="stduentTable">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Student ID</th>
								<th scope="col">Name</th>
								<th scope="col">Course Name</th>
								<th scope="col">Details</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="count" value="0" scope="page" />
						<%-- 	<c:set var="courseList"  value="${coursesLists}" scope="page"/> --%>
							<c:forEach var="student" items="${studentList}" varStatus="loop">
								<tr>
									<c:set var="count" value="${count + 1}" scope="page" />
									<th scope="row">${count}</th>
									<td>${student.id}</td>
									<td>${student.name}</td>
									<td>
										<c:forEach var="course"
											items="${coursesLists[loop.index]}">
											${course.name},
										</c:forEach></td>
									<td>
										<a href='SeeMoreServlet?id=${student.id}'>
											<button type="submit" class="btn btn-secondary mb-2">
												See More
											</button>
										</a>
									</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="./layouts/footer.html"%>
</body>

</html>
