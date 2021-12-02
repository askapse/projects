function pointerindicator(n){
    var ind=document.getElementById("indicator").getElementsByClassName("pointer");
    for(i=0;i<ind.length;i++){
        ind[i].style.background="white";
    }
    ind[n-1].style.background="blue";
}

//variable declaration for globle use....
var content=$("#content");


//load contact block......
function loadcontact(n){
    content.load("./register/contact.html");
    pointerindicator(n);
}

//load personal information block....
function loadpersonalinfo(n){
    content.load("./register/personalinfo.html");
    pointerindicator(n);
}

// personalinfo();

//load address block....
function loadhomeadd(n){
    content.load("./register/homeaddress.html");
    pointerindicator(n);
}

// homeaddress();

//load hotel address block....
function loadhoteladd(n){
    content.load("./register/hoteladd.html");
    pointerindicator(n);
}

// hoteladdress();


//load password block....
function loadpasswordsetter(n){
    content.load("./register/password.html");
    pointerindicator(n);
}

//jason for gathering data from the user...
// var data={};


