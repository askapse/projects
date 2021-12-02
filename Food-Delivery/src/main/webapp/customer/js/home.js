var totalbill=0.00;
var dishcounter=0;



function orderPlacer(id,cnt,hotelid){
	addMiddleLayer(true,"#disheslist");
	let dishes=new Array();
	let count=new Array();
	
	
	for(i=0;i<id.length;i++){
		if(id[i].checked){
			dishes.push(id[i].getAttribute("value"));
			count.push(cnt[i].getAttribute("value"));
		}
	}
	
	if(dishes.length == 0 || count.length == 0){
		alert("You have to select atleast one dish");
		addMiddleLayer(false);	
		document.querySelector("#ordplacer").disabled=false;
	}else{
		let data={"ids":"["+dishes+"]","count":"["+count+"]","hotel":hotelid};
		console.log(data);
		
		$.ajax({
			url:"placeorder",
			data:data,
			dataType:"json",
			type:"POST",
			
			success:(res)=>{
				if(res.result){
					alert("Order placed successfully");
					document.querySelector(".closer").click();					
				}else{
					alert(res.msg);
					
				}
			},
			
			error:()=>{
				alert("Failed Order Placing");					
			},
			
			complete:()=>{
				addMiddleLayer(false);
				document.querySelector("#ordplacer").disabled=false;
			}			
			
		});
		
	}
}



//loaddish image after loading all details of the dishes
function loadDishimg(dish,dimg){
	i=0;
	dimg.forEach((cimg)=>{
		let img=document.createElement("img");
			img.setAttribute("src","dishImage?dishid="+dish[i]);
			img.setAttribute("alt","Dish Image");
			
		cimg.append(img);
					
			i+=1;
	})
}





//dish priceing add to the total billing and remove it from too by agruments
function addDishToTotal(price,status){
	let prce=parseFloat(price);
	let tbill=document.querySelector(".bill");
	if(status){
		totalbill+=prce;
		tbill.innerHTML=totalbill;
	}else if(!status){
		totalbill-=prce;
		
		if(totalbill == 0){
			tbill.innerHTML="0.00";
		}else{
			tbill.innerHTML=totalbill;
		}
		
		
		
	}
}









//load hotel dishes from the server with all of the stuff details...
function loadDishes(loader,dishid,name,price,hotelid){
	//set total bill to zero
	document.querySelector(".bill").innerHTML="0.00";
	
	let d=dishid.length;
	
	let dishselector=new Array();
	let dplus=[];
	let dminus=[];
	let dishcount=new Array();
	
	let dishimg=[];
	
	//make all the dish cards
	for(i=0;i<d;i++){
		let dish=document.createElement("div"); //main dish card
			dish.setAttribute("class","dish");
			
		//dish image 
		let dimg=document.createElement("div");
			dimg.setAttribute("class","dishimg");
			dishimg[i]=dimg; //add to array
			//appending dish image container to the dish card
			dish.append(dimg);
			
			
		//dish info section of the dish card
		let dishinfo=document.createElement("div");
			dishinfo.setAttribute("class","dishinfo");
			
			//dish name to the dishinfo section....
			let dishname=document.createElement("h3");
				dishname.setAttribute("class","dishname");
				dishname.innerHTML=name[i];
				
				//append dishname to the dish info
				dishinfo.append(dishname);	
				
			//price of the dish 
			let prc=document.createElement("h5");
				prc.setAttribute("class","price");		
				prc.setAttribute("value",price[i]);
				prc.innerHTML="Price  :";
				prc.append(price[i]);
				
				
				//append price to the dish info
				dishinfo.append(prc);
				
			//selection of the dish and quantity
			// rrr section as a continer
			let rrr=document.createElement("div");
				rrr.setAttribute("class","rrr");
				
				//quantity of the dish setting after selection of the dish...
				let quant=document.createElement("div");
					quant.setAttribute("class","quantity");
					
					//minus dish qunatity
					let min=document.createElement("div");
						min.setAttribute("class","minus arrow");
						min.style.display="none";
						min.innerHTML="<";
						
						//-1 dish 
						min.onclick=()=>{
							let qnt = parseInt(dishq.getAttribute("value"));
							if(qnt>1){							
								qnt-=1;
								dishq.setAttribute("value",qnt.toString());
								addDishToTotal(prc.getAttribute("value"),false);
							}							
						}																												
						
						dminus[i]=min;
						
						//append min to the quanti
						quant.append(min);
			
			
					//dish quantity input field
					let dishq=document.createElement("input");
						dishq.setAttribute("class","dishq");
						dishq.setAttribute("disabled","disabled");
						dishq.setAttribute("value","0");						
						
						
						dishcount[i]=dishq; //add to array....
						
						//appending to quantity
						quant.append(dishq);
			
					//plus dish quantity
					let max=document.createElement("div");	
						max.setAttribute("class","plus arrow");
						max.style.display="none";
						max.innerHTML=">";
						
						
						//onclick +1 for the dish....
						max.onclick=()=>{				
							let qnt = parseInt(dishq.getAttribute("value"));
							qnt+=1;
							dishq.setAttribute("value",qnt.toString());
							addDishToTotal(prc.getAttribute("value"),true);
						}
						
						
						
						dplus[i]=max;
												
						
						//appending to quantity
						quant.append(max);
					
					let sel=document.createElement("div");
						sel.setAttribute("class","addtocart");
						
						sel.innerHTML="Add To Cart :";  //text 
						
						//adding check box int he selector section
						let checker=document.createElement("input");
							checker.setAttribute("type","checkbox");
							checker.setAttribute("value",dishid[i]);
																		
							//on dish selection action and on dis-selection too 
							checker.onchange=()=>{
								if(checker.checked){
									if(dishcounter<10){
										dishcounter+=1;
										max.style.display="block";
										min.style.display="block";
										dishq.setAttribute("value","1");
										addDishToTotal(prc.getAttribute("value"),true);
									}else{
										alert("Only 10 dishes allowed");
										checker.checked=false;										
									}																		
								}else{
									if(dishcounter>0){
										dishcounter-=1;
										max.style.display="none";
										min.style.display="none";									
										let qnt=parseInt(dishq.getAttribute("value"));
										let price=parseFloat(prc.getAttribute("value"));									
										dishq.setAttribute("value","0");
										addDishToTotal((price*qnt),false);										
									}else{
										alert("Something went wrong please close the window and reopen it");
									}									
								}
							}
							
							dishselector[i]=checker;
							
							//append checker to the parent...
							sel.append(checker);														
							
					quant.append(sel);
					
				rrr.append(quant);
			dishinfo.append(rrr);
			dish.append(dishinfo);
			
			loader.append(dish);//load dish to the main dish loader			
	}
//on plus quantity click
	
addMiddleLayer(false);
loadDishimg(dishid,dishimg);


document.querySelector("#ordplacer").onclick=()=>{	
	document.querySelector("#ordplacer").disabled=true;
	orderPlacer(dishselector,dishcount,hotelid);	
}

}







