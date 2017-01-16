function readEvents()
{
	 $('#tableEvents').append('<tr onclick="goToBookSeat(1)" style="cursor: pointer"><td>aaa</td><td>bbb</td><td>ddd</td></tr>');
	 //$('#tableEvents').append('<tr onclick="goToBookSeat('+idEvent+')" style="cursor: pointer"><td>'+aaa+'</td><td>'+bbb+'</td><td>'+ddd+'</td></tr>');
	 	
	$.get("	http://localhost:8080/OldTheaterClient/theater/client/eventList", function(data)
	{
		var response = "";
		
		for(var i = 0; i < data.length; i++)
			{
			response += data[i];
			}
		alert(response + " " + response.length);
		
		if(data.length == 1)
		{	
			setCookie("id", response, 1);	
			document.location.href="index.html";
		}
		else
		{
			alert(response); 
		
	})
	.error(function() { 
		alert("Wrong combination."); 
	});
}

}
 

function goToBookSeat(idEvent){
	alert(idEvent);
	setCookie("idEvent",idEvent,1);
	document.location.href="book.html";
}

