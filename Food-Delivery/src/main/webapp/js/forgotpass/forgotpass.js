//check email id s write or not 
function emailval(email) {
	let em = email.val().trim();
	let domain = em.split("@")[1];
	if (domain === "gmail.com" || domain === "outlook.com" || domain === "yahoo.com") {
		email.css("background", "none");
		return true;
	} else {
		email.css("background", "rgba(238, 119, 103,0.3)");
		return false;
	}
}

function disabledAll(val){
	$("#email").prop("disabled",val);
	$("#sendpass").prop("disabled",val);
}

//reset password on click button

$("#sendpass").click(function() {
	
	$("#errormsg").text("");
	let email = $("#email");
	disabledAll(true);
	if (emailval(email)) {
		$.ajax({
			url:"./passrecovery",
			data: {
				"email": email.val().trim(),
				"usertype": usertype
			},
			dataType: "json",
			type: "POST",
			success: function(res) {
				if (res.result) {
					closer.click();
					alert("passwrod send to your regstered email...");
				} else {
					disabledAll(false);
					$("#errormsg").text(res.msg)					
				}
			},

			error: function() {
				$("#errormsg").text("Server error please try again later...");
				disabledAll(false);
			}
		});
	}else{
        $("#errormsg").text("Please check entered email is correct or not");
		disabledAll(false);
    }
});
