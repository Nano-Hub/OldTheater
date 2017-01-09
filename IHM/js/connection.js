 $(document).ready(function()
   		 	{
    			$("#buttonSubmit").click(function()
    			{
					var token = login(document.getElementById(username), document.getElementById(password));
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