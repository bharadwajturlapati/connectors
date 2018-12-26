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
		//Populate the current Directory to upload.
		uploadfile();
		
		// Load data to the current view.
		var url = "/services/api/v2/getcontents";
		var currentURL = window.location.href;
		if(!(currentURL.endsWith("/services/api/v2/viewcontents")|| currentURL.endsWith("/services/api/v2/viewcontents/"))){
			url = url+"${rootpath}";
		}
		fetch(url, {
			method : "GET",
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			filldata(data["newInstance"]["paths"]);
		});
	}
	
	function loadData2(){
		var path = event.target.getAttribute("filepath");
		window.location.href = "/services/api/v2/viewcontents"+path;
	}
	
	function downloadFile(){
		var path = event.target.getAttribute("filepath");
		var filename = event.target.getAttribute("filename");
		var payload = {
			"path" : path
 		};
		fetch("/services/api/v2/downloadfile", {
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
	
	function uploadfile(){
		var currentURL = window.location.href;
		var urllen = currentURL.length;
		// length of "viewcontents" is 12
		var startIdx = currentURL.indexOf("viewcontents")+12;
		var endIdx = urllen;
		var currentDir = currentURL.substring(startIdx, endIdx);
		
		document.getElementById('pathToUpload').value = currentDir;
	}
</script>
</head>
<body onload="loadData()">
	<div class="container">
		<h2>List View</h2>
		<form action="/services/api/v1/uploadfile" method="post" enctype="multipart/form-data">
		upload File: <input type="file" name="file"
			id="file"> 
			<input type="submit" value="Upload file"
			name="submit">
			<input type="hidden" id="pathToUpload" name="pathToUpload" value="">
		</form>
		
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