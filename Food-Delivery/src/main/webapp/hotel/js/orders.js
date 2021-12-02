

//creating and displaying order data for the hotel user...
function loadOrderInfo(res, id) {
	//making popup
	//main div
	let odm = document.createElement("div");
	odm.setAttribute("class", "ordermanager");
	odm.setAttribute("id", "ordermanager");

	//heading of the popup
	let popuphead = document.createElement("p");
	popuphead.innerHTML = "Order Details";

	odm.append(popuphead);

	let closer = document.createElement("div");
	closer.setAttribute("class", "odmanagerhead");
	closer.innerHTML = "&times;";
	closer.onclick = () => {
		odm.innerHTML = "";
		odm.remove();
	}
	odm.append(closer);

	//ordered customer info
	let custinfo = document.createElement("table");
	custinfo.setAttribute("class", "ordinfo");

	//customer name row
	let custname = document.createElement("tr");
	custname.setAttribute("class", "inforow");

	//td
	let custnamehead = document.createElement("td");
	custnamehead.innerHTML = "Customer Name"

	custname.append(custnamehead);

	//td value
	let custnameval = document.createElement("td");
	custnameval.innerHTML = res.cname;

	custname.append(custnameval);
	custinfo.append(custname);

	//address of the customer....
	let custadd = document.createElement("tr");
	custadd.setAttribute("class", "inforow");

	//td
	let custaddhead = document.createElement("td");
	custaddhead.innerHTML = "Home Address";

	custadd.append(custaddhead);

	//td value
	let custaddvalue = document.createElement("td");
	custaddvalue.innerHTML = res.address;

	custadd.append(custaddvalue);
	custinfo.append(custadd);

	//mobile 
	let custmobile = document.createElement("tr");
	custmobile.setAttribute("class", "inforow");

	//td
	let custmobilehead = document.createElement("td");
	custmobilehead.innerHTML = "Mobile";

	custmobile.append(custmobilehead);

	//td value
	let custmobilevalue = document.createElement("td");
	custmobilevalue.innerHTML = res.mobile;

	custmobile.append(custmobilevalue);
	custinfo.append(custmobile);

	//appending info table to the main element....
	odm.append(custinfo);


	//dishes, prices and quantity....
	let dishes = document.createElement("table");
	dishes.setAttribute("class", "dishes ordinfo");


	//dishes from the server....
	for (i = 0; i < res.dishes.length; i++) {
		let dish = document.createElement("tr");
		dish.setAttribute("class", "dishrow inforow");

		//td1
		let name = document.createElement("td");
		name.setAttribute("class", "dishname");
		name.innerHTML = res.dishes[i].name;

		dish.append(name);

		//td2
		let quantity = document.createElement("td");
		quantity.setAttribute("class", "dishprice");
		quantity.innerHTML = res.dishes[i].quantity;

		dish.append(quantity);

		//td3
		let price = document.createElement("td");
		//price.setAttribute()
		price.innerHTML = res.dishes[i].quantity * res.dishes[i].price;

		dish.append(price);

		dishes.append(dish);
	}

	odm.append(dishes);

	//total bill
	let tbill = document.createElement("table");
	tbill.setAttribute("class", "ordinfo");

	//row of total bill
	let billr = document.createElement("tr");
	billr.setAttribute("class", "bill inforow dishrow");

	//td1
	let tbillhead = document.createElement("td");
	tbillhead.innerHTML = "Totle Bill";

	billr.append(tbillhead);

	//td2
	let tbillamt = document.createElement("td");
	tbillamt.setAttribute("class", "totalamt");
	tbillamt.innerHTML = res.totalbill;

	billr.append(tbillamt);

	tbill.append(billr);

	odm.append(tbill);


	//button container...
	let buttons = document.createElement("div");
	buttons.setAttribute("class", "buttons");
	
	//if status of the order is pending then following html is placed...
	if (res.status == "pending") {
		let accept = document.createElement("button");
		accept.setAttribute("class", "btn");
		accept.style.background = "green";
		accept.innerHTML = "Accept";

		buttons.append(accept);

		let cancel = document.createElement("button");
		cancel.setAttribute("class", "btn");
		cancel.style.background = "red";
		cancel.innerHTML = "Cancel";

		buttons.append(cancel);


		//acctept the order....
		accept.onclick = () => {
			addMiddleLayer(true, "body");
			$.ajax({
				url: "acceptorderbyhotel",
				type: "POST",
				dataType: "json",
				data: { "id": id },

				success: (res) => {
					if (res.result) {
						alert("Order accepted successfully");
						closer.click();
					} else if (res.result == "reload") {
						window.location.replace("./");
					} else {
						alert(res.msg);
					}
				},

				error: () => {
					alert("Error occured while accepting order");
				},

				complete: () => {
					addMiddleLayer(false);
				}
			});
		}


		//decline order for any reason			
		//decline order for any reason			
		cancel.onclick = () => {
			addMiddleLayer(true, "body");

			let reason = prompt("Enter reason for cancelling order -");

			if (reason != null) {

				$.ajax({
					url: "cancelorderbyhotel",
					type: "POST",
					dataType: "json",
					data: {
						"id": id,
						"reason": reason
					},

					success: (res) => {
						if (res.result) {
							alert("Order cancel successfully");
							closer.click();
						} else if (res.result == "reload") {
							window.location.replace("./");
						} else {
							alert(res.msg);
						}
					},

					error: () => {
						alert("Error occured while reaching server");
					},

					complete: () => {
						addMiddleLayer(false);
					}
				});
			} else {
				alert("Please enter reason ...");
				addMiddleLayer(false);
			}
		}
	} else if (res.status == "accepted") {//end of if pending
		let delivered = document.createElement("button");
		delivered.setAttribute("class", "btn");
		delivered.style.background = "green";
		delivered.innerHTML = "Delivered";

		buttons.append(delivered);

		let cancel = document.createElement("button");
		cancel.setAttribute("class", "btn");
		cancel.style.background = "red";
		cancel.innerHTML = "Cancel";

		buttons.append(cancel);


		//acctept the order....
		delivered.onclick = () => {
			addMiddleLayer(true, "body");
			$.ajax({
				url: "deliveredorderbyhotel",
				type: "POST",
				dataType: "json",
				data: { "id": id },

				success: (res) => {
					if (res.result) {
						alert("Order delivered successfully");
						closer.click();
					} else if (res.result == "reload") {
						window.location.replace("./");
					} else {
						alert(res.msg);
					}
				},

				error: () => {
					alert("Error occured while reaching to server");
				},

				complete: () => {
					addMiddleLayer(false);
				}
			});
		}


		//decline order for any reason			
		cancel.onclick = () => {
			addMiddleLayer(true, "body");

			let reason = prompt("Enter reason for cancelling order -");

			if (reason != null) {

				$.ajax({
					url: "cancelorderbyhotel",
					type: "POST",
					dataType: "json",
					data: {
						"id": id,
						"reason": reason
					},

					success: (res) => {
						if (res.result) {
							alert("Order cancel successfully");
							closer.click();
						} else if (res.result == "reload") {
							window.location.replace("./");
						} else {
							alert(res.msg);
						}
					},

					error: () => {
						alert("Error occured while reaching server");
					},

					complete: () => {
						addMiddleLayer(false);
					}
				});
			} else {
				alert("Please enter reason ...");
				addMiddleLayer(false);
			}
		}

	} else if (res.status == "cancel") {//end if accepted			
		let reason = document.createElement("p");
		reason.innerHTML = "<b>Order canceled due to :</b><br>   &nbsp&nbsp&nbsp&nbsp" + res.reason+"<br><br>By the "+res.by;
		reason.style.color = "red";

		buttons.append(reason);
	} else if (res.status == "delivered") { //end of cancel
		let reason = document.createElement("p");
		reason.innerHTML = "Order delivered successfully";
		reason.style.color = "green";

		buttons.append(reason);
	}//end of delivered




	odm.append(buttons);

	let container = document.querySelector("#ordmn");
	container.append(odm);










	//console.log(orddata);

}



