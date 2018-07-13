function updateBoard(form, data, board){

	form.find('button[type="submit"][name="update"]').unbind().click(function(){
		form.customValidate({
			table_name:{
				required:true,
			}
		})
		.then(r=>{
			let json = form.formToJSON();
			json.board_id = data.board_id;
			json.board_collaborators = form.find('.chips').material_chip('data');
			swal.showLoading();
			customFetch(json, "PUT", "http://localhost:8080/Trello/BoardServlet")
			.then(r=>{
				switch(r.status){
					case 200:
					swal({
						title: "Board Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					board.children('i.material-icons.circle').css("background-color", json.board_color);
					board.children('a.title').text(json.board_name);
					board.children('p.paragraph').text(json.board_description);
					board.find('span.collabs').text(JSON.stringify(r.board_collaborators));
					form.closest('.modal.open').modal('close');
					form.get(0).reset();
					break;
					case 403:
					swal({
						title: "Acces Denied",
						text: "Only owner and colaborators can edit elements",
						type: "error",
						showConfirmButton: false,
						timer: 2500
					})
					break;
					case 404:
					swal({
						title: "Oppss!",
						type : "error",
						text: "We couldn't find the board, try again later",
						timer: 2500
					});
					break;
					case 500:
					swal({
						title: "Oppss!",
						type : "error",
						text: "Server problem, try again later",
						timer: 2500
					});
					break;
				}
			})
		})
	});

	form.find('button[type="submit"][name="delete"]').click(function(){
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
				json.board_id = parseInt(board.find('span.id').text());
				swal.showLoading();
				customFetch(json, "DELETE", "http://localhost:8080/Trello/BoardServlet")
				.then(r=>{
					if(r.status === 200){
						swal({
							title: "Board deleted successfully!",
							type : "success",
							showConfirmButton: false,
							timer: 2500
						});
						board.remove();
						form.closest('.modal.open').modal('close');
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
					text: "Your boards are safe",
					timer: 2500
				});
			}
		})
		.catch(err=>{console.log(err);})
	})
}