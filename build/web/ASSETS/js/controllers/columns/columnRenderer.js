function renderColumn(data, container) {
	//Elements creation
	var column =  $(`
		<div class="col s12 m4">
		    <div class="card">
		        <div class="card-content">
		            <span class="board_id" hidden="">${data.board_id}</span>
		            <span class="user_id" hidden="">${data.user_id}</span>
		            <span class="column_id" hidden="">${data.column_id}</span>
					
		            <!-- CARTA -->
		            <h5 class="collection-header">${data.column_name}<a class="secondary-content modal-trigger" data-target="modal_update" data-column_id="${data.column_id}" data-board_id="${data.board_id}"><i class="material-icons right">more_vert</i></a></h5>
		            <br>
		            <div class="collection">
		                
		            </div>
		        </div>
		        <div class="card-action center-align">
		            <a class="teal-text modal-trigger" data-target="modal2">Add Card</a>
		        </div>
		    </div>
		</div>
	`);

	column.get(0).ondrop = function(e) {
		let json = {}
		json.card_id = parseInt(e.dataTransfer.getData("card_id"));
		let card = $("li.collection-item").filter( (k,v)=> $(v).data("card-id") == json.card_id);
		card.data("column_id", $(this).find(".column_id").text());
		json.column_id = parseInt($(this).find(".column_id").text());
		customFetch(json, "POST", `/Trello/DragDropServlet`)
		.then(r=>{
			$(this).find(".collection").append(card);
		})
		.catch(err => console.log(err))
	}
	column.get(0).ondragover = function(e) {
		e.preventDefault();
	}

	var cardContainer = column.find(".collection");
	for (var i = 0; i < data.cards.length; i++) {
		renderCard(data.cards[i], cardContainer);
	}
	column.appendTo(container);
}
