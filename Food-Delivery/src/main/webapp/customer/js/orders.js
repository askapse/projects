

//order info display section
function displayOrderInfo(res,id){
	let mainwin=document.createElement("div");
		mainwin.setAttribute("class","ordermanager");
		
		//heading....
		mainwin.append(document.createElement("p").innerHTML="Order Details");
			let closer=document.createElement("div");
				closer.innerHTML="&times;";
				closer.setAttribute("class","odmanagerhead");
				
				closer.onclick=()=>{
					mainwin.innerHTML="";
					mainwin.remove();
				}				
		mainwin.append(closer);  //closer of the window added
			
				//adding table for basic info
				let intable=document.createElement("table");
					intable.setAttribute("class","ordinfo");
					
						//adding rows from here
						let hname=document.createElement("tr");
							hname.setAttribute("class","inforow");
							
							//td
							let hnameh=document.createElement("td");
								hnameh.innerHTML="Hotel Name";
						hname.append(hnameh);
							
							let hnamev=document.createElement("td");
								hnamev.innerHTML=res.name;
						hname.append(hnamev);
				intable.append(hname);
					
				
						//hotel address
						let hadd=document.createElement("tr");
							hadd.setAttribute("class","inforow");
					
							//td
							let hadh=document.createElement("td");
								hadh.innerHTML="Hotel Address";
						hadd.append(hadh);
							
							let hadv=document.createElement("td");
								hadv.innerHTML=res.address;
						hadd.append(hadv);
				intable.append(hadd);
				
						
						//hotel mobile no.
						let hmobile=document.createElement("tr");
							hmobile.setAttribute("class","inforow");
							
							//td
							let hmh=document.createElement("td");
								hmh.innerHTML="Mobile";							
						hmobile.append(hmh);
						
							let hmv=document.createElement("td");
								hmv.innerHTML=res.mobile;
						hmobile.append(hmv);							
				intable.append(hmobile);
							
						//status of the order
						let status=document.createElement("tr");
							status.setAttribute("class","inforow");
							
							//td
							let sth=document.createElement("td");						
								sth.innerHTML="Delivery Status";
						status.append(sth);
						
							let stv=document.createElement("td");
								stv.innerHTML=res.status;
						status.append(stv);
				intable.append(status);
				
			mainwin.append(intable);//append the information table to the main wondow
		
				//creating table for dishes ,prices and qunatity
				let dishtb=document.createElement("table");
					dishtb.setAttribute("class","dishes ordinfo");
						
						for(i=0;i<res.dishes.length;i++){
							let row=document.createElement("tr");
							
								//td
								let dname=document.createElement("td");
									dname.innerHTML=res.dishes[i].name;
							row.append(dname);
								
								let dqnt=document.createElement("td");
									dqnt.innerHTML=res.dishes[i].quantity;
							row.append(dqnt);
								
								let dprc=document.createElement("td");
									dprc.innerHTML=res.dishes[i].price;									
							row.append(dprc);
							
						dishtb.append(row);
						}
			mainwin.append(dishtb);
				
				//create total bill row
				let tbill=document.createElement("table");
					tbill.setAttribute("class","ordinfo");
					
					//tr
					let rw=document.createElement("tr");
						rw.setAttribute("class","bill inforow dishrow");
						
						//td
						rw.append(document.createElement("td").innerHTML="Total Bill ");
							let tamt=document.createElement("td");
								tamt.setAttribute("class","totalamt");
								tamt.innerHTML=res.totalbill;
						rw.append(tamt);
				tbill.append(rw);
			mainwin.append(tbill);
		
		
		let btns=document.createElement("div");
			btns.style.margin="10px 0";
		//operation on order
		if(res.status=="pending" || res.status=="accepted"){
			
			//buttons
			let cancelbtn=document.createElement("button");
				cancelbtn.style.color="white";
				cancelbtn.style.background="red";
				cancelbtn.innerHTML="Cancel";
			btns.append(cancelbtn);
		
			cancelbtn.onclick=()=>{
				addMiddleLayer(true,"body");
				
				$.ajax({
					url:"cancelorderbycust",
					data:{"id":id},
					dataType:"json",
					type:"POST",
					
					success:(res)=>{
						if(res.result){
							alert("Order canceled Successfully");
							cancelbtn.remove();
						}else if(res.result=="reload"){
							window.location.replace("./");
						}else{
							alert(res.msg);
						}
						
					},
					
					error:()=>{
						alert("Server fails to serve provded request");
					},
					
					complete:()=>{
						addMiddleLayer(false);	
					}					
				});
			}
		}else if(res.status == "delivered"){
			let dl=document.createElement("p");
				dl.innerHTML="&nbsp &nbsp &nbsp &nbsp Order delivered successfully...";
				dl.style.color="green";
			btns.append(dl);			
		}else if(res.status == "cancel"){
			
			if(res.by == "Customer"){
				let cncl=document.createElement("p");
				cncl.innerHTML="&nbsp &nbsp &nbsp &nbsp This order canceled by you...";
				cncl.style.color="red";
			btns.append(cncl);
			}else if(res.by == "Hotel"){
				let cncl=document.createElement("p");
				cncl.innerHTML="&nbsp &nbsp &nbsp &nbsp This order canceled by hotel...<br><br>Reason : "+res.reason;
				cncl.style.color="red";
			btns.append(cncl);
			}
		}
	mainwin.append(btns);// button or text of the delivery cancel ect
	
	document.getElementById("popup").append(mainwin);
}


