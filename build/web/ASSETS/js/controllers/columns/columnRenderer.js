function renderColumn(data, container) {
	//Elements creation
	let columnDivCol = document.createElement("div");
	let columnDivH = document.createElement("div");
	let columnDivS = document.createElement("div");
	let columnDivC = document.createElement("div");
	let columnH5   = document.createElement("h5");
	let columnBr   = document.createElement("br");
	let cardDivCo = document.createElement("div");
	let columnDivAc = document.createElement("div");
	let columnLink = document.createElement("a");
	let cardContainer = document.createElement("div");
	let boardId = document.createElement("span");
	let userId = document.createElement("span");
	let columnId = document.createElement("span");
	let columnDivA = document.createElement("a");
	let columnDivI = document.createElement("i");

	//attributes assigment
	cardContainer.className = "collection";
	columnH5.className = "collection-header";
	columnDivCol.className = "col s12 m4";
	columnDivH.className = "";
	columnDivS.className = "card";
	columnDivC.className = "card-content";
	columnDivAc.className = "card-action center-align";
	columnLink.className = "teal-text modal-trigger";
	// columnLink.href = "#modal2"
	columnLink.setAttribute("data-target", "modal2");
	boardId.className= "board_id";
	boardId.hidden = true;
	userId.className= "user_id";
	userId.hidden = true;
	columnId.className= "column_id";
	columnId.hidden = true;
	columnDivI.className = "material-icons right";
	columnDivA.className = "secondary-content modal-trigger";
	// columnDivA.href = "#modal_update";
	columnDivA.setAttribute("data-target", "modal_update");
	columnDivA.setAttribute("data-column_id", data.column_id);
	columnDivA.setAttribute("data-board_id", data.board_id);


	//appending
	columnId.appendChild(document.createTextNode(data.column_id));
	boardId.appendChild(document.createTextNode(data.board_id));
	userId.appendChild(document.createTextNode(data.user_id));
	columnDivCol.appendChild(columnDivH);
	columnDivCol.appendChild(columnDivH);
	columnDivH.appendChild(columnDivS);
	columnDivS.appendChild(columnDivC);
	columnDivS.appendChild(columnDivAc);
	columnDivC.appendChild(boardId);
	columnDivC.appendChild(userId);
	columnDivC.appendChild(columnId);
	columnDivA.appendChild(columnDivI);
	columnDivI.appendChild(document.createTextNode("more_vert"));
	columnH5.appendChild(document.createTextNode(data.column_name));
	columnH5.appendChild(columnDivA);
	columnDivC.appendChild(columnH5);
	columnDivC.appendChild(columnBr);
	columnDivC.appendChild(cardDivCo);
	if(data.cards){
		for (var i = 0; i < data.cards.length; i++) {
			console.log(data.cards);
			renderCard(data.cards[i], cardContainer);
		}	
	};
	columnDivC.appendChild(cardContainer);
	columnDivAc.appendChild(columnLink);
	columnLink.appendChild(document.createTextNode("Agregar tarjeta"));
	container.appendChild(columnDivCol);
}


