 $(document).ready(function()
   		 	{
    			$("#buttonSubmit").click(function()
    			{
					//alert(document.getElementById("username").value + " " + document.getElementById("password").value);
					var token = login((document.getElementById("username").value, document.getElementById("password").value));
					if(token != "")
					{
						setCookie("token", token, 1);
						document.location.href="book.html";
					}
					else
					{
						alert("Error");
					}
    				//$.post( "myFunction", $("#myForm").serialize());
    			});
   			});