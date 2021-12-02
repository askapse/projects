//fields validations......... on click the button of the add dish
document.querySelector("#adddishbtn").onclick=()=>{
    //checker
    let chk=true;
    
    //dish name
    let dishname=document.getElementById("dishname");
    if(dishname.value==""){
        dishname.style.background="rgb(238, 156, 156)";
        chk=false;
    }else{
        dishname.style.background="none";
    }

    //file selector
    let imageselect=document.querySelector("#imageselect");
    if(imageselect.value==""){
        imageselect.style.background="rgb(238, 156, 156)";
        chk=false;
    }else if((imageselect.files.item(0).size/100)<=512){  //file size
        imageselect.style.background="rgb(238, 156, 156)";
        chk=false;
    }else{
        imageselect.style.background="none";        
    }

    

    //price validator....
    let price=document.querySelector("#price");
    if(price.value.match("[0-9]+\\.[0-9]{2}")){
        if(parseFloat(price.value)>1){
			price.style.background="none";
		}else{
			price.style.background="rgb(238, 156, 156)";
      		chk=false;
        	alert("Price can't less than one");
		}
		
    }else {
        price.style.background="rgb(238, 156, 156)";
        chk=false;
        alert("Please enter price like 0.00 format");
    }

	//file and data upload..........
	if(chk){
		let data=new FormData();
		data.append("imagefile",imageselect.files.item(0));
		data.append("dishname",dishname.value);
		data.append("price",price.value);
		
		$.ajax({
			url:"addnewdish",
			data:data,
			dataType:"json",
			type:"POST",
			contentType: false,
        	processData: false,
			success:(res)=>{				
				
				if(res.result){
					alert("dish added succesfully...");
					imageselect.value="";
					price.value="";
						dishname.value="";
					window.location.replace("./");
				}else
					alert(res.msg);
			},			
			error:()=>{
				alert("server error");	
			}			
		});			
	}	
}

document.getElementById("imageselect").onchange=()=>{
    let imageselect=document.getElementById("imageselect");
    if(imageselect.value.length==""){
        imageselect.value="";
    }else if(imageselect.files.item(0).name.endsWith(".png") || imageselect.files.item(0).name.endsWith(".jpg")){
		if((imageselect.files.item(0).size/1000)>512){
     	   	alert("File size have to less than 512 KB");
       	   	imageselect.value="";
   		}else{
			if((imageselect.files.item(0).size/1000)<50){
     	   		alert("File size have to greater than 50 KB");
       	   		imageselect.value="";
			}
        	return true;
    	}
	}else{
		alert("only .jpg and .png file are allowed")
		imageselect.value="";
	}
}

document.getElementById("price").onkeypress=(event)=>{
    let price=document.getElementById("price");
    if(event.key.match("[0-9\\.]")){
        return true;
    }else{
        return false;
    }
}


//load dish images....................
function loadDishImage(imgplace){
	imgplace.forEach((td)=>{
		let id=td.getAttribute("value");
		
		let dishimg=document.createElement("img");
		dishimg.setAttribute("src","getdishimage?id="+id);
		dishimg.setAttribute("alt","Image not loaded");
		td.append(dishimg);
	})	
	
}





//dish inforamtion loading 
function loadDishInfo(id,name,price){
	let loader=document.querySelector("tbody");
	let rmbtn=[];
	let imagetd=[];
	
	for(i=0;i<id.length;i++){
		let tr=document.createElement("tr");
		
		imagetd[i]=document.createElement("td");
		imagetd[i].setAttribute("value",id[i]);
		loader.append(tr);
		tr.append(imagetd[i]);
		
		let dn=document.createElement("td");
			dn.innerHTML=name[i];
		tr.append(dn);
		
		
		let dp=document.createElement("td");
			dp.innerHTML=price[i];
		tr.append(dp);
		
		
		//button to remove dish form the database.......		
		rmbtn[i]=document.createElement("button");
		rmbtn[i].setAttribute("value",id[i]);
		rmbtn[i].innerHTML="Remove";
		let rmtd=document.createElement("td");
			rmtd.append(rmbtn[i]);	
		tr.append(rmtd);		
	}
	
	
	
	rmbtn.forEach((rb)=>{
		rb.onclick=()=>{
			let id=rb.getAttribute("value");	
			$.ajax({
				url:"removemydish",
				data:{"id":id},
				type:"POST",
				dataType:"json",
				
				success:(res)=>{
					if(res.result){
						alert("Dish removed successfully...");
						rb.parentElement.parentElement.remove();
					}else{
						alert("Failed to remove dish...");
					}
				},
				
				error:()=>{
					alert("Unable to remove dish");
				}
				
			});
			
		}	
	})
	
	
	loadDishImage(imagetd);
}
























//get dish info from the server
function getDishes(){
	addMiddleLayer(true,"body");

	let dishid=[];
	let dishname=[];
	let dishprice=[];
	
	$.ajax({
		url:"getdishlist",
		type:"POST",
		dataType:"text",		
		data:{},
		
		success:(res)=>{
			if(res=="false"){
				alert("No dish added yet");
				addMiddleLayer(false);			
			}else{
				let jsonvr=JSON.parse(res);
				dishid=jsonvr.id;
				dishname=jsonvr.name;
				dishprice=jsonvr.price;	
				loadDishInfo(dishid,dishname,dishprice);
			}					
		},
		
		error:()=>{
			
		},
		
		complete:()=>{
			addMiddleLayer(false);
		}
	});
	
	
	
	
}


getDishes();





