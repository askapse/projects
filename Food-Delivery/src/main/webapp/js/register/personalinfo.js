var fname = $("#fname");
var lname = $("#lname");
var dob = $("#dob");
var checkbtn = $("#personalinfocheck");

//input validate
fname.keypress(function(event) {
	return /^[a-zA-Z ]+$/.test(event.key);
});

lname.keypress(function(event) {
	return /^[a-zA-Z ]+$/.test(event.key);
});


//disable all elements 
function disableAll(val) {
	fname.prop("disabled", val);
	lname.prop("disabled", val);
	dob.prop("disabled", val);
	checkbtn.prop("disabled", val);
}



checkbtn.click(function() {
	disableAll(true);
	let chk = 0;
	if (fname.val().trim() == "") {
		fname.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		fname.css("background", "none");
		chk--;
	}

	if (lname.val().trim() == "") {
		lname.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		lname.css("background", "none");
		chk--;
	}

	if (dob.val().trim() == "") {
		dob.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		dob.css("background", "none");
		chk--;
	}

	let dobval = dob.val().split("-");
    if (new Date().getFullYear() - dobval[0] >= 18 && dobval[1] >= 1 && dobval[1] <= 12 && dobval[2] >= 1 && dobval[2] <= 31) {
        dob.css("background", "none");
		chk--;
    } else {
        dob.css("background", "rgba(238, 119, 103,0.3)");
        alert("User have to 18 years complete...");
        chk++;
    }


	if (chk == -4) {
		$("#errormsg").text("");

		regformdata.fname = fname.val().trim();
		regformdata.lname = lname.val().trim();
		regformdata.dob = dob.val().trim();

		//regulate home and hotel address blocks....for customer and hotel registration....
		if (regformdata.usertype == "hotel") {
			loadhoteladd(3);
		} else {
			loadhomeadd(3);
		}
	} else {
		disableAll(false);
		$("#errormsg").text("please check red fields");
	}

});