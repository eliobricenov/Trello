let container = document.querySelector('div#page');
let b_container = document.querySelector("ul#board_container");
customFetch("", "GET", "/Trello/UserServlet")
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
		customFetch("", "GET", "/Trello/BoardServlet")
		.then(r=>{
			console.log(r);
			switch(r.status){
				case 200:{
					if(r.boards.length > 0){
						for (var i = 0; i < r.boards.length; i++) {
							renderBoard(b_container, r.boards[i]);
						}
					}
				}
			}
		}).catch(err=>{console.log(err);});
	}
}).catch(err=>{console.log(err)});




