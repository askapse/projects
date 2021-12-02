
function hoteladdtoserver(chk) {
	//check result
	if (chk == -6) {
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

$("#hoteladdsubmit").click(function() {
	hoteladdtoserver(valadd());
});