//user verifier for logged in user check and redirect
function userlogincheck(){
	$.ajax({
		url:"loginchecker",
		type:"POST",
		dataType:"json",
		data:{},
			
		success:(res)=>{
			if(res.result){
				if(res.user=="hotel"){
					$("body").load("./hotel/index.html");
				}else if(res.user=="customer"){				
					$("body").load("./customer/index.html");
				}
			}else{
				return;
			}
		},
		
		error:()=>{
			alert("Failed operation....");
		}
			
	});
}

userlogincheck();


















//starage for reg form fill up....
var regformdata={};

//usertype detector 
var usertype=null;

//diabled all page only popup active....
function disableAllDoc(val){
    if(val)
        document.getElementById("layer").style.display="block";
    else
        document.getElementById("layer").style.display="none";
}

//navigation bar clicks

var reglist=document.getElementById("reglist");
    document.getElementById("regtext").onclick=function(){
    	let	 regbt=document.getElementById("regtext");
	    if(reglist.style.display=="block"){
            reglist.style.display="none";
        }else{
            reglist.style.display="block";
        }
    };

//open page click on costomer registration
var container=$("#container");
var changable=$("#changable");
document.getElementById("creg").onclick=function(){
    reglist.style.display="none";
    disableAllDoc(true);
    changable.css("display","block");
    container.load("./register/customer.html");
    regformdata.usertype="customer";
};

//open page click on hotel registration
document.getElementById("hreg").onclick=function(){
    reglist.style.display="none";
    disableAllDoc(true);
    changable.css("display","block");
    container.load("./register/hotel.html");
    regformdata.usertype="hotel";
};

// close container of the containt
var closer=$(".closer");

closer.click(function(){
    changable.css("display","none");
    disableAllDoc(false);
    container.text("");
    regformdata={};
});

//show hide password
function showpass(check,passfield){
    if(check.checked){           
        document.getElementById(passfield).setAttribute("type","text");
    }else{            
        document.getElementById(passfield).setAttribute("type","password");
    }
}

var cshowhidepass=document.getElementById("cshowhidepass");
    cshowhidepass.onclick=function(){
        showpass(cshowhidepass,"cpassword");
    };

var hshowhidepass=document.getElementById("hshowhidepass");
    hshowhidepass.onclick=function(){
        showpass(hshowhidepass,"hpassword");
    };    


//show hide section of login 
var clgsec=document.getElementById("clgsec");
var hlgsec=document.getElementById("hlgsec");

var csecinput=document.getElementById("clogininput");
var hsecinput=document.getElementById("hlogininput");

    //when click on customer title hide the hotel sec and opens customer input sec
    clgsec.onclick=function(){
        csecinput.style.display="block";
        hsecinput.style.display="none";        
    };

    hlgsec.onclick=function(){
        csecinput.style.display="none";
        hsecinput.style.display="block";        
    };


//disable fields on operation is being done...
function disabledAll(f1,f2,btn,op){
    f1.prop("disabled",op);
    f2.prop("disabled",op);
    btn.prop("disabled",op);
}


//login on click 

$("#hloginbtn").click(function(){
    let username=$("#husername");
    let password=$("#hpassword");
	
	//disable all fields....    
    disabledAll(username,password,$("#hloginbtn"),true);
	
    if(password.val()=="")
        password.css("background","rgba(238, 119, 103,0.3)");
    else
        password.css("background","none");

    if(username.val()=="")
        username.css("background","rgba(238, 119, 103,0.3)");
    else 
        username.css("background","none");

     $.ajax({
         url:"./hotellogin",
         data:{
             "username":username.val().trim(),
             "pass":password.val().trim(),
			 "user":"hotel"
         },
         dataType:"json",
         type:"POST",

         success:function(res){
			if(res.result){
				$("body").load("./hotel/index.html");			
			}else{
				alert(res.msg);
				disabledAll(username,password,$("#hloginbtn"),false);
			}
         },

         error:function(){
			alert("server error occurs please try again...");
			disabledAll(username,password,$("#hloginbtn"),false);
         }
     });
}); 


//customer login
$("#cloginbtn").click(function(){
    let username=$("#cusername");
    let password=$("#cpassword");

    //disable all fields 
    disabledAll(username,password,$("#cloginbtn"),true);

    if(password.val()=="")
        password.css("background","rgba(238, 119, 103,0.3)");
    else
        password.css("background","none");

    if(username.val()=="")
        username.css("background","rgba(238, 119, 103,0.3)");
    else 
        username.css("background","none");



     $.ajax({
         url:"./customerlogin",
         data:{
             "username":username.val().trim(),
             "pass":password.val().trim(),
			 "user":"customer"
         },
         dataType:"json",
         type:"POST",

         success:function(res){
			if(res.result){
				$("body").load("./customer/index.html");				
			}else{
				alert(res.msg);
				disabledAll(username,password,$("#cloginbtn"),false);
			}
         },

         error:function(){
			alert("server error occurs please try again...");
			disabledAll(username,password,$("#cloginbtn"),false);
         }
     });
}); 


//open customer password reset 
document.getElementById("cpassforgot").onclick=function(){
    changable.css("display","block");
    disableAllDoc(true);
    container.load("./forgotpass/forgotpass.html");
    usertype="customer";
};


//open hotel password reset
document.getElementById("hpassforgot").onclick=function(){
    changable.css("display","block");
    disableAllDoc(true);
    container.load("./forgotpass/forgotpass.html");
    usertype="hotel";
};








