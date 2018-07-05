let mainContainer = document.querySelector('div.nav-container');
let container = document.querySelector('div.container');
let myparams = new URLSearchParams(window.location.search);
let board_id = myparams.get("board_id");

 if(board_id !== '' || board_id !== undefined){
 	customFetch("", "POST", "http://localhost:8080/Trello/GetUserData")
 	.then(res=>{
 		if(res.status !== 200){
 			renderNavBar("default", mainContainer);
 			renderFloatingButton(mainContainer);
 		}else{
 			renderNavBar(res.data.name, mainContainer);
 			renderFloatingButton(mainContainer);
 			customFetch("", "GET", "http://localhost:8080/Trello/GetColumnsData?board_id=" + board_id)
 			.then(r => {
 				r.columns.forEach(column =>{
 					renderColumn(column, container);
 				})
 			})
 			.catch(err => { console.log(err); })
 		}
 	}).catch(err=>{console.log(err)});
 }else{
 	console.log("NO ID");
 }
