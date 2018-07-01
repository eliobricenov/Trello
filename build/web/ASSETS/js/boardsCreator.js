$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");

let board_container = document.querySelector("ul#board_container");

$('form#boardCreate').submit(function(e) {
	e.preventDefault();
	let j = $(this).formToJSON();
	console.log(j);
	$(this).customValidate({
		table_name:{
			required:true,
			lettersonlys:true
		},
		board_description:{
			required:true,
			lettersonlys:true
		},
		board_color:{
			required:true
		}
	})
	.then(r=>{
		swal.showLoading();
		customFetch(j, "POST", "http://localhost:8080/Trello/Board_Creation")
		.then(r=>{
			if(r.status === 200){ 
				swal({
					title: "Board created successfully!",
					type : "success",
					showConfirmButton: false,
					timer: 2500
				});
				$(this).closest('.modal.open').modal('close');
				renderBoard(board_container, r.data);
			}else{
				swal({
					title: "Oppss!",
					type : "error",
					text: "Seems there was a problem, try again later.",
					timer: 3500
				});
			}})
		.catch(err=>{console.log(err)});
	})
});

$('form#edit_modal').submit(function(e) {
	e.preventDefault();
	let j = $(this).formToJSON();
	$(this).customValidate({
		table_name:{
			required:true,
			lettersonlys:true
		},
		board_description:{
			required:true,
			lettersonlys:true
		},
		board_color:{
			required:true
		}
	})
	.then(r=>{
		console.log(j);
	})
	.catch(err=>{console.log(err)});
});