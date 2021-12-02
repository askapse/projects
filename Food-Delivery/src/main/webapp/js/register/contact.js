var mbele=$("#mobile");
var emele=$("#email");
var vbtn=$("#verifycontact");

mobileinput(mbele);

//element disabled
function allDisabled(val){
    mbele.prop("disabled",val);
    emele.prop("disabled",val);
    vbtn.prop("disabled",val);
}

//validations.....
function mobileinput(mobile){
    mobile.keypress(function(event){

        if(mobile.val().trim().length==10){
            return false;
        }  

        if(event.charCode>=48 && event.charCode<=57)         
            return true;
        else 
            return false;        
    });
}

function mobile(mobile){
    if(mobile.val().trim().length<10){
        mobile.css("background","rgba(238, 119, 103,0.3)");
        return false;
    }
    else if(mobile.val().trim().length==10){
        mobile.css("background","none");
        return true;
    }
}

function email(email){
    let em=email.val().trim();
    let domain=em.split("@")[1];
    if(domain==="gmail.com" || domain==="outlook.com" || domain==="yahoo.com"){
        email.css("background","none");
        return true;
    }else{
        email.css("background","rgba(238, 119, 103,0.3)");
        return false;
    }      
}


//otp valodation on input 
function otpEntry(otp){
    otp.keypress(function(event){
        if(otp.val().trim().length==4){
            return false;
        }

        if(event.charCode>=48 && event.charCode<=57 ){
            return true;
        }else return false;
    });
}

//mobile and email to server
function contacttoserver(mobile,email){
     $.ajax({
         url:"./contacttoemail",
         data:{"mobile":mobile,"email":email,"usertype":regformdata.usertype},
         type:"POST",
         dataType:"json",
         success:function(res){
             if(res.result){
                 $("#verify").load("./register/otpentry.html");
                //regformdata.mobile=mbele.val().trim();
                //regformdata.email=emele.val().trim();
                $("#errormsg").text("Enter OTP and verify");
             }else{
				$("#errormsg").text(res.msg);
				allDisabled(false);					
			}
         },
         error:function(){
            $("#errormsg").text("Server not responding...");
			allDisabled(false);			
         }
     }); 
}

//click button to verify email and mobile with server and send the email for otp varification via the email id....
vbtn.click(function(){
    allDisabled(true);
    if(mobile(mbele) && email(emele)){
            contacttoserver(mbele.val().trim(),emele.val().trim());                   
    }else{
        allDisabled(false);
        $("#errormsg").text("Please check the red fields");
    } 

});