function loadOrders(st, ad, tm, id) {
	let loader = document.querySelector("#ordertable");

	let showordbtn = new Array();

	let d = id.length;
	for (i = 0; i < d; i++) {
		//create a row
		let order = document.createElement("tr");
		order.setAttribute("class", "orderrow");

		//status of the order
		let state = document.createElement("td");
		state.innerHTML = st[i];

		order.append(state);

		//address of the order...
		let addr = document.createElement("td");
		addr.innerHTML = ad[i];

		order.append(addr);

		//time of the order
		let tyme = document.createElement("td");
		tyme.innerHTML = tm[i];

		order.append(tyme);

		//set button of for showing the order info 
		let shord = document.createElement("td");
		shord.setAttribute("value", id[i]);

		//button creation....
		let btnsh = document.createElement("button");
		btnsh.setAttribute("class", "showordbtn");
		btnsh.innerHTML = "Show";


		showordbtn[i] = btnsh;

		shord.append(btnsh);

		order.append(shord);

		loader.append(order);
	}

	//add onclick event to the show order details 
	showordbtn.forEach((btn) => {
		btn.onclick = () => {
			addMiddleLayer(true, "body");


			//send request for get total order info 
			$.ajax({
				url: "cmplordinfo",
				data: {
					"id": btn.parentElement.getAttribute("value")
				},
				dataType: "json",
				type: "POST",

				success: (res) => {
					if (res.result) {
						loadOrderInfo(res, btn.parentElement.getAttribute("value"));
					} else {
						alert(res.msg);
					}
				},

				error: () => {
					alert("Error retriving data from server please try again");
				},

				complete: () => {
					addMiddleLayer(false);
				}

			});
		}
	})
}


//onclick show order button event .......
document.querySelector("#showorders").onclick = () => {
	document.querySelector("#ordertable").innerHTML = "";
	let date = document.getElementById("ordersdate").value;
	if (date == "") {
		alert("please select a date...");

	} else {
		addMiddleLayer(true, "body");

		let status = new Array();
		let address = new Array();
		let time = new Array();
		let id = new Array();

		$.ajax({
			url: "getorders",
			data: { "date": date },
			dataType: "text",
			type: "POST",

			success: (res) => {

				if (res == "false") {
					alert("server not found date");
				} else {
					let data = JSON.parse(res);

					if (data.status.length == 0) {
						alert("You have no orders on \n\n" + date);
					} else {
						status = data.status;
						address = data.caddress;
						time = data.time;
						id = data.ordid;

						//loading all the data on the orders page
						loadOrders(status, address, time, id);
					}
				}
			},

			error: () => {
				alert("Error retriving data from server");
			},

			complete: () => {
				addMiddleLayer(false);
			}
		});

	}
}
