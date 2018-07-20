let c_container = document.querySelector('div#container');

$('form#column_create').submit(function(e) {
	e.preventDefault();
	let form = $(this);
	$(this).customValidate({
		column_name:{
			required:true,
			lettersonlys:true
		}
	}).then(r=>{
		let j = form.formToJSON();
		j.board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
		swal.showLoading();
		customFetch(j, "POST", `http://localhost:8080/Trello/ColumnServlet?board_id=${j.board_id}`)
		.then(r=>{
			if(r.status === 200){ 
				swal({
					title: "Column created successfully!",
					type : "success",
					showConfirmButton: false,
					timer: 1500
				});
				$(this).closest('.modal.open').modal('close');
				form[0].reset();
				renderColumn(r.data, c_container);
			}else if(r.status === 403){
				swal({
					title: "Acces Denied",
					text: "Only board master and board's collaborators can add columns!",
					type: "error",
					showConfirmButton: true,
				})
				form.get(0).reset();
				$(this).closest('.modal.open').modal('close');
			}else{
				swal({
					title: "Oppss!",
					type : "error",
					text: "Seems there was a problem, try again later.",
					timer: 1500
				});
				form.get(0).reset();
			}})
		.catch(err=>{console.log(err)});
	})
	.catch(err=>{console.log(err)})
})

