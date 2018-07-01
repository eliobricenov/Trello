function renderNavBar(username, container) {
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

function renderFloatingButton(container){
	//tag creation 
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
	mainB.setAttribute("id", "showButton");
	create_icon.className = "material-icons"
	createB.className = "btn-floating red modal-trigger";
	createB.setAttribute("id", "createButton");
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

function renderBoard(container){
	//tag creation
	let collection = document.createElement("ul");
	let collectionItem = document.createElement("li");
	let avatar = document.createElement("i");
	let title = document.createElement("a");
	let description = document.createElement("p");
}
