$.validator.addMethod("lettersonlys", function(value, element) {
	return this.optional(element) || /^[A-Za-z](([a-zA-Z])*[^#&<>\"~;$^%{}?]{1,20}?)*$/.test(value);
}, "Invalid format. Example: MyBoard, MyBoard 1, My_Board_1");

function renderNavBar(username, container) {
	//tag creation (first in, first out)
	let nav = document.createElement("nav"); 
	let navDiv = document.createElement("div"); 
	let navLink = document.createElement("a");
	
	//attributes assignment
	navDiv.className="navbar-fixed";
	navLink.className="brand-logo center";
	
	//appending
	container.appendChild(nav);
	nav.appendChild(navDiv);
	navDiv.appendChild(navLink);
	navLink.appendChild(document.createTextNode(`${username}`));
}

function renderFloatingButton(container){
	//tag creation 
	let mainDiv = document.createElement("div"); 
	let mainB = document.createElement("a");
	let create_icon = document.createElement("i"); 
	let ul = document.createElement("ul");
	let li = document.createElement("li");
	let createB = document.createElement("a");
	let add_icon = document.createElement("i");
	
	//attributes assignment
	mainDiv.className = "fixed-action-btn";
	mainB.className = "btn-floating btn-large blue";
	mainB.setAttribute("id", "showButton");
	create_icon.className = "material-icons"
	createB.className = "btn-floating red modal-trigger";
	createB.setAttribute("id", "createButton");
	createB.href = "#modal1";
	add_icon.className= "material-icons";
	
	//appending
	container.appendChild(mainDiv);
	mainDiv.appendChild(mainB);
	mainB.appendChild(create_icon);
	create_icon.appendChild(document.createTextNode("create"));
	mainDiv.appendChild(ul);
	ul.appendChild(li);
	li.appendChild(createB);
	createB.appendChild(add_icon);
	add_icon.appendChild(document.createTextNode("add"));
}

function renderBoard(container, data){
	//tag creation
	let collectionItem = document.createElement("li");
	let avatarIcon = document.createElement("i");
	let title = document.createElement("a");
	let description = document.createElement("p");
	let id = document.createElement("span");
	let optionsLink = document.createElement("a");
	let optionsIcon = document.createElement("i");

	//attributes assignment 
	collectionItem.className = "collection-item avatar";
	avatarIcon.className = "material-icons circle";
	avatarIcon.style.backgroundColor = data.board_color;
	title.className = "title";
	title.href = "#";
	description.className= "paragraph";
	id.className= "id";
	id.hidden = true;
	optionsLink.className = "secondary-content modal-trigger";
	optionsLink.href = "#modal_update"
	optionsIcon.className = "material-icons";

	//appending
	avatarIcon.appendChild(document.createTextNode("event_note"));
	title.appendChild(document.createTextNode(data.board_name));
	description.appendChild(document.createTextNode(data.board_description));
	id.appendChild(document.createTextNode(data.board_id));
	optionsIcon.appendChild(document.createTextNode("more_vert"));
	optionsLink.appendChild(optionsIcon);

	collectionItem.appendChild(avatarIcon);
	collectionItem.appendChild(title);
	collectionItem.appendChild(description);
	collectionItem.appendChild(id);
	collectionItem.appendChild(optionsLink);
	container.appendChild(collectionItem);

}


function updateBoard(form, board){
	// console.log(board.children("i.material-icons.circle").css('background-color'));
	// console.log(board.children("a.title").text());
	// console.log(board.children("p.paragraph").text());
	// console.log(form.closest('.modal.open'));

	form.find('button[type="submit"][name="update"]').click(function(){
		form.customValidate({
			table_name:{
				required:true,
			}
		})
		.then(r=>{
			let json = form.formToJSON();
			json.board_id = board.find('span.id').text();
			swal.showLoading();
			customFetch(json, "POST", "http://localhost:8080/Trello/Board_Update")
			.then(r=>{
				if(r.status === 200){
					swal({
						title: "Board Updated successfully!",
						type : "success",
						showConfirmButton: false,
						timer: 2500
					});
					board.children('i.material-icons.circle').css("background-color", json.board_color);
					board.children('a.title').text(json.board_name);
					board.children('p.paragraph').text(json.board_description);
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
				json.board_id = board.find('span.id').text();
				swal.showLoading();
				customFetch(json, "POST", "http://localhost:8080/Trello/Board_Delete")
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