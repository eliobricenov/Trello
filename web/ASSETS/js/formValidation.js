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
		return JSON.stringify(data);
	}

	$.fn.checkInputs = function(){
		$.each($(this).find('input'), function(){
			switch(this.type){
				case 'text':{
					if ($(this).val().length == 0) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}else{
						$(this).removeClass('is-invalid').addClass('is-valid');
					}
				}
				break;
				case'password':{
					if ($(this).val().length == 0 || !isPass($(this).val())) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}else{
						$(this).removeClass('is-invalid').addClass('is-valid');
					}
				}
				break;
				case 'email':{
					if ($(this).val().length == 0 || !isEmail($(this).val())) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}else{
						$(this).removeClass('is-invalid').addClass('is-valid');
					}
				}
				break;
			}
		});

		if($(this).find('input.user_pass_original, input.user_pass_confirm').length == $(this).find('input[type=password]').length){
			if($('input.user_pass_confirm').val() !== '' && ($('input.user_pass_confirm').val() == $('input.user_pass_original').val())){
				$('input.user_pass_confirm').removeClass('is-invalid').addClass('is-valid');
			}else{
				$('input.user_pass_confirm').removeClass('is-valid').addClass('is-invalid');
			}
		}
		return this;
	}

	$.fn.setEmailListener = function(){
		if($(this).is('form')){
			$.each($(this).find('input.user_email'), function(){
				$(this).on('focus', function(){
					if ($(this).val().length == 0) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}
					$(this).keyup(function(){
						if ($(this).val().length > 0 && isEmail($(this).val())) {
							$(this).removeClass('is-invalid').addClass('is-valid');
						}else{
							$(this).removeClass('is-valid').addClass('is-invalid');
						}
					})
				})
			});
		}
		return this;
	}

	$.fn.setTextListener = function(){
		if($(this).is('form')){
			$.each($(this).find('input.user_personal'), function(){
				$(this).on('focus', function(){
					if ($(this).val().length == 0) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}
					$(this).keyup(function(){
						if ($(this).val().length > 0) {
							$(this).removeClass('is-invalid').addClass('is-valid');
						}else{
							$(this).removeClass('is-valid').addClass('is-invalid');
						}
					})
				});
			})
		}
		return this;
	}

	$.fn.setPasswordsListener = function(){
		if($(this).is('form')){
			if($(this).find('input.user_pass_original, input.user_pass_confirm').length >= 2){
				$('input.user_pass_original').on('focus', function(){
					if ($(this).val().length == 0) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}
					$(this).keyup(function(){
						if ($(this).val().length > 0 && isPass($(this).val())) {
							$(this).removeClass('is-invalid').addClass('is-valid');
						}else{
							$(this).removeClass('is-valid').addClass('is-invalid');
						}
					})
				});
				$('input.user_pass_confirm').on('focus', function(){
					if ($(this).val().length == 0 || !($(this).val() == $('input.user_pass_original').val())) {
						$(this).removeClass('is-valid').addClass('is-invalid');
					}else{
						$(this).removeClass('is-invalid').addClass('is-valid');
					}
					$(this).keyup(function(){
						if($(this).val() == $('input.user_pass_original').val()){
							$(this).removeClass('is-invalid').addClass('is-valid');
						}else{
							$(this).removeClass('is-valid').addClass('is-invalid');
						}
					})
				})
			}else{
				if($(this).find('input.user_pass_original').length){ 
					$('input.user_pass_original').on('focus', function(){
						if ($(this).val().length == 0) {
							$(this).removeClass('is-valid').addClass('is-invalid');
						}
						$(this).keyup(function(){
							if ($(this).val().length > 0 && isPass($(this).val())) {
								$(this).removeClass('is-invalid').addClass('is-valid');
							}else{
								$(this).removeClass('is-valid').addClass('is-invalid');
							}
						})
					});
				}
			}
		}
		return this;
	}
	
	$.fn.chek = function(){
		$(this).find('input').hasClass('is-valid');
	}
	

	$.fn.prepareListeners = function(){
		if($(this).is('form')){
			$(this)
			.setEmailListener()
			.setPasswordsListener()
			.setTextListener();
		}
		return this;
	}
	
	$.fn.validate = function(fn){
		let form = this;
		if($(this).is('form')){
			$(this).prepareListeners()
			.submit(function(e){
				e.preventDefault();
				$(this).checkInputs();
				let flag = (form.find('input.is-valid').length == form.find('input').length)
				if(flag){
					$.proxy(fn, form)();
				}
			})
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






