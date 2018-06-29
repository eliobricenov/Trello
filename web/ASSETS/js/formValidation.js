//jQuery Functions

(function($){
	
	$.fn.formToJSON = function(){
		let data = {};
		let json = {};
		if($(this).is('form')){
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






