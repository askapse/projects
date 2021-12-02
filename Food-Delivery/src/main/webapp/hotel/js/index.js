
//loading();

$("#change").load("./hotel/home.html");

document.querySelector("#homebtn").onclick = function() {
	$("#change").load("./hotel/home.html");
}

document.querySelector("#profilebtn").onclick = () => {
	$("#change").load("./hotel/profile.html");
}

document.querySelector("#orderbtn").onclick = () => {
	$("#change").load("./hotel/orders.html");
}




//logout
document.querySelector("#logout").onclick = () => {
	$.ajax({
		url: "logout",
		data: {},
		dataType: "json",
		type: "POST",

		success: (res) => {
			if (res.result) {
				window.location.replace("./index.html");
			} else {
				alert("Logout failed");
			}
		},

		error: () => {
			alert("Failed operation");
		}
	});
}











function addMiddleLayer(val, select) {
	if (val) {
		let layer = document.createElement("div");
		layer.style.zIndex = 50;
		layer.style.position = "absolute";
		layer.style.top = 0;
		layer.style.left = 0;
		layer.style.width = "100%";
		layer.style.height = "100%";
		layer.style.background = "rgba(107, 109, 106, 0.445)";
		layer.setAttribute("id", "midlayer");
		document.querySelector(select).appendChild(layer);

		let loading = document.createElement("div");
		loading.setAttribute("id", "loading");
		layer.appendChild(loading);

	} else {
		document.getElementById("midlayer").remove();
	}
}
