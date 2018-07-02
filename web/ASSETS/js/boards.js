let container = document.querySelector('div#page');
let b_container = document.querySelector("ul#board_container");
customFetch("", "POST", "http://localhost:8080/Trello/GetUserData")
.then(res=>{
	if(res.status !== 200){
		renderNavBar("default", container);
		renderFloatingButton(container);
	}else{
		renderNavBar(res.data.name, container);
		renderFloatingButton(container);
		customFetch("", "POST", "http://localhost:8080/Trello/GetBoardsData")
		.then(r=>{
			console.log(r);
			for (var i = 0; i < r.boards.length; i++) {
				renderBoard(b_container, r.boards[i]);
			}
		}).catch(err=>{console.log(err);});
	}
}).catch(err=>{console.log(err)});




