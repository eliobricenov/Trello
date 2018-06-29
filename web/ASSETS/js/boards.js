$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");

$("form#boardCreate").validate({
	rules: {
		table_name:{
			required:true,
			lettersonlys:true
		},
	},
	errorClass: 'invalid',
	validClass: "valid",
	errorPlacement: function(error, element) {
		$(element)
		.closest("form")
		.find("label[for='" + element.attr("id") + "']")
		.attr('data-error', error.text());
	}
});
$('form#boardCreate').submit(function(e) {
	e.preventDefault();
	$('input[type="text"]').each(function() {
		$(this).val($.trim($(this).val()));
	});
	$(this).validate();
	let j = $(this).formToJSON();
	if ($(this).valid()) {
		customFetch(j, "POST", "http://localhost:8080/Trello/Board")
		.then(r=>{
			if(r.status === 200){ 
			alert("Board created successfully");
			// $(this).closest("div.modal").close();
		}else{
			console.log(r);
		}})
		.catch(err=>{console.log(err)});
		console.log(j);
		// $(this).closest("div.modal").closeModal();
	}
});
