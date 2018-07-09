$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");

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

function renderBoard(container, data){
	//tag creation
	let collectionItem = document.createElement("li");
	let avatarIcon = document.createElement("i");
	let title = document.createElement("a");
	let description = document.createElement("p");
	let id = document.createElement("span");
	let optionsLink = document.createElement("a");
	let deleteLink = document.createElement("a");
	let optionsIcon = document.createElement("i");
	let deleteIcon = document.createElement("i");

	//attributes assignment 
	collectionItem.className = "collection-item avatar";
	avatarIcon.className = "material-icons circle";
	avatarIcon.style.backgroundColor = data.board_color;
	title.className = "title";
	title.href = "http://localhost:8080/Trello/views/columns.html?board_id=" + data.board_id;
	description.className= "paragraph";
	id.className= "id";
	id.hidden = true;
	optionsLink.className = "secondary-content modal-trigger";
	deleteLink.className = "secondary-content modal-trigger";
	optionsLink.href = "#modal_update";
	deleteLink.href = "#";
	optionsIcon.className = "material-icons";
	deleteIcon.className = "material-icons";

	//appending
	avatarIcon.appendChild(document.createTextNode("event_note"));
	title.appendChild(document.createTextNode(data.board_name));
	description.appendChild(document.createTextNode(data.board_description));
	id.appendChild(document.createTextNode(data.board_id));
	optionsIcon.appendChild(document.createTextNode("edit"));
	deleteIcon.appendChild(document.createTextNode("delete"));
	optionsLink.appendChild(optionsIcon);
	deleteLink.appendChild(deleteIcon);

	collectionItem.appendChild(avatarIcon);
	collectionItem.appendChild(title);
	collectionItem.appendChild(description);
	collectionItem.appendChild(id);
	collectionItem.appendChild(optionsLink);
	container.appendChild(collectionItem);

}


