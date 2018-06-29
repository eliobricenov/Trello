let container = document.querySelector('div#page');

// customFetch("", "POST", "http://localhost:8080/Trello/GetData")
// .then(res=>{
// 	if(res.status !== 200){
// 		renderNavBar("default");
// 		renderFloatingButton();
// 	}else{
// 		renderNavBar(res.data.name);
// 		renderFloatingButton();
// 	}
// }).catch(err=>{console.log(err)});
let data = "";
let datajson = {
	method: 'POST',
	body: JSON.stringify(data),
	withCredentials: true,
	credentials: 'same-origin',
	headers: {
		"Content-Type" : "application/json"
	}
};

fetch("http://localhost:8080/Trello/GetData", datajson)
.then((res) => res.json())
.then((data) => {
	console.log(data);
})
.catch((err) => {
});

function renderNavBar(username) {
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

function renderFloatingButton(){
	//tag creation (first in, last out)
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
	create_icon.className = "material-icons"
	createB.className = "btn-floating red modal-trigger";
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


