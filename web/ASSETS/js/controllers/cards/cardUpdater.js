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
			json.board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
			swal.showLoading();
			customFetch(json, "PUT", "http://localhost:8080/Trello/CardServlet")
			.then(r=>{
				if(r.status === 200){
					swal({
						title: "Card Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					let cardId = document.createElement("span");
					cardId.className= "card_id";
					cardId.hidden = true;
					cardId.appendChild(document.createTextNode(data.card_id));
					card.text(json.card_name);
					card.get(0).appendChild(cardId);
					form.closest('.modal.open').modal('close');
					form[0].reset();
				}else if(r.status === 403){
					swal({
						title: "Acces Denied",
						text: "You can only edit elements if you're the board master or the element's creator!",
						type: "error",
						showConfirmButton: true,
					})
					$(this).closest('.modal.open').modal('close');
				}
				else{
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
				json.board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
				swal.showLoading();
				customFetch(json, "DELETE", "http://localhost:8080/Trello/CardServlet")
				.then(r=>{
					if(r.status === 200){
						swal({
							title: "Cards deleted successfully!",
							type : "success",
							showConfirmButton: false,
							timer: 2500
						});
						card.remove();
						form.closest('.modal.open').modal('close');
						form[0].reset();
					}else if(r.status === 403){
					swal({
						title: "Acces Denied",
						text: "You can only edit elements if you're the board master or the element's creator!",
						type: "error",
						showConfirmButton: true,
					})
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

