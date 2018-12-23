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
	function loadData(){
		var payload = {
			"path" : "E:\\apache-tomcat-8.5.20\\webapps"
		};
		
		fetch("/services/api/v1/getcontents", {
			method : "POST",
			headers: new Headers({'Content-Type': 'application/json'}),
			body : JSON.stringify(payload)
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			filldata(data["newInstance"]["paths"]);
		});
	}
	
	function loadData2(){
		var domNode = document.getElementById("tablebody");
		domNode.innerText="";
		domNode.innerHTML= "";
		var path = event.target.getAttribute("filepath");
		var payload = {
			"path" : path
		};
		
		fetch("/services/api/v1/getcontents", {
			method : "POST",
			headers: new Headers({'Content-Type': 'application/json'}),
			body : JSON.stringify(payload)
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			filldata(data["newInstance"]["paths"]);
		});
	}
	
	function downloadFile(){
		var path = event.target.getAttribute("filepath");
		var filename = event.target.getAttribute("filename");
		var payload = {
			"path" : path
 		};
		fetch("/services/api/v1/downloadfile", {
			method : "POST",
			headers: new Headers({'Content-Type': 'application/json'}),
			body : JSON.stringify(payload)
		}).then(function(res) {
			return res.blob();
		}).then(function(blob) {
			var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = filename;
            document.body.appendChild(a);
            a.click();    
            a.remove();
		});
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
				td3.innerHTML = "<button filename='"+jsondata[index].fileName+"' filepath='"+jsondata[index].filePath+"' onclick='downloadFile()'>Download</button>";
			} else if(jsondata[index].fileType == "dir"){
				td3.innerHTML = "<button filepath='"+jsondata[index].filePath+"' onclick='loadData2()'>Open Dir</button>";
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
