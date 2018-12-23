<!DOCTYPE html>
<html lang="en">
<head>
<title>List Files</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	function loadData() {
		var payload = {
			"path" : "E:\\apache-tomcat-8.5.20\\webapps"
		};
		var data = new FormData();
		data.append("json", JSON.stringify(payload));
		fetch("/services/api/v1/getcontents", {
			method : "POST",
			body : data
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			filldata(data["paths"]);
		})
	}
	
	function filldata(jsondata){
		var domNode = document.getElementById("tablebody");
		for(var index in jsondata){
			var tr = document.createElement("tr");
			var td = document.createElement("td");
			td.innerText = jsondata[index].fileName;
			tr.append(td);
			var td2 = document.createElement("td");
			td2.innerText = jsondata[index].fileType;
			tr.append(td2);
			var td3 = document.createElement("td");
			if(jsondata[index].fileType == "file"){
				td3.innerText = "Download";
			}
			tr.append(td3);
			domNode.append(tr);
		}
	}
</script>
</head>
<body onload="loadData()">
	<div class="container">
		<h2>List View</h2>
		<table class="table">
			<thead>
				<tr>
					<th>File Name</th>
					<th>File Type</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody id="tablebody">
			</tbody>
		</table>
	</div>

</body>
</html>
