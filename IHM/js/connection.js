function login()
{
    var email = document.getElementById("username").value;
	var psw = document.getElementById("password").value;
	
	$.get("http://localhost:8080/OldTheaterClient/theater/client/login?email="+email+"&password="+psw, function(data)
	{
		var response = "";
		
		for(var i = 0; i < data.length; i++)
			{
			response += data[i];
			}		
		if($.isNumeric(response))
		{	
			setCookie("id", response, 1);	
			document.location.href="eventlist.html";
		}
		else
		{
			alert(response); 
		}
		
	})
	.error(function() { 
		alert("Wrong combination."); 
	});
}
