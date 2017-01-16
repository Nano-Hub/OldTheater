function readEvents()
{
	 //$('#tableEvents').append('<tr onclick="goToBookSeat(1)" style="cursor: pointer"><td>aaa</td><td>bbb</td><td>ddd</td></tr>');
	 //$('#tableEvents').append('<tr onclick="goToBookSeat('+idEvent+')" style="cursor: pointer"><td>'+aaa+'</td><td>'+bbb+'</td><td>'+ddd+'</td></tr>');
	 	
	$.get("	http://localhost:8080/OldTheaterClient/theater/client/eventList", function(data)
	{
		var response = "";
		//id name date category
		for(var i = 0; i < data.length; i++)
			{
			response += data[i];
			}
			
		var splittedEvent = response.split("/");
		var splittedInfos;

		for(var i = 0; i < splittedEvent.length; i++)
			{
			splittedInfos= splittedEvent[i].split(",");
			$('#tableEvents').append('<tr onclick="goToBookSeat('+splittedInfos[0]+')" style="cursor: pointer"><td>'+splittedInfos[1]+'</td><td>'+splittedInfos[2]+'</td><td>'+splittedInfos[3]+'</td></tr>');
			setCookie("eventCat",splittedInfos[3], 1);
			}
	})
	.error(function() { 
		alert("Problème de connection."); 
	});
}


function goToBookSeat(idEvent){
	setCookie("idEvent",idEvent,1);
	document.location.href="book.html";
}

