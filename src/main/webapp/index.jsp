<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/> 
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script> 
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" ></script> 
</head>
<body>
	
	
	<div class="form-group">
		<a href="<c:url value="/uploadcsv.html" />" class="btn btn-primary" id="upload">Upload Employee Data</a>
		<a href="<c:url value="/salaryviewer.html" />" class="btn btn-primary" id="view">Go To Salary Viewer</a> 
	</div>
	
	<!-- <form action="uploadfile" method="POST"
		enctype="multipart/form-data">
		<div class="form-group">
			<label class="control-label">Upload Employee Data</label> 
			<input id="input-folder-1" type="file" name="file" class="file-loading" webkitdirectory />
		</div>
		<button type="submit" class="btn btn-primary" id="upload">Upload</button>
	</form> -->
</body>
</html>