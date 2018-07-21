function renderCard(data, container){
	let card = document.createElement("li");
	let cardIcon = document.createElement("a");
	let cardId = document.createElement("span");
	let userId = document.createElement("span");
	let comments = document.createElement("span");
	let editIcon = document.createElement("i");
	editIcon.className = "material-icons right";
	cardId.className= "card_id";
	cardId.hidden = true;
	comments.hidden = true;
	comments.className = "comments";
	userId.className= "user_id";
	userId.hidden = true;
	card.className = "collection-item"
	cardIcon.setAttribute("data-target", "card_update")
	cardIcon.className = "secondary-content modal-trigger";
	card.setAttribute("data-column_id", data.column_id);
	editIcon.appendChild(document.createTextNode("more_vert"));
	cardId.appendChild(document.createTextNode(data.card_id));
	cardIcon.appendChild(editIcon);
	card.appendChild(cardId);
	card.appendChild(cardIcon);
	userId.appendChild(document.createTextNode(data.user_id));
	comments.appendChild(document.createTextNode(data.comments));
	card.appendChild(comments);
	card.appendChild(userId);
	card.appendChild(document.createTextNode(data.card_name));
	container.appendChild(card);
}

