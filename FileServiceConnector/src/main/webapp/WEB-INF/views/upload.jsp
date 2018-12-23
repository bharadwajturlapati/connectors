<html>
<head>
<title>File Upload</title>
</head>
<body>
	<form action="/services/uploadfile" method="post" enctype="multipart/form-data">
		Select document to upload: <input type="file" name="file"
			id="file"> 
			<input type="submit" value="Upload file"
			name="submit">
	</form>
</body>
</html>