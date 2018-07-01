$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");


$('form#boardCreate').submit(function(e) {
	e.preventDefault();
	let j = $(this).formToJSON();
	console.log(j);
	// $(this).customValidate({
	// 	table_name:{
	// 		required:true,
	// 		lettersonlys:true
	// 	},
	// 	board_description:{
	// 		required:true,
	// 		lettersonlys:true
	// 	}
	// })
	// .then(r=>{
	// 	swal.showLoading();
	// 	customFetch(j, "POST", "http://localhost:8080/Trello/Board_Creation")
	// 	.then(r=>{
	// 		if(r.status === 200){ 
	// 			swal({
	// 				title: "Board created successfully!",
	// 				type : "success",
	// 				showConfirmButton: false,
	// 				timer: 2500
	// 			});
	// 			$(this).closest('.modal.open').modal('close');
	// 			console.log(r.data);
	// 		}else{
	// 			swal({
	// 				title: "Oppss!",
	// 				type : "error",
	// 				text: "Seems there was a problem, try again later.",
	// 				timer: 3500
	// 			});
	// 		}})
	// 	.catch(err=>{console.log(err)});
	// })
});
