function createFile(container, data){
	let file = `
	<li class="collection-item" style="background-color: transparent;">
			<span class='card_id' hidden>${data.card_id}</span>
            <span class='file_id' hidden>${data.file_id}</span>
			<a href=${data.file_url}>${data.file_name}</a>
			<a class="secondary-content file-delete"><i class="material-icons" style="color: red;">delete</i></a>
	</li>`;
	$(container).prepend(file);
}

function prepareFiles($cont, $btn, filesCont, data){

	$btn.on("change", function(e){
		let fd = new FormData();
		let file = $btn[0].files[0];
		fd.append("file", file);
		fd.append("data", JSON.stringify(data));
		let board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
		swal.showLoading();
		let datajson = {
            method: 'POST',
            body: fd,
            withCredentials: true,
            credentials: 'same-origin'
         };
		fetch(`/Trello/FileServlet?board_id=${board_id}`, datajson)
		.then(r=> r.json()).then(r=>{
			switch(r.status){
				case 200: {
					swal({
                        title: "File created successfully!",
                        type: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    createFile(filesCont, r.data.created_file);
                    $cont.find('span.files').text(JSON.stringify(r.data.files));
				}
				break;
				case 403: {
					swal({
                        title: "Access denied!",
                        type: "error",
                        text: "You can only add files if you are a collaborator or board master.",
                        timer: 1500
                    });
				}
				break;
				case 404:
				case 500:{
					swal({
                        title: "Oops",
                        type: "error",
                        text: "Seems there was a problem, try again later.",
                        timer: 1500
                    });
				}
			}
		})
		.catch(err => console.log(err));
	});

	$(document).on("click", "a.file-delete",  function(){
               swal({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            type: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
        }).then((result) => {
            if (result.value) {
                let data = {};
                let $file = $(this).parent();
                data.card_id = parseInt($file.find('span.card_id').text());
                data.file_id = parseInt($file.find('span.file_id').text());
                let board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
                swal.showLoading();
                customFetch(data, "DELETE", `/Trello/FileServlet?board_id=${board_id}&file_id=${data.file_id}`)
                .then(r => {
                  console.log(r);
                    switch (r.status) {

                        case 200:
                            {
                                swal({
                                    title: "File deleted successfully!",
                                    type: "success",
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                                $file.remove();
                                $cont.find('span.files').text(JSON.stringify(r.data));
                            }
                            break;
                        case 403:
                            {
                                swal({
                                    title: "Acces Denied",
                                    text: "You can only edit elements if you're the board master or the element's creator!",
                                    type: "error",
                                    showConfirmButton: true,
                                })
                            }
                            break;
                        case 404:
                        case 500:
                            {
                                swal({
                                    title: "Oppss!",
                                    type: "error",
                                    text: "Seems there was a problem, try again later.",
                                    timer: 2500
                                });
                            }
                            break;
                    }
                })
            } else if (result.dismiss === swal.DismissReason.cancel) {
                swal('Cancelled', 'error')
            }
        })
     });
}

function setFiles(container, files){
	if(files.length > 0 ){
			let html = files.map(file => {
	            return `
	     			<li class="collection-item" style="background-color: transparent;">
	     				<span class='card_id' hidden>${file.card_id}</span>
	            		<span class='file_id' hidden>${file.file_id}</span>
						<a href=${file.file_url}>${file.file_name}</a>
						<a class="secondary-content file-delete"><i class="material-icons" style="color: red;">delete</i></a>
					</li>
	       `
	        }).join('');

		html += `<a id="file-add" onclick="$('input#files').click()" class="center-align collection-item">Click to add a file!</a>
	             <input type="file" name="files" id="files" hidden>`;

	    $(container).html(html);

	}else{
		$(container).html(`<a id="file-add" onclick="$('input#files').click()" class="center-align collection-item">Click to add a file!</a>
	             <input type="file" name="files" id="files" hidden>`);
	}

}