//get hotel dishes all data.....
function getHotelDhishes(id){
	addMiddleLayer(true,"#disheslist");
	dishloader=document.querySelector("#dishcontainer");
		document.querySelector("#disheslist").style.display="block";	
	let dishid=[];
	let dishname=[];
	let price=[];
	
	$.ajax({
		url:"gethoteldishes",
		data:{"hotelid":id},
		dataType:"json",
		type:"POST",
		
		success:(res)=>{
			if(!res.result){
				alert(res.msg);
				document.querySelector(".closer").click();
			}else if(res.result){
				dishid=res.id;
				dishname=res.name;
				price=res.price;
				
				loadDishes(dishloader,dishid,dishname,price,id);
			}						
		},
		
		error:()=>{
			alert("Failed to get dishes");
		}
	});
	
	
	
}


document.querySelector(".closer").onclick=()=>{
	document.querySelector("#dishcontainer").innerHTML="";
	document.querySelector(".disheslist").style.display="none";	
	totalbill=0.00;
	dishcounter=0;
}







function loadHotelImage(hids){
	hids.forEach((idele)=>{
		let id=idele.parentElement.getAttribute("value");
		
		let image=document.createElement("img");
			image.setAttribute("src","gethotelfirstdishimage?hotelid="+id);
			image.setAttribute("alt","Hotel Image");
			
		idele.append(image);
		
	})
}


function loadHotels(id,name,area,city){
	let list=document.querySelector(".hotels");
	
	let hotel=[];
	let hotelimg=[];
	
	for(i=0;i<id.length;i++){
		hotel[i]=document.createElement("div");
		hotel[i].setAttribute("class","row");
		hotel[i].setAttribute("value",id[i]);
		
		//hotel image imge place
		hotelimg[i]=document.createElement("div");
		hotelimg[i].setAttribute("class","img");
		
		hotel[i].append(hotelimg[i]);
		
		//hotel name and adress
		let hotelnadd=document.createElement("div");
			hotelnadd.setAttribute("class","nameadd")
			
			//hotel name
			let hname=document.createElement("h3");
				hname.setAttribute("class","hotelname");
				hname.innerHTML=name[i];
				
			hotelnadd.append(hname);	
				
			//hotel area and city
			let hca=document.createElement("p");
				hca.setAttribute("class","add");
				hca.innerHTML=area[i]+" ,"+city[i];
	
			hotelnadd.append(hca);
			
			
		hotel[i].append(hotelnadd);	
	
	//append all the stuff to the page container
	list.append(hotel[i]);			
	}
	
	hotel.forEach((htl)=>{
		let id=htl.getAttribute("value");
		htl.onclick=()=>{
			getHotelDhishes(id);
		}
	})

	//remove loading element........
	addMiddleLayer(false);
	
	//load hotel images
	loadHotelImage(hotelimg);
	
}


function getHotelList(){	
	addMiddleLayer(true,"body");
	
	let hotelid=[];
	let hotelname=[];
	let area=[];
	let city=[];
	
	$.ajax({	
		url:"gethotellist",
		data:{},
		dataType:"text",
		type:"POST",
	
		success:(res)=>{
			if(res=="error"){
				alert("Error in loading hotels from your area");				
			}else if(res=="false"){
				alert("Sorry no hotels in your area");
			}else{
				let hotels=JSON.parse(res);
				hotelid=hotels.hotelid;
				hotelname=hotels.hotelname;
				area=hotels.area;
				city=hotels.city;
				
				loadHotels(hotelid,hotelname,area,city);
			}
		},
	
		error:()=>{
			alert("Failed to load hotels from server");
		},
	
		complete:()=>{
			addMiddleLayer(false);
		}
		
	});	
}


//excution start here for the page..........
getHotelList();


