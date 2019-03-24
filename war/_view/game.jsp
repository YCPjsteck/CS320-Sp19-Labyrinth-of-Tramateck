<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/game" method="post">
			<table>
				<tr>
					<td class="label">Player input:</td>
					<td><input type="text" name="input" size="20" value="${input}" /></td>
				</tr>
				<tr>
					<td class="label">Game output:</td>
					<td>${output}</td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Submit">
		</form>
	</body>
</html>