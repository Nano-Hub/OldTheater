
    function login()
    {
        var name = document.getElementById("name").value;
            var email = document.getElementById("username").value;
            var psw = document.getElementById("password").value;
            
            $.post("http://localhost:8080/OldTheaterClient/theater/client/register?email="+email+"&password="+psw+"&name="+name, function(data)
            {

            })
            .error(function() { 
                    alert("You are already in our databases"); 
            });
    }