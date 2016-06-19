<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet"
		href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	
	<style>
	.bg-1 {
		background-color: #1abc9c;
		color: #ffffff;
	}
	
	.bg-2 {
		background-color: #474e5d;
		color: #ffffff;
	}
	
	.bg-3 {
		background-color: #ffffff;
		color: #555555;
	}
	
	.container-fluid {
		padding-top: 70px;
		padding-bottom: 70px;
	}
	</style>
</head>
<body>
	<div class="container-fluid bg-1 text-center">
		<h3>Solinor Salary Viewer</h3>
	</div>

	<div class="container-fluid bg-2 text-center">
		<h3>What Am I?</h3>
		<p>Solinor Salary Viewer is a system that allow you to upload your
			employees daily check-in and check-out time in to the system that
			calculates the monthly pay for the employees along with their
			compensation i.e., overtime and evening compensations.</p>
		<div style="float: none; margin-left: auto; margin-right: auto;">
			<form action="${baseURL}/SolinorSalarySystem/uploadfile.html"
				method="POST" enctype="multipart/form-data" role="form">
				<div class="form-group">
					<input type="file" name="file" style="margin: 0 auto;" /> <br />
					<button class="btn btn-default btn-lg" type="submit" id="upload">Upload</button>

					<!-- show error if any. -->
					<c:if test="${not empty model.error}">
						<h3 class="errorblock">${error}</h3>
					</c:if>
				</div>
			</form>
		</div>
	</div>

	<div class="container-fluid bg-3 text-center">
		<h3>Instructions</h3>
		<p>Please choose a file and click on the 'Upload' buton.The file
			must be a comma-separated csv file that contains your employees data.</p>
	</div>
</body>
</html>