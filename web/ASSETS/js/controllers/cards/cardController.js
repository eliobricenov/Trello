function renderCard(data , container){
	let card = document.createElement("a");
	let cardId = document.createElement("span");
	let userId = document.createElement("span");
	cardId.className= "card_id";
	cardId.hidden = true;
	userId.className= "user_id";
	userId.hidden = true;
	// card.href = "#card_update";
	card.setAttribute("data-target", "card_update")
	card.className = "collection-item modal-trigger";
	card.setAttribute("data-column_id", data.column_id);
	cardId.appendChild(document.createTextNode(data.card_id));
	card.appendChild(cardId);
	userId.appendChild(document.createTextNode(data.user_id));
	card.appendChild(userId);
	card.appendChild(document.createTextNode(data.card_name));
	container.appendChild(card);
}

