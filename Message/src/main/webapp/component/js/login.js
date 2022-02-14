function login() {
	let username = document.getElementById("userid");
	let password = document.getElementById("password");
	let msg = document.getElementById("msg");

	//checking username and password
	if (username.value.length == 0 || password.value.length == 0) {
		msg.innerHTML = "Username or Password is missing";
	} else {
		document.getElementById("login").submit();
	}

	//hiding message after some time
	setInterval(function() {
		msg.innerHTML = "";
	}, 10000);
}


function err(ermsg) {
	let msg = document.getElementById("msg");
	msg.innerHTML = ermsg;


	//hiding after some time
	setInterval(function() {
		msg.innerHTML = "";
	}, 10000);
}