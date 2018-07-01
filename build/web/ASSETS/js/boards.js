let container = document.querySelector('div#page');

customFetch("", "POST", "http://localhost:8080/Trello/GetData")
.then(res=>{
	console.log(res);
	if(res.status !== 200){
		renderNavBar("default", container);
		renderFloatingButton(container);
	}else{
		renderNavBar(res.data.name, container);
		renderFloatingButton(container);
	}
}).catch(err=>{console.log(err)});




