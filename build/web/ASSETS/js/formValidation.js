//jQuery Functions
// requires jquery.validate.min.js
(function($){
	
	$.fn.formToJSON = function(){
		let data = {};
		let json = {};
		if($(this).is('form')){
			$(this).find($('input[type="text"]').each(function() {
				$(this).val($.trim($(this).val()));
			}));
			$.each($(this).find('input.include'), function(){
					if(this.name && this.value){ //are not empty{
						if(this.type === 'checkbox' && this.checked){
							data[this.name] = (data[this.name] || []).concat(this.value || ''); //check if exists
						}else{
							data[this.name]= this.value;
						}
					}
				});
		}
		json["data"] = data;
		return data;
	}

	$.fn.customValidate = function(r){
		if($(this).is('form')){
			return new Promise((resolve, reject)=>{
				let params = {
					rules: r,
					errorClass: "invalid",
					validClass: "valid",
					errorPlacement: function(error, element) {
						$(element)
						.closest("form")
						.find("label[for='" + element.attr("id") + "']")
						.attr('data-error', error.text());
					}
				}
				$(this).validate(params);

				if($(this).valid()){
					resolve("OK");
				}else{
					reject(new Error("not valid"));
				}
			});
		}else{
			return new Error("Element is not a form");
		}
		
	}



	
})(jQuery);

//Vanilla Helpers

function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}

function isPass(pass) {
	var regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/
	return regex.test(pass);
}

function capitalizeFirstLetter(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}






