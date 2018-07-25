function renderCard(data, container){
	var card = $(`
		<li class="collection-item" data-card-id="${data.card_id}" data-column_id="${data.column_id}">
		    <span class="card_id" hidden="">${data.card_id}</span>
		    <a data-target="card_update" class="secondary-content modal-trigger">
		        <i class="material-icons right">more_vert</i>
		    </a>
		    <span hidden="" class="comments">${JSON.stringify(data.comments)}</span>
		    <span hidden="" class="files">${JSON.stringify(data.files)}</span>
		    <span class="user_id" hidden="">${data.user_id}</span>
		    <span class="card_description" hidden="">${data.card_description}</span>
		    <a id="card-name" data-target="card_show" class="modal-trigger">${data.card_name}</a>
		</li>
	`);

	card.prop("draggable",true);
	card.get(0).ondragstart = function (e) {
		e.dataTransfer.setData("card_id", data.card_id );
	};

	card.appendTo(container);
}
