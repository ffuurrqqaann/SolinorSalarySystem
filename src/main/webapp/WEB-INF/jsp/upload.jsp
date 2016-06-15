<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head></head>
<body>
	<h1>File Upload.</h1>
	<form action="${baseURL}/SolinorSalarySystem/uploadfile.html" method="POST"
		enctype="multipart/form-data">
		<div class="form-group">
			<label>File input</label> <input type="file" name="file" />
		</div>
		<br />
		<br />
		<div class="form-group">
			<button type="submit" id="upload">Upload</button>
		</div>
	</form>
</body>
</html>