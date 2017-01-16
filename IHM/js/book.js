	var count=0;
	
	function loadSeats()
	{
			//Siege du mec siege / siege  reserevre
			$.get("http://localhost:8080/OldTheaterClient/theater/client/bookedSeats?idUser="+getCookie("id")+"&idEvent="+getCookie("idEvent"), function(data)
		{
			var response = "";
			
			for(var i = 0; i < data.length; i++)
				{
					response += data[i];
				}		
				
			var splittedSpecialSeats = response.split("/");
			
			//str.substring(1, 4)
			count = splittedSpecialSeats[1];
			var splittedSeats = splittedSpecialSeats[0].split("-");
			
			for(var i = 0; i < splittedSeats.length; i++)
			{
				var cat = splittedSeats[i].substring(0, 1);
				var number =  splittedSeats[i].substring(1);
				$("#"+cat+number).removeClass("btn-success");
				$("#"+cat+number).addClass("btn-danger");
				$("#"+cat+number).prop("disabled", true);
			}			
		})
		.error(function() { 
			alert("Problème de connection."); 
		});
	}
	
	

	function categoryA()
	{
		$("#root").append('<h2>Category A</h2>');
		for(var i = 1 ; i <= 25 ; i++)
		{
			$("#root").append('<span><button id="A'+i+'" onclick=book(A'+i+') class="btn btn-success btnBook" data-color="success" >' + i + ' </button></span> ');
		}
	}

	function categoryB()
	{
		$("#root").append('<h2>Category B</h2>');
		for(var i = 1 ; i <= 45 ; i++)
		{
			$("#root").append('<span  ><button onclick=book(B'+i+') id="B'+i+'" class="btn btn-success btnBook" data-color="success" >' + i + ' </button></span> ');
		}
	}

	function categoryC()
	{
		$("#root").append('<h2>Category C</h2>');
		for(var i = 1 ; i <= 100 ; i++)
		{
			$("#root").append('<span ><button onclick=book(C'+i+') id="C'+i+'" class="btn btn-success btnBook" data-color="success" >' + i + ' </button></span> ');
		}
	}
function categoryD()
	{
		$("#root").append('<h2>Category D</h2>');
		for(var i = 1 ; i <= 500 ; i++)
		{
			$("#root").append('<span><button onclick=book(D'+i+') id="D'+i+'" class="btn btn-success btnBook" data-color="success" >' + i + ' </button></span> ');
		}
	}
	
	
categoryA();
categoryB();
categoryC();
categoryD();

function book(id)
{
	if(count<=3)
	{
		var values = $("#bookArea").val();
		values+=id.id+"  ";
		$("#bookArea").val(values);
		$("#"+id.id).removeClass("btn-success");
		$("#"+id.id).addClass("btn-danger");
		$("#"+id.id).prop("disabled", true);
		count++;
	}
	else
	{
		alert("Vous ne pouvez réserver que 4 places maximums par compte. Cliquer sur Annuler pour modifier votre commande.");
	}
}

function cancelBooking()
{
	count=0;
	var values = $("#bookArea").val();
	var v = values.split("  ");
	for(var i = 0 ; i < v.length-1 ; i++)
	{
		$("#" +v[i]).removeClass("btn-danger");
		$("#" +v[i]).addClass("btn-success");
		$("#"+v[i]).prop("disabled", false);
	}
	$("#bookArea").val("");
}


function valid()
{
	var values = $("#bookArea").val();
	var v = values.split("  ");
	var result="";
	for(var i = 0 ; i < v.length-1 ; i++)
	{
		if(i===v.length-2)
			result+=v[i];
		else
			result+=v[i]+"-";
	}
	/*var datas="?book="+result;
	
	$.post( "ajax/test.html"+datas, function( data ) 
	{
		  $( ".result" ).html( data );
	});*/
	//lockSeats(getCookie("idEvent"), result, getCookie("token"));
	
	$.get("http://localhost:8080/OldTheaterClient/theater/client/lockSeats?idUser="+getCookie("id")+"&idEvent="+getCookie("idEvent")+"&seats="+result, function(data)
	{
		var response = "";
		
		for(var i = 0; i < data.length; i++)
			{
				response += data[i];
			}
			
		if($.isNumeric(response))
		{	
			setCookie("price", response, 0.1);	
			document.location.href="payment.html";
		}
		else
		{
			alert(response); 
		}
				
	})
	.error(function() { 
		alert("Problème de connection."); 
	});
	
	
	cancelBooking();
}