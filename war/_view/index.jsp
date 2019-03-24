<!DOCTYPE html>

<html>
	<head>
		<title>Index view</title>
	</head>

	<body>
		Welcome to the index view jsp! Click one of the following buttons to go to that page.
		<form action="${pageContext.servletContext.contextPath}/index" method="post">
			<input type="Submit" name="add" value="Add Numbers">
			<input type="Submit" name="text" value="Text Based">
		</form>
	</body>
</html>
