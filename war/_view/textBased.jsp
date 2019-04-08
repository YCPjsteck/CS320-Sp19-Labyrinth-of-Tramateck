<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Text Based</title>
			<style>
				.error {
					color: red;
				}
				body {
					background-color: black;
				}
				td {
					color: #39ff14;
					text-align: center;
				}
				td.label {
					color: #39ff14;
					text-align: right;
				}
                input {
                	color: #39ff14;
                    border: 2px solid #606060;
                    background-color: black;
                }
                hr {
                	color: #39ff14;
					border-style: solid;
  					border-width: 1px;    
				}
			</style>
	</head>
	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/textBased" method="post">
        	<hr>
			<table>
            
				<tr>
					<td class="label">Player input:</td>
					<td><input type="text" name="input" size="20" value="${input}" />					</td>
				</tr>
				<tr>
					<td class="label">Game output:</td>
					<td>${output}</td>
				</tr>
			</table>
            <hr>
			<input type="Submit" name="submit" value="Submit">
		</form>
	</body>
</html>