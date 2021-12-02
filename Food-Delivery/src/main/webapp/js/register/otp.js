var otp=$("#otp");
var vrotp=$("#verifyotp");
otpEntry(otp);

function disabledAll(val){
    otp.prop("disabled",val);
    vrotp.prop("disabled",val);
}

vrotp.click(function(){
    disabledAll(true);
    if(otp.val().length!=4){
        $("#errormsg").text("please enter all digits");
        disabledAll(false);
        return;
    }

	let p=/[0-9]{4}/;
	if(p.test(otp.val())){
		
    	$("#errormsg").text("");

     	$.ajax({
        	 url:"./otpvalidator",
         	data:{"otp":otp.val().trim()},
         	dataType:"json",
         	type:"POST",

	         success:function(res){
            	if(res.result){
					alert("OTP varification successfull...");
                	loadpersonalinfo(2);
            	}else{
	                $("#errormsg").text(res.msg);
    	            disabledAll(false);
        	    }
         	},

         	error:function(){
             	$("#errormsg").text("server error please try again later");
             	disabledAll(false);
         	}
     	});
	}else{
		$("#errormsg").text("You are entering invalid otp format");
	}
});

