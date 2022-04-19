function addclass(value) {
	let adcl = value==null?document.getElementById("addclass"):value;
	adcl.removeAttribute("hidden");

	function close() {
		adcl.setAttribute("hidden", "true");
	}
	let clbt = document.getElementsByClassName("cl");
	for (let i = 0; i < clbt.length; i++) {
		clbt[i].onclick = () => {
			close();
		}
	}
}




//subject window
function changesub(){
	let sub = document.getElementById("subject");
	
	let sn =document.getElementById("subname");
	let fc = document.getElementById("facs");
	let ads = document.getElementById("addsubs");
	let btn = document.getElementById("sbtn");
	if(sub.value == -1){
		sn.removeAttribute("hidden");
		sn.setAttribute("required","required");
		fc.removeAttribute("hidden");
		ads.setAttribute("action","addsubject");
		btn.innerHTML="Submit";
		btn.removeAttribute("hidden");
	}else if(sub.value == -2){
		sn.setAttribute("hidden","true");
		sn.removeAttribute("required");
		fc.setAttribute("hidden","true");
		ads.setAttribute("action","removesubject");
		btn.setAttribute("hidden","true");
	}else{
		sn.setAttribute("hidden","true");
		sn.removeAttribute("required");
		fc.setAttribute("hidden","true");
		ads.setAttribute("action","removesubject");
		btn.innerHTML="Remove";
		btn.removeAttribute("hidden");
		
	}
}


document.getElementsByTagName("button")[2].onclick = () => {
	window.print();
}
document.getElementsByTagName("button")[3].onclick = () => {
	if (window.confirm("You want to remove class")) {
		window.location.replace("./remove");
	}
}
document.getElementsByTagName("button")[4].onclick = () => {
	addclass(document.getElementById("addclass"));
}

document.getElementsByTagName("button")[5].onclick = () => {
	addclass(document.getElementById("addsubject"));
}