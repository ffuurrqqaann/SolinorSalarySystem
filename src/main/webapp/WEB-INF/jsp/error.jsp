<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

<style type="text/css">
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 1px solid #ff0000;
	padding: 8px;
	margin: 16px;
}

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
		<!-- show error if any. -->
		<c:if test="${not empty error}">
			<h3 class="errorblock">${error}</h3>
		</c:if>
	</div>
</body>
</html>