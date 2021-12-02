//password fields variables....
var pass = document.getElementById("pass");
var repass = document.getElementById("repass");

//radio buttons fields...........
var pshowbtn = document.getElementById("showhidepass");
var rpshowbtn = document.getElementById("showhiderepass");


//password show hide button for setting password to the account....
pshowbtn.onchange = function() {
	if (pshowbtn.checked) {
		pass.setAttribute("type", "text");
	} else {
		pass.setAttribute("type", "password");
	}
};

rpshowbtn.onchange = function() {
	if (rpshowbtn.checked) {
		repass.setAttribute("type", "text");
	} else  {
		epass.setAttribute("type", "password");
	}
};



//function to validate password
function passvalidate(pass) {

	let d = document.getElementById("digit");
	let u = document.getElementById("uppercase");
	let s = document.getElementById("lowercase");
	let sm = document.getElementById("specialsimbol");
	let len = document.getElementById("length");

	let chk=0;

	if (pass.match(/[0-9]/g)) {
		d.style.color = "green";		
	} else {
		d.style.color = "red";
		chk++;
	}

	if (pass.match(/[a-z]/g)) {
		s.style.color = "green";		
	} else {
		s.style.color = "red";
		chk++;
	}

	if (pass.match(/[A-Z]/g)) {
		u.style.color = "green";		
	} else {
		u.style.color = "red";
		chk++;
	}

	if (pass.match(/[!@#$%^&*)(+=._-]/g)) {
		sm.style.color = "green";		
	} else {
		sm.style.color = "red";
		chk++;
	}

	if (pass.length < 8) {
		len.style.color = "red";
		chk++;
	} else {
		len.style.color = "green";		
	}


	if(chk>0){
		return false;
	}else{
		if(chk == 0){
			return true;
		}else{
			return false;
		}
	}	
}


pass.onfocus = function(event) {
	document.getElementById("passindicator").style.display = "flex";
	passvalidate((pass.value).toString());
}

pass.onblur = function(event) {
	document.getElementById("passindicator").style.display = "none";
}

pass.onkeypress = function(event) {
	passvalidate((pass.value + event.key).toString());
};


repass.onfocus = function(event) {
	document.getElementById("passindicator").style.display = "flex";
	passvalidate((repass.value).toString());
}

repass.onblur = function(event) {
	document.getElementById("passindicator").style.display = "none";
}

repass.onkeypress = function(event) {
	passvalidate((repass.value + event.key).toString());
};


//disable all fields
function disabledAll(val) {
	$("#pass").prop("disabled", val);
	$("#repass").prop("disabled", val);
	$("#submitdata").prop("disabled", val);
}

document.getElementById("submitdata").onclick = function() {   //submit all the data form input form the user for registering on the portal...
	disabledAll(true);
	if (passvalidate((repass.value).toString()) && passvalidate((pass.value).toString())) {
		if (pass.value === repass.value) {
			$("#errormsg").text("");
			
			regformdata.pass=pass.value;
			
			$.ajax({
				url: "./registeruser",
				data: regformdata,
				dataType: "json",
				type: "POST",
				success: function(res) {
					if (res.result) {						
						alert("Your registration is successfull...");
						closer.click();
						alert("Please login here to use the system...");
					}else{
						$("#errormsg").text(res.msg);
						disabledAll(false);
					}
				},

				error: function() {
					$("#errormsg").text("Server error please try again later");
					disabledAll(false);
				}

			});

		} else {
			$("#errormsg").text("mismatch passwords");
			disabledAll(false);
		}

	} else {

		if (pass.value === repass.value) {
			$("#errormsg").text("password not enough strong");
			disabledAll(false);
		} else {
			$("#errormsg").text("mismatch passwords and not enough strong too");
			disabledAll(false);
		}

	}
};