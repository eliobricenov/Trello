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
	let columnId = document.createElement("span");
	let columnDivA = document.createElement("a");
	let columnDivI = document.createElement("i");

	//attributes assigment
	cardContainer.className = "collection";
	columnDivCol.className = "col s12 m6 l3";
	columnDivH.className = "card horizontal";
	columnDivS.className = "card-stacked";
	columnDivC.className = "card-content";
	columnDivAc.className = "card-action";
	columnLink.className = "teal-text modal-trigger";
	columnLink.href = "#modal2"
	boardId.className= "board_id";
	boardId.hidden = true;
	columnId.className= "column_id";
	columnId.hidden = true;
	columnDivI.className = "material-icons right";
	columnDivA.className = "secondary-content modal-trigger";
	columnDivA.href = "#modal_update";
	// columnDivA.setAttribute("data-target", "modal_update");
	columnDivA.setAttribute("data-column_id", data.column_id);
	columnDivA.setAttribute("data-board_id", data.board_id);

	//appending
	columnId.appendChild(document.createTextNode(data.column_id));
	boardId.appendChild(document.createTextNode(data.board_id));
	columnDivCol.appendChild(columnDivH);
	columnDivCol.appendChild(columnDivH);
	columnDivH.appendChild(columnDivS);
	columnDivS.appendChild(columnDivC);
	columnDivS.appendChild(columnDivAc);
	columnDivC.appendChild(columnH5);
	columnDivC.appendChild(boardId);
	columnDivC.appendChild(columnId);
	columnDivC.appendChild(columnDivA);
	columnDivA.appendChild(columnDivI);
	columnDivI.appendChild(document.createTextNode("more_vert"));
	columnH5.appendChild(document.createTextNode(data.column_name));
	columnDivC.appendChild(columnBr);
	columnDivC.appendChild(cardDivCo);
	if(data.cards){
		for (var i = 0; i < data.cards.length; i++) {
			renderCard(data.cards[i], cardContainer);
		}	
	};
	columnDivC.appendChild(cardContainer);
	columnDivAc.appendChild(columnLink);
	columnLink.appendChild(document.createTextNode("Agregar tarjeta"));
	container.appendChild(columnDivCol);
}

function renderCard(data , container){

	let card = document.createElement("a");
	let cardId = document.createElement("span");
	cardId.className= "card_id";
	cardId.hidden = true;
	card.href = "#card_update";
	card.className = "collection-item modal-trigger";
	cardId.appendChild(document.createTextNode(data.card_id));
	card.appendChild(cardId);
	card.appendChild(document.createTextNode(data.card_name));
	container.appendChild(card);
}

function updateColumn(form, data, column){
	form.find('button[type="submit"][name="update"]').unbind().click(function(e){
		form.customValidate({
			card_name:{
				required:true,
				lettersonlys:true
			},
			card_description:{
				required:true,
				lettersonlys:true
			}
		})
		.then(r=>{
			let json = form.formToJSON();
			json.column_id = data.column_id;
			json.board_id = data.board_id;
			console.log(json);
			swal.showLoading();
			customFetch(json, "POST", "http://localhost:8080/Trello/Column_Update")
			.then(r=>{
				console.log(r);
				if(r.status === 200){
					swal({
						title: "Column Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					column.children('h5').text(json.column_name);
					form.closest('.modal.open').modal('close');
					form[0].reset();
				}else{
					swal({
						title: "Oppss!",
						type : "error",
						text: "Seems there was a problem, try again.",
						timer: 2500
					});
				}
			})
		})
	});

	form.find('button[type="submit"][name="delete"]').unbind().click(function(){
		swal({
			title: 'Are you sure?',
			text: "You won't be able to revert this!",
			type: 'warning',
			showCancelButton: true,
			cancelButtonColor: '#d33',
			confirmButtonColor: '#3085d6',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.value) {
				let json = form.formToJSON();
				json.column_id = data.column_id;
				swal.showLoading();
				customFetch(json, "POST", "http://localhost:8080/Trello/Column_Delete")
				.then(r=>{
					if(r.status === 200){
						swal({
							title: "Column deleted successfully!",
							type : "success",
							showConfirmButton: false,
							timer: 2500
						});
						column.parent().remove();
						form.closest('.modal.open').modal('close');
						form[0].reset();
					}else{
						swal({
							title: "Oppss!",
							type : "error",
							text: "Seems there was a problem, try again later.",
							timer: 2500
						});
					}
				})
				.catch(err=>{console.log(err);})
			}else if( result.dismiss === swal.DismissReason.cancel){
				swal({
					title: "Cancelled!",
					type : "error",
					text: "Your cards are safe",
					timer: 2500
				});
			}
		})
		.catch(err=>{console.log(err);})
	});

}

function createCard(form, container){
	form.submit(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		let form = $(this);
		$(this).customValidate({
			card_name:{
				required:true,
				lettersonlys:true
			},
			card_description:{
				required:true,
				lettersonlys: true
			}
		}).then(r=>{
			let j = form.formToJSON();
			j.board_id = new URLSearchParams(window.location.search).get("board_id");
			j.column_id = container.parent().find('span.column_id').text();
			swal.showLoading();
			console.log(j);
			customFetch(j, "POST", "http://localhost:8080/Trello/Card_Creation")
			.then(r=>{
				console.log(r);
				if(r.status === 200){ 
					swal({
						title: "Card created successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 1500
					});
					$(this).closest('.modal.open').modal('close');
					renderCard(r.data, container.get(0));
					form[0].reset();
				}else{
					form[0].reset();
					swal({
						title: "Oppss!",
						type : "error",
						text: "Seems there was a problem, try again later.",
						timer: 1500
					});
				}})
			.catch(err=>{console.log(err)});
		})
		.catch(err=>{console.log(err)})
	})
}

function updateCard(form, data, card){
	form.find('button[type="submit"][name="update"]').unbind().click(function(e){
		form.customValidate({
			card_name:{
				required:true,
				lettersonlys:true
			},
			card_description:{
				required:true,
				lettersonlys:true
			}
		})
		.then(r=>{
			let json = form.formToJSON();
			json.card_id = data.card_id;
			swal.showLoading();
			customFetch(json, "POST", "http://localhost:8080/Trello/Card_Update")
			.then(r=>{
				console.log(r);
				if(r.status === 200){
					swal({
						title: "Card Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					card.text(json.card_name);
					form.closest('.modal.open').modal('close');
					form[0].reset();
				}else{
					swal({
						title: "Oppss!",
						type : "error",
						text: "Seems there was a problem, try again.",
						timer: 2500
					});
				}
			})
		})
	});

	form.find('button[type="submit"][name="delete"]').unbind().click(function(){
		swal({
			title: 'Are you sure?',
			text: "You won't be able to revert this!",
			type: 'warning',
			showCancelButton: true,
			cancelButtonColor: '#d33',
			confirmButtonColor: '#3085d6',
			confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if (result.value) {
				let json = form.formToJSON();
				json.card_id = data.card_id;
				swal.showLoading();
				customFetch(json, "POST", "http://localhost:8080/Trello/Card_Delete")
				.then(r=>{
					if(r.status === 200){
						swal({
							title: "Cards deleted successfully!",
							type : "success",
							showConfirmButton: false,
							timer: 2500
						});
						card().remove();
						form.closest('.modal.open').modal('close');
						form[0].reset();
					}else{
						swal({
							title: "Oppss!",
							type : "error",
							text: "Seems there was a problem, try again later.",
							timer: 2500
						});
					}
				})
				.catch(err=>{console.log(err);})
			}else if( result.dismiss === swal.DismissReason.cancel){
				swal({
					title: "Cancelled!",
					type : "error",
					text: "Your cards are safe",
					timer: 2500
				});
			}
		})
		.catch(err=>{console.log(err);})
	});
}

