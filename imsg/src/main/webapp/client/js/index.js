//message container 
var container = document.getElementById("maincn");
var lastmsg = 0;
var students = new Map();;
var faculties;
var id;
var fmsgid = 0;
var fmsg = null;

$.getJSON("../webmsg/msgapp/getid", (res) => {
	id = res.id
});

//getting student list in the class
$.ajax({
	url: "../webmsg/msgapp/getstudents",
	data: {},
	async: false,
	dataType: "JSON",
	type: "GET",
	success: (res) => {
		res.forEach((item) => {
			students.set(item.id, item.name);
		});
	},

	error: (res) => {

	},
	complete: (res) => {

	}
});


//get faculty list
$.ajax({
	url: "../webmsg/msgapp/getfaculties",
	data: {},
	async: false,
	dataType: "JSON",
	type: "GET",
	success: (res) => {
		faculties = new Map();
		res.forEach((item) => {
			faculties.set(item.id, item.name);
		});
	},

	error: (res) => {

	},
	complete: (res) => {

	}
});


onload = () => {   //on document loaded start the functionality
	//get student list  
	getLast();
}

function getLast() {
	//get last 25 msg	
	$.ajax({
		url: "../webmsg/msgapp/getlastmsg/" + lastmsg,
		data: {},
		dataType: "JSON",
		async: false,
		type: "GET",
		success: (res) => {

			for (let i = 0; i < res.length; i++) {

				//store last msg id from
				if (i == res.length - 1)
					lastmsg = res[i].id;

				let message = document.createElement("div");
				let fr = document.createElement("div");
				if (res[i].senderId == id) {
					message.setAttribute("class", "message out");
					message.setAttribute("style", "background:#9bf296");

					fr.innerHTML = "You";

				} else {
					message.setAttribute("class", "message");
					if (res[i].type == 's') {
						fr.innerHTML = students.get(res[i].senderId);
					} else if (res[i].type == 'f') {
						fr.innerHTML = faculties.get(res[i].senderId);
					}
				}
				fr.setAttribute("class", "from");

				let msg = document.createElement("p");
				msg.innerHTML = res[i].text;
				let time = document.createElement("div");
				time.setAttribute("class", "time");
				let date = new Date(res[i].time);
				time.innerHTML = date.getDate() + '/' + date.getMonth() + '/' + date.getFullYear() + "  " + date.getHours() + ":" + date.getMinutes();

				message.appendChild(fr);
				message.appendChild(msg);
				message.appendChild(time);

				container.appendChild(message);

				//get first msg id for the fmsgid variable when msgs ==0 and its first msg from server
				if (i == 0 && fmsgid == 0) {
					fmsgid = res[i].id;
					fmsg = message;
				}
			}
		},

		error: () => {
			alert("Error getting messages....");
		}
	});

	/*setInterval(()=>{getLast()},3000);*/
}

onkeydown = (event) => {
	if (event.keyCode == 13) {
		send();
	}
}

function send() {
	getLast();
	let msgtext = document.getElementById("msgsend");
	if (msgtext.value.trim() == "") {
		return;
	} else {
		let message = document.createElement("div");
		message.setAttribute("class", "message out");
		let fr = document.createElement("div");
		fr.setAttribute("class", "from");
		fr.innerHTML = "You";
		let msg = document.createElement("p");
		msg.innerHTML = msgtext.value;
		let time = document.createElement("div");
		time.setAttribute("class", "time");
		let date = new Date();
		time.innerHTML = date.getDate() + '/' + date.getMonth() + '/' + date.getFullYear() + "  " + date.getHours() + ":" + date.getMinutes();

		message.appendChild(fr);
		message.appendChild(msg);
		message.appendChild(time);

		container.appendChild(message);
		msgtext.value = "";
		$.ajax({
			url: "./sendmsg",
			data: {
				"text": msg.textContent,
			},
			dataType: "json",
			type: "POST",
			success: (res) => {
				if (res.response) {
					message.setAttribute("style", "background:#9bf296");
					lastmsg = res.id;
				} else if (!res.response) {
					message.setAttribute("style", "background:#f06e82");
				} else {
					alert("you are not looged in...");
					window.location.replace("./logout");
				}
			},

			error: () => {
				message.setAttribute("style", "background:#f06e82");
			},
			complete: (res) => {
				message.scrollIntoView({
					behavior: 'smooth'
				});
			}
		});
	}
}



function loadold() {
	$.ajax({
		url: "../webmsg/msgapp/getfirstmsg/"+ fmsgid,
		data: {},
		dataType: "json",
		type: "GET",
		success: (res) => {
			for (let i = 0; i < res.length; i++) {

				//store last msg id from
				if (i == res.length - 1)
					fmsgid = res[i].id;

				let message = document.createElement("div");
				let fr = document.createElement("div");
				if (res[i].senderId == id) {
					message.setAttribute("class", "message out");
					message.setAttribute("style", "background:#9bf296");

					fr.innerHTML = "You";

				} else {
					message.setAttribute("class", "message");
					if (res[i].type == 's') {
						fr.innerHTML = students.get(res[i].senderId);
					} else if (res[i].type == 'f') {
						fr.innerHTML = faculties.get(res[i].senderId);
					}
				}
				fr.setAttribute("class", "from");

				let msg = document.createElement("p");
				msg.innerHTML = res[i].text;
				let time = document.createElement("div");
				time.setAttribute("class", "time");
				let date = new Date(res[i].time);
				time.innerHTML = date.getDate() + '/' + date.getMonth() + '/' + date.getFullYear() + "  " + date.getHours() + ":" + date.getMinutes();

				message.appendChild(fr);
				message.appendChild(msg);
				message.appendChild(time);

				container.insertBefore(message,container.children[1]);
			}

		},
		error: () => {
			alert("Error getting message.....");
		}
	});
}