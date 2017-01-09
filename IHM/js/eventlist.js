function readEvents()
{
	 $('#tableEvents').append('<tr onclick="goToBookSeat(1)" style="cursor: pointer"><td>aaa</td><td>bbb</td><td>ddd</td></tr>');
	 <!--$('#tableEvents').append('<tr onclick="goToBookSeat('+idEvent+')" style="cursor: pointer"><td>'+aaa+'</td><td>'+bbb+'</td><td>'+ddd+'</td></tr>');-->
}
 

function goToBookSeat(idEvent){
	alert(idEvent);
	setCookie("idEvent",idEvent,1);
	document.location.href="book.html";
}