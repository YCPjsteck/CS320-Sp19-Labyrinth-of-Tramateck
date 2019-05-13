<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<style>
			* {
				font-family: 'Share Tech Mono', monospace;
				font-weight: 300;
			}
			body {
				background-color: black;
			}
			.error {
				color: red;
			}
			td.label {
				text-align: right;
			}
			form {
				color: #39FF14;
			}
			input[type=text], select {
				width: 100%;
				background-color: black;
				color: #39FF14;
				border-color: #39FF14;
				border-style: solid;
				outline: none;
				display: border-box;
			}
			input[type=submit] {
				width: 100%;
				background-color: black;
				color: #39FF14;
				border: 2px solid #39FF14;
				border-radius: 0px;
				cursor: pointer;
				display: border-box;
				outline: none;
			}
			#contentGrid {
				display: grid;
				align-content: center;
			}
			#outputBox {
				border-color: #39FF14;
				color: #39FF14;
				background-color:black;
			}
			#inputBox {
				display: grid;
				grid-template-columns: 4fr 1fr;
				background color: black;
			}
		</style>
	</head>
	<body>
		<form action="/action_page.php">
			Username: <input type="text" name="username"><br>
			Password: <input type="text" name="password"><br>
			<br>
			<br>
			<input type="submit" value="Submit">
		</form>
	</body>
</html>
