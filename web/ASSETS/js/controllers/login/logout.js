// console.log(document.querySelector("form#searchForm"));

function logout(){
	customFetch("", "POST", "http://localhost:8080/Trello/LogoutServlet")
	.then(r=>{
		if(r.status === 200){
			swal({
				title: "Bye!",
				text : "Hope to see you soon",
				type: "success",
				showConfirmButton: false,
				timer: 2000,
				onClose: () => {window.location.href = "http://localhost:8080/Trello/views/index.html"}
			});
		}else{
			swal({
				title: "Oppss!",
				type : "error",
				text: "Seems there was a problem, try again later.",
				timer: 1500
			});
		}
	})
	.catch(err=>{console.log(err);})
}