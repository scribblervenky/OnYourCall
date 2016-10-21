function loadDoc() {
	var name =document.getElementById("Name").value;
	var mail =document.getElementById("Mail").value;
	var date =document.getElementById("Date").value;
	var data= {Name:name,Mail:mail,Date:date};
	var ajaxData = JSON.stringify(data);
	$.getJSON('ajax', {
        data    : ajaxData
      }, function(jsonResponse) {
    	  console.log(jsonResponse);
    	  });
	}