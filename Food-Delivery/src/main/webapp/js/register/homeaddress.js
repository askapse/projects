var dist = $("#district");
var subdist = $("#subdist");
var city = $("#city");
var area = $("#area");
var home = $("#home");
var zipcode = $("#zipcode");
var btn = $("#submitadd")

//input validation 
dist.keypress(function(event) {
	return /^[a-zA-Z]+$/.test(event.key);
});

subdist.keypress(function(event) {
	return /^[a-zA-Z]+$/.test(event.key);
});

city.keypress(function(event) {
	return /^[a-zA-Z]+$/.test(event.key);
});

area.keypress(function(event) {
	return /^[a-zA-Z0-9 ]+$/.test(event.key);
});

home.keypress(function(event) {
	return /^[a-zA-Z0-9 ]+$/.test(event.key);
});

zipcode.keypress(function(event) {
	if (zipcode.val().length == 6) return false;
	return /^[0-9]+$/.test(event.key);
});


//disable all fields 
function disabledAll(val) {
	dist.prop("disabled", val);
	subdist.prop("disabled", val);
	city.prop("disabled", val);
	area.prop("disabled", val);
	home.prop("disabled", val);
	zipcode.prop("disabled", val);
	btn.prop("disabled", val);
}

//validate on submit
function valadd() {
	disabledAll(true);
	let chk = 0;
	if (dist.val().trim() === "") {
		dist.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		dist.css("background", "none");
		chk--;
	}

	if (subdist.val().trim() === "") {
		subdist.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		subdist.css("background", "none");
		chk--;
	}

	if (city.val().trim() === "") {
		city.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		city.css("background", "none");
		chk--;
	}

	if (area.val().trim() === "") {
		area.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		area.css("background", "none");
		chk--;
	}

	if (home.val().trim() === "") {
		home.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		home.css("background", "none");
		chk--;
	}

	if (zipcode.val().trim().length != 6) {
		zipcode.css("background", "rgba(238, 119, 103,0.3)");
		chk++;
	} else {
		zipcode.css("background", "none");
		chk--;
	}
	return chk;
}

function homeaddtoserver(chk) {
	var res = {};
	res.result = true;
	//check result
	if (chk == -6) {
		$("#errormsg").text("");

		regformdata.dist = dist.val().trim();
		regformdata.subdist = subdist.val().trim();
		regformdata.city = city.val().trim();
		regformdata.area = area.val().trim();
		regformdata.hname = home.val().trim();		
		regformdata.zipcode = zipcode.val().trim();
		loadpasswordsetter(4);

	} else {
		disabledAll(false);
		$("#errormsg").text("please check red fields");
	}
}

btn.click(function() {
	homeaddtoserver(valadd());
});