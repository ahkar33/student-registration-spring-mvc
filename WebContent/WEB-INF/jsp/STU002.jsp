<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
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
				<form:form action="/SpringMvcStudentRegistration/updateStudent" method="post"
					modelAttribute="data">
					<h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Update</h2>
					<h3 style="color: red; text-align: center;">${error}</h3>
					<h3 style="color: green; text-align: center;">${message}</h3>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Student
							ID</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" id="name"
								readonly="true" value="${data.id}" path="id" />
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="name" class="col-md-2 col-form-label">Name</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" id="name"
								path="name" value="${data.name}" />
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="dob" class="col-md-2 col-form-label">DOB</label>
						<div class="col-md-4">
							<form:input type="date" class="form-control" id="dob"
								value="${data.dob}" path="dob" />
						</div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Gender</legend>
						<div class="col-md-4">
							<c:choose>
								<c:when test="${empty data.gender}">
									<div class="form-check-inline">
										<form:radiobutton class="form-check-input" path="gender"
											id="gridRadios1" value="Male" checked="checked" />
										<label class="form-check-label" for="gridRadios1">
											Male </label>
									</div>
									<div class="form-check-inline">
										<form:radiobutton class="form-check-input" path="gender"
											id="gridRadios2" value="Female" />
										<label class="form-check-label" for="gridRadios2">Female</label>
									</div>
								</c:when>
								<c:otherwise>
									<c:if test="${data.gender eq \"Male\"}">
										<div class="form-check-inline">
											<form:radiobutton class="form-check-input" path="gender"
												id="gridRadios1" value="Male" checked="checked" />
											<label class="form-check-label" for="gridRadios1">
												Male </label>
										</div>
										<div class="form-check-inline">
											<form:radiobutton class="form-check-input" path="gender"
												id="gridRadios2" value="Female" />
											<label class="form-check-label" for="gridRadios2">Female</label>
										</div>
									</c:if>
									<c:if test="${data.gender eq \"Female\"}">
										<div class="form-check-inline">
											<form:radiobutton class="form-check-input" path="gender"
												id="gridRadios1" value="Male" />
											<label class="form-check-label" for="gridRadios1">
												Male </label>
										</div>
										<div class="form-check-inline">
											<form:radiobutton class="form-check-input" path="gender"
												id="gridRadios2" value="Female" checked="checked" />
											<label class="form-check-label" for="gridRadios2">Female</label>
										</div>
									</c:if>

								</c:otherwise>
							</c:choose>

						</div>
					</fieldset>

					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="phone" class="col-md-2 col-form-label">Phone</label>
						<div class="col-md-4">
							<form:input type="text" class="form-control" id="phone"
								value="${empty data.phone ? '+95' : data.phone}" path="phone" />
						</div>
					</div>
					<div class="row mb-4">
						<div class="col-md-2"></div>
						<label for="education" class="col-md-2 col-form-label">Education</label>
						<div class="col-md-4">
							<form:select class="form-select" aria-label="Education"
								id="education" path="education">

								<c:if test="${not empty data.education}">
									<form:option value="${data.education}">${data.education}</form:option>
								</c:if>
								<c:if
									test="${data.education != \"Bachelor of Information Technology\"}">
									<option value="Bachelor of Information Technology">Bachelor
										of Information Technology</option>
								</c:if>
								<c:if test="${data.education != \"Diploma in IT\"}">
									<form:option value="Diploma in IT">Diploma in IT</form:option>
								</c:if>
								<c:if
									test="${data.education != \"Bachelor of Computer Science\"}">
									<form:option value="Bachelor of Computer Science">Bachelor
										of Computer Science</form:option>
								</c:if>

							</form:select>
						</div>
					</div>
					<fieldset class="row mb-4">
						<div class="col-md-2"></div>
						<legend class="col-form-label col-md-2 pt-0">Attend</legend>
						<div class="col-md-6 offset-md-4 mt-4">
							<c:forEach var="course" items="${courseList}">
								<div class="form-check-inline col-md-2">
									<form:checkbox class="form-check-input" path="attendCourses"
										id="gridRadios1" value="${course.id}" />
									<label class="form-check-label" for="gridRadios1">${course.name}</label>
								</div>
							</c:forEach>
						</div>
					</fieldset>
					<div class="row mb-4">
						<div class="col-md-4"></div>

						<div class="col-md-4">
							<button type="submit" class="btn btn-secondary col-md-2">
								Update</button>
							<button type="button" class="btn btn-danger"
								onclick="location.href = '/SpringMvcStudentRegistration/deleteStudent/${data.id}'">
								<span>Delete</span>
							</button>
							<!-- 							<div class="modal fade" id="exampleModal" tabindex="-1"
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
							</div> -->
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<%@ include file="./layouts/footer.html"%>
</body>

</html>