//load profile from the server to the page
function loadprofile(){
	addMiddleLayer(true,"body");
	$.ajax({
		url:"gethotelprofile",
		data:{},
		dataType:"json",
		type:"POST",
		
		success:(res)=>{
			if(res.result){
				document.querySelector("#fname").value=res.fname;
				document.querySelector("#lname").value=res.lname;
				document.querySelector("#dob").value=res.dob;
				document.querySelector("#hotelname").value=res.hotelname;
				document.querySelector("#dist").value=res.dist;
				document.querySelector("#subdist").value=res.subdist;
				document.querySelector("#city").value=res.city;
				document.querySelector("#area").value=res.area;
				document.querySelector("#zipcode").value=res.zipcode;
				document.querySelector("#mobile").value=res.mobile;
				document.querySelector("#email").value=res.email;
			}else{
				alert(res.msg);
			}
		},
		
		error:()=>{
			alert("failed to load profile from server");
		},
		
		complete:()=>{
			addMiddleLayer(false);
		}
		
	});
}

loadprofile();









//update btn click event... for updating information..........
document.querySelector("#updateinfobtn").onclick = () => {
    addMiddleLayer(true,"body");
    let chk = true;
    //.......first name validation.....
    let fname = document.getElementById("fname");
    if (fname.value == "") {
        fname.style.background = "rgb(238, 156, 156)";
        chk = false;
    } else {
        fname.style.background = "none";
    }

    //.............last name valodation..........
    let lname = document.getElementById("lname");
    if (lname.value == "") {
        lname.style.background = "rgb(238, 156, 156)";
        chk = false;
    } else {
        lname.style.background = "none";
    }

    //...........dob validation..........
    let dob = document.getElementById("dob");
    let dobval = dob.value.split("-");
    if (new Date().getFullYear() - dobval[0] >= 18 && dobval[1] >= 1 && dobval[1] <= 12 && dobval[2] >= 1 && dobval[2] <= 31) {
        dob.style.background = "none";
    } else {
        dob.style.background = "rgb(238, 156, 156)";
        alert("User have to 18 years complete...");
        chk = false;
    }


    //hotel name validation...........
    let hname = document.getElementById("hotelname");
    if (hname.value == "") {
        chk = false;
        hname.style.background = "rgb(238, 156, 156)";
    } else {
        hname.style.background = "none";
    }


    //dist validation.............
    let dist = document.getElementById("dist");
    if (dist.value == "") {
        chk = false;
        dist.style.background = "rgb(238, 156, 156)";
    } else {
        dist.style.background = "none";
    }


    //subdist validation..........
    let subdist = document.getElementById("subdist");
    if (subdist.value == "") {
        chk = false;
        subdist.style.background = "rgb(238, 156, 156)";
    } else {
        subdist.style.background = "none";
    }


    //city validation
    let city = document.getElementById("city");
    if (city.value == "") {
        chk = false;
        city.style.background = "rgb(238, 156, 156)";
    } else {
        city.style.background = "none";
    }


    //area validation............
    let area = document.getElementById("area");
    if (area.value == "") {
        chk = false;
        area.style.background = "rgb(238, 156, 156)";
    } else {
        area.style.background = "none";
    }


    //zipcode validation..............
    let zipcode = document.getElementById("zipcode");
    if (zipcode.value.match("[0-9]{6}")) {
        zipcode.style.background = "none";
    } else {
        chk = false;
        zipcode.style.background = "rgb(238, 156, 156)";
    }

    //mobile validation..........
    let mobile = document.getElementById("mobile");
    if (mobile.value.match("[6-9]{1}[0-9]{9}")) {
        mobile.style.background = "none";
    } else {
        chk = false;
        mobile.style.background = "rgb(238, 156, 156)";
    }
    //check status of the validation and grab details for the update....
    if (chk) {
		//request to update info..........
		$.ajax({
			url:"updateprofile",
			data:{
				"fname":fname.value,
				"lname":lname.value,
				"dob":dob.value,
				"hname":hname.value,
				"dist":dist.value,
				"subdist":subdist.value,
				"city":city.value,
				"area":area.value,
				"zipcode":zipcode.value,
				"mobile":mobile.value
			},
			dataType:"json",
			type:"POST",
			
			success:(res)=>{
				if(res.result){
					alert("Profile updated successfully");
				}else{
					alert("Profile updated failed");	
				}				
			},
			
			error:()=>{
				alert("Server failed to update prifile...");
			},
			
			complete:()=>{
				addMiddleLayer(false);
			}
			
		});	    
    } else {
        alert("Please check red fields ...");
        addMiddleLayer(false);    	
    }
}




//email otp verifier........
function loadotpverifier(){
    document.querySelector("#emailupdate").disabled=true;
    document.querySelector("#email").disabled=true;
    let otpinput=document.createElement('input');
    otpinput.setAttribute("id","otp");
    otpinput.setAttribute("onpaste","return false");
    otpinput.onkeypress=(event)=>{
        if(otpinput.value.length==4){
            return false;
        }else if(event.key.match("[0-9]")){
            return true;
        }else{
            return false;
        }

    }
    otpinput.style.width="40px";
    otpinput.setAttribute("placeholder","OTP");
    otpinput.setAttribute("autocomplete","off");
    otpinput.style.borderBottom="0.5px solid black";
    otpinput.style.margin="5px 20px 5px 0";


    let otpvbtn=document.createElement("button");
    otpvbtn.innerHTML="Verify";
    otpvbtn.margin="5px 0 0 0";
	
    let emailu=document.getElementById("updateemail");

    emailu.appendChild(otpinput);
    emailu.appendChild(otpvbtn);

    addMiddleLayer(false);

    otpvbtn.onclick=()=>{
        addMiddleLayer(true,"body");
         $.ajax({
             url:"otpverifier",
             data:{"otp":otpinput.value},
             dataType:"json",
             type:"POST",

             success:function(res){
					if(res.result){
						$.ajax({
							url:"updateemail",
							data:{},
							dataType:"json",
							type:"POST",
							
							success:(ress)=>{
								if(ress.result){
									alert("Email updated successfully");
									otpinput.remove();
									otpvbtn.remove();
									document.querySelector("#emailupdate").disabled=false;
    								document.querySelector("#email").disabled=false;
								}else{
									alert("email updation failed");
								}
							},
							
							error:()=>{
								alert("Email not updated please try again");
							}
							
						});
					}else{
						alert("wrong otp try with correct otp");
					}
             },

             error:function(){
					alert("error while validating otp to the server");
             },
			
			complete:()=>{
				addMiddleLayer(false);
			}
         });
    }

}



//checking email and varify using otp.........
document.getElementById("emailupdate").onclick = () => {
    addMiddleLayer(true,"body");
    let email = document.getElementById("email");
    if (email.value.match("[a-zA-Z0-9]+@{1}[a-z]+\\.[a-z]+$")) {
        email.style.background = "none";

        //send email for otp..............

        $.ajax({
             url:"otptoemail",
             type:"POST",
             dataType:"json",
             data:{"email":email.value},
             success:function(res){
                    if(res.result){
                       loadotpverifier();
                    }else{
                        alert(res.msg);
                    } 
             },

             error:function(){
					alert("server unable to send otp");
             },

			 complete:()=>{
				addMiddleLayer(false);
			}
         });


    } else {
        email.style.background = "rgb(238, 156, 156)";
        alert("Please Enter Valid Email...");
        addMiddleLayer(false);
    }
}



//onkey press events for entry fields.......

//first name
document.getElementById("fname").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z ]"))
        return true;
    return false;
}

//last name
document.getElementById("lname").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z ]"))
        return true;
    return false;
}

//hotel name
document.getElementById("hotelname").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z ]"))
        return true;
    return false;
}

//district
document.getElementById("dist").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z]"))
        return true;
    return false;
}


//sub-district
document.getElementById("subdist").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z]"))
        return true;
    return false;
}

//city
document.getElementById("city").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z]"))
        return true;
    return false;
}

//area
document.getElementById("area").onkeypress = (event) => {
    if (event.key.match("[a-zA-Z ]"))
        return true;
    return false;
}

//zipcode
document.getElementById("zipcode").onkeypress = (event) => {
    if (document.getElementById("zipcode").value.length == 6) {
        return false;
    } else if (event.key.match("[0-9]"))
        return true;
    return false;
}


//zipcode
document.getElementById("mobile").onkeypress = (event) => {
    if (document.getElementById("mobile").value.length == 10) {
        return false;
    } else if (event.key.match("[0-9]"))
        return true;
    return false;
}

//function to validate password
function passvalidate(pass) {

	let chk=0;

	if (!pass.match(/[0-9]/g)) {
		chk++;
	}

	if (!pass.match(/[a-z]/g)) {
		chk++;
	}

	if (!pass.match(/[A-Z]/g)) {
		chk++;
	}

	if (!pass.match(/[!@#$%^&*)(+=._-]/g)) {
		chk++;
	}

	if (pass.length < 8) {		
		chk++;
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

//password change 
var passchng = document.createElement("div");
	passchng.innerHTML = "Change Password";
	passchng.style.color = "navy";
	passchng.style.cursor = "pointer";
	passchng.style.textAlign = "center";
	passchng.style.margin = "20px 0 ";
	
	
	//onclick event
	passchng.onclick=()=>{
		let pass = prompt("Enter new password :");
			if(passvalidate(pass)){
				addMiddleLayer(true,"body");
				$.ajax({
					url:"hpassupdate",
					type:"POST",
					dataType:"json",
					data:{"pass":pass},
					
					success:(res)=>{
						if(res.result){
							alert("Password updated successfully");
						}else if(res.result ==  "reload"){
							window.location.replace("./");
						}else{
							alert(res.msg);
						}
					},
					
					error:()=>{
						alert("server not responding please try again");
					},
					
					complete:()=>{
						addMiddleLayer(false);	
					}					
				});
			}else{
				alert("You entered password not meet the requirements\n\nA-Z a-z 0-9 special character");
			}
	};
	
	
	



document.querySelector("#profile").append(passchng);