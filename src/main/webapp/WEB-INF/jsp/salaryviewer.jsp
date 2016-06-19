<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Solinor Salary Viewer</title>

<!-- stylesheet for css. -->
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

.outer-container {
	position: absolute;
	display: table;
	width: 100%;
	height: 100%;
}

.inner-container {
	display: table-cell;
	vertical-align: middle;
	text-align: center;
}

.centered-content {
	display: inline-block;
	background: #fff;
	padding: 20px;
	border: 1px solid #000;
}
</style>

<link rel="stylesheet" type="text/css"
	href="//cdn.datatables.net/1.10.0/css/jquery.dataTables.css">
<script type="text/javascript"
	src="//code.jquery.com/jquery-1.10.2.min.js"></script>
<script type="text/javascript"
	src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>

<!-- Javascript for Datatables. -->
<script type="text/javascript">
	//Plug-in to fetch page data 
	jQuery.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
		return {
			"iStart" : oSettings._iDisplayStart,
			"iEnd" : oSettings.fnDisplayEnd(),
			"iLength" : oSettings._iDisplayLength,
			"iTotal" : oSettings.fnRecordsTotal(),
			"iFilteredTotal" : oSettings.fnRecordsDisplay(),
			"iPage" : oSettings._iDisplayLength === -1 ? 0 : Math
					.ceil(oSettings._iDisplayStart / oSettings._iDisplayLength),
			"iTotalPages" : oSettings._iDisplayLength === -1 ? 0 : Math
					.ceil(oSettings.fnRecordsDisplay()
							/ oSettings._iDisplayLength)
		};
	};

	jQuery(document).ready(function() {

		jQuery("#employeeSalaryData").dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"sort" : "position",
			//bStateSave variable you can use to save state on client cookies: set value "true" 
			"bStateSave" : false,
			//Default: Page display length
			"iDisplayLength" : 10,
			//We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			"iDisplayStart" : 0,
			"fnDrawCallback" : function() {
				//Get page numer on client. Please note: number start from 0 So
				//for the first page you will see 0 second page 1 third page 2...
				//Un-comment below alert to see page number
				//alert("Current page number: "+this.fnPagingInfo().iPage);    
			},
			"sAjaxSource" : "ajax/getMonthlySalaryRecords/",
			"aoColumns" : [ {
				"mData" : "id"//id coloumn
			}, {
				"mData" : "name" //name coloumn
			}, {
				"mData" : "evening_compensation" //evening compensation coloumn
			}, {
				"mData" : "overtime_compensation" //overtime compensation coloumn
			}, {
				"mData" : "total_pay" //total pay coloumn
			},

			]
		});

	});
</script>
</head>
<body>

	<div class="container-fluid bg-1 text-center">
		<h3>Solinor Salary Viewer</h3>
	</div>

	<div class="container-fluid bg-2 text-center">
		<h3>What Am I?</h3>
		<p>This is a Solinor Salary Viewer that shows the Employee name,
			total evening compensation and overtime compensation for March, 2014.
		<div class="outer-container">
			<div class="inner-container">
				<div class="centered-content">
					<form:form action="" method="GET" style="text-align:center;">
						<table width="100%"
							style="border: 3px; background: rgb(243, 244, 248);">
							<tr>
								<td>
									<table id="employeeSalaryData" class="display" cellspacing="0"
										width="100%">
										<thead>
											<tr>
												<th>Id</th>
												<th>Name</th>
												<th>Total Evening Compensation</th>
												<th>Total Overtime Compensation</th>
												<th>Total Salary</th>
											</tr>
										</thead>
									</table>
								</td>
							</tr>
						</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>