function createCard(form, data, container){
	form.unbind('submit').submit(function(e) {
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
			j.board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
			j.column_id = data.column_id;
			swal.showLoading();
			customFetch(j, "POST", `http://localhost:8080/Trello/CardServlet?board_id=${j.board_id}&column_id=${j.column_id}`)
			.then(r=>{
				if(r.status === 200){ 
					swal({
						title: "Card created successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 1500
					});
					$(this).closest('.modal.open').modal('close');
					renderCard(r.data, data.cardContainer);
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
