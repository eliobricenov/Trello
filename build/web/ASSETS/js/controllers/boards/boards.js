let container = document.querySelector('div#page');
let b_container = document.querySelector("ul#board_container");
customFetch("", "GET", "http://localhost:8080/Trello/UserServlet")
.then(res=>{
	if(res.status !== 200){
		renderNavBar("default", container);
		renderFloatingButton(container);
	}else{
		renderNavBar(res.data.name, container);
		renderFloatingButton(container);
		let title = `<li class="collection-header">
		<h2>${res.data.name}'s Boards</h2>
		</li>`;
		b_container.innerHTML = title;
		customFetch("", "GET", "http://localhost:8080/Trello/BoardServlet")
		.then(r=>{
			if(r.boards.length > 0){
				for (var i = 0; i < r.boards.length; i++) {
					renderBoard(b_container, r.boards[i]);
				}
			}else{

				// let message = document.createElement('h5');
				// message.appendChild(document.createTextNode("You don't have any boards yet, create a new one!"))
				// // container.innerHTML = "<h5> You don't have any boards yet, create a new one!<h5>"
				// container.appendChild(message);
				// let title = `<li class="collection-header">
				// <h2>You don't have any boards yet, create a new one!</h2>
				// </li>`;
				// b_container.innerHTML = title;
				// alert("You don't have any boards yet, create a new one!");
			}	
		}).catch(err=>{console.log(err);});
	}
}).catch(err=>{console.log(err)});




