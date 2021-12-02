function addMiddleLayer(val,select) {
    if (val) {
        let layer = document.createElement("div");
        layer.style.zIndex = 50;
        layer.style.position = "absolute";
        layer.style.top = 0;
        layer.style.left = 0;
        layer.style.width = "100%";
        layer.style.height = "100%";
        layer.style.background = "rgba(107, 109, 106, 0.445)";        
        layer.setAttribute("id","midlayer");
        document.querySelector(select).appendChild(layer);

        let loading=document.createElement("div");
        loading.setAttribute("id","loading");
        layer.appendChild(loading);

    }else{
        document.getElementById("midlayer").remove();
    }
}


// addMiddleLayer(true,"body");



//logout
document.querySelector("#logout").onclick=()=>{
	$.ajax({
		url:"logout",
		data:{},
		dataType:"json",
		type:"POST",
		
		success:(res)=>{
			if(res.result){
				window.location.replace("./index.html");	
			}else{
				alert("Logout failed");	
			}
		},
		
		error:()=>{
			alert("Failed operation");
		}
	});
}






















//document.getElementById("srch").onclick=()=>{
   // addMiddleLayer(true,"body");
    //let srchtext=document.querySelector("#searchtext");
  //  if(srchtext.value==""){
      //  return;   
     //   addMiddleLayer(false);
    //}else if(srchtext.value.match(/[a-zA-Z0-9 ]+$/)){
        
        //ajax through to search string and get the result for search text...


    //}else{
        //alert("Special character not allowed to search");
       // addMiddleLayer(false);
    //}
//}


$("#change").load("./customer/home.html");

document.querySelector("#homebtn").onclick = function () {
    $("#change").load("./customer/home.html");
}

document.querySelector("#profilebtn").onclick = () => {
    $("#change").load("./customer/profile.html");
}

document.querySelector("#orderbtn").onclick = () => {
    $("#change").load("./customer/orders.html");
}
