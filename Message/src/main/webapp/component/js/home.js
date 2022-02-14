function addclass() {
	let adcl = document.getElementById("addclass")
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
