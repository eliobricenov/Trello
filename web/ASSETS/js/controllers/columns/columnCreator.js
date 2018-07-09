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
			console.log(j);
		swal.showLoading();
		customFetch(j, "POST", "http://localhost:8080/Trello/ColumnServlet")
		.then(r=>{
			console.log(r);
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
			}else{
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

