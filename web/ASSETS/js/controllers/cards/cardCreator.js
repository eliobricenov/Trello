function createCard(form, data, container){
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
			j.board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
			j.column_id = data.column_id;
			swal.showLoading();
			console.log(j);
			customFetch(j, "POST", "http://localhost:8080/Trello/CardServlet")
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
					console.log(container.find('div.collection.with-header'));
					renderCard(r.data, container.find('div.collection.with-header').get(0));
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