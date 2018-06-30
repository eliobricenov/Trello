$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");


$('form#boardCreate').submit(function(e) {
	e.preventDefault();
	let j = $(this).formToJSON();
	$(this).customValidate({
		table_name:{
			required:true,
			lettersonlys:true
		}
	})
	.then(r=>{
		swal.showLoading();
		customFetch(j, "POST", "http://localhost:8080/Trello/Board")
		.then(r=>{
			if(r.status === 200){ 
				swal({
					title: "Board created successfully!",
					type : "success",
					timer: 3000
				});
				$(this).closest('.modal.open').modal('close');
			}else{
				console.log(r);
			}})
		.catch(err=>{console.log(err)});

	})
});
