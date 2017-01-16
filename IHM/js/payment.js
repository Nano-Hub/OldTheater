 $(document).ready(function()
   		 	{		
				var price = document.getElementById("priceInput");
				price.value = getCookie("price");
   			});
	
	function bookSeatsJS()
	{

	$.get("http://localhost:8080/OldTheaterClient/theater/client/bookSeats?idUser="+getCookie("id")+"&idEvent="+getCookie("idEvent"), function(data)
	{
		var response = "";
		
		for(var i = 0; i < data.length; i++)
			{
				response += data[i];
			}
		
			alert(response); 			
	})
	.error(function() { 
		alert("Problème de connection."); 
	});
	
	}

	