function login()
{
    var email = document.getElementById("username").value;
	var psw = document.getElementById("password").value;
	
	$.get("	http://localhost:8080/OldTheaterClient/theater/client/login?email="+email+"&password="+psw, function(data)
	{
		var response = "";
		
		for(var i = 0; i < data.length; i++)
			{
			response += data[i];
			}
		alert(response);
		
		if(data.length == 1)
		{	
			setCookie("id", response, 1);	
			document.location.href="index.html";
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
