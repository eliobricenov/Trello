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
			json.column_id = parseInt(data.column_id);
			json.board_id = parseInt(data.board_id);
			swal.showLoading();
			customFetch(json, "PUT", `http://localhost:8080/Trello/ColumnServlet?board_id=${json.board_id}&column_id=${json.column_id}`)
			.then(r=>{
				switch(r.status){
					case 200:
					swal({
						title: "Column Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					let columnDivA = document.createElement("a");
					let columnDivI = document.createElement("i");
					columnDivI.className = "material-icons right";
					columnDivA.className = "secondary-content modal-trigger";
					columnDivA.href = "#modal_update";
					columnDivA.setAttribute("data-column_id", data.column_id);
					columnDivA.setAttribute("data-board_id", data.board_id);
					columnDivA.appendChild(columnDivI);
					columnDivI.appendChild(document.createTextNode("more_vert"));
					column.children('h5.collection-header').text(json.column_name);
					column.children('h5.collection-header').get(0).appendChild(columnDivA);
					form.closest('.modal.open').modal('close');
					form[0].reset();
					break;

					case 403:
					swal({
						title: "Acces Denied",
						text: "You can only edit elements if you're the board master or the element's creator!",
						type: "error",
						showConfirmButton: true,
					})
					break;

					case 404:
					swal({
						title: "Oppss!",
						type : "error",
						text: "We couldn't find the column, try again later",
						timer: 2500
					});
					break;
					
					case 500:
					swal({
						title: "Oppss!",
						type : "error",
						text: "Seems there was a problem, try again.",
						timer: 2500
					});
					break;
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
				json.board_id = parseInt(data.board_id);
				swal.showLoading();
				customFetch(json, "DELETE", `http://localhost:8080/Trello/ColumnServlet?board_id=${json.board_id}&column_id=${json.column_id}`)
				.then(r=>{
					if(r.status === 200){
						swal({
							title: "Column deleted successfully!",
							type : "success",
							showConfirmButton: false,
							timer: 2500
						});
						column.parent().parent().remove();
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
