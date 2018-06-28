let container = document.querySelector('div#page');
if(sessionStorage.email){
	email = sessionStorage.email
	let config = {
		method : "POST",
		headers: {
			"Content-Type" : "application/json"
		},
		body : sessionStorage.email
	};
	fetch('http://localhost:8080/MySQL/GetData', config)
	.then(r=>{
		return r.json(r.name);
	}).then(r=>{
		sessionStorage.username = r.username;
		sessionStorage.name = r.name;
		renderNavBar(sessionStorage.username);
		renderFloatingButton();
	}).catch(e=>{
		console.log(e);
	});
}else{
	renderNavBar('Default');
	renderFloatingButton();
}

function renderNavBar(username) {
	//tag creation (first in, first out)
	let nav = document.createElement("nav"); 
	let navDiv = document.createElement("div"); 
	let navLink = document.createElement("a");
	
	//attributes assignment
	navDiv.className="navbar-fixed";
	navLink.className="brand-logo center";
	
	//appending
	container.appendChild(nav);
	nav.appendChild(navDiv);
	navDiv.appendChild(navLink);
	navLink.appendChild(document.createTextNode(`${username}`));
}

function renderFloatingButton(){
	//tag creation (first in, last out)
	let mainDiv = document.createElement("div"); 
	let mainB = document.createElement("a");
	let create_icon = document.createElement("i"); 
	let ul = document.createElement("ul");
	let li = document.createElement("li");
	let createB = document.createElement("a");
	let add_icon = document.createElement("i");
	
	//attributes assignment
	mainDiv.className = "fixed-action-btn";
	mainB.className = "btn-floating btn-large blue";
	create_icon.className = "material-icons"
	createB.className = "btn-floating red modal-trigger";
	createB.href = "#modal1";
	add_icon.className= "material-icons";
	
	//appending
	container.appendChild(mainDiv);
	mainDiv.appendChild(mainB);
	mainB.appendChild(create_icon);
	create_icon.appendChild(document.createTextNode("create"));
	mainDiv.appendChild(ul);
	ul.appendChild(li);
	li.appendChild(createB);
	createB.appendChild(add_icon);
	add_icon.appendChild(document.createTextNode("add"));
}