//load order info
function loadOrdInfo(id){
	addMiddleLayer(true,"body");
	
	$.ajax({
		url:"cmporderinfocust",
		type:"POST",
		data:{"id":id},
		dataType:"json",
		
		success:(res)=>{
			if(res.result){
				
				displayOrderInfo(res,id);
				
			}else if(res.result=="reload"){
				window.location.replace("./");
			}else{
				alert(res.msg);	
			}
			
		},
		
		error:()=>{
			alert("server fails retriving data");
		},
		
		complete:()=>{
			addMiddleLayer(false);
		}
	});
}



//load order info interface
function loadOrders(res){
	let mtable=document.createElement("table");
		mtable.setAttribute("class","listord");
		
		let ordbtn=new Array();
	
	
		//table body for orders in the selected month
		for(i=0;i<res.ordid.length;i++){
			let order=document.createElement("tr");
				
				//td
				let state=document.createElement("td");
					state.innerHTML=res.status[i];
			order.append(state);
			
				let nm=document.createElement("td");
					nm.innerHTML=res.name[i];					
			order.append(nm);
			
				let dt=document.createElement("td");
					dt.innerHTML=res.dtime[i];
			order.append(dt);
				
				//button for view order full info			
				let btnc=document.createElement("td");
					btnc.setAttribute("value",res.ordid[i]);
					let showbtn=document.createElement("button");
						showbtn.setAttribute("class","showorder");
						showbtn.innerHTML="Show";
						showbtn.style.width="70px";
						ordbtn[i]=showbtn;
					btnc.append(showbtn);
					
				order.append(btnc);
			mtable.append(order);
		}
	
	ordbtn.forEach((btn)=>{
		btn.onclick=()=>{			
			loadOrdInfo(btn.parentElement.getAttribute("value"));	
		}
	})	
	
	document.querySelector("#ordersec").append(mtable);//all the order append to the section
	
}



//onclick show orders
document.querySelector("#showorders").onclick=()=>{
	addMiddleLayer(true,"body");
	document.querySelector("#ordersec").innerHTML="";
	let datemonth=document.getElementById("ordersmonth").value;	
		if(datemonth==""){
			alert("please select month");
			addMiddleLayer(false);			
		}else{
			$.ajax({
				url:"getmonthorders",
				dataType:"json",
				data:{"date":datemonth},
				type:"POST",
				
				success:(res)=>{
					if(res.result){
						if(res.ordid.length == 0){
							alert("No order in month \n\n"+datemonth)	
						}else{
							loadOrders(res); //load orders from server data
						}						
					}else if(res.result=="reload"){
						window.location.replace("./");
					}else{
						alert(res.msg);
					}					
				},
				
				error:()=>{
					alert("server fails to retrive data");
				},
				
				complete:()=>{
						addMiddleLayer(false);						
				}
					
			});
				
		}
			
}

