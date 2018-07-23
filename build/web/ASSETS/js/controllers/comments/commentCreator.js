function prepareComment(modal, data, trigger) {
    let $button = modal.find('a.comment-submit');
    let $comment = modal.find('textarea.comment');
    let $commentContainer = modal.find('div.comment-container');
    let board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
    //agregar comentario
    $button.unbind("click").on("click", function() {
        if ($comment.val() !== '') {
            data.comment_text = $comment.val();
            swal.showLoading();
            customFetch(data, "POST", `http://localhost:8080/Trello/CommentsServlet?board_id=${board_id}`)
            .then(r => {
                if (r.status === 200) {
                    swal({
                        title: "Comment created successfully!",
                        type: "success",
                        showConfirmButton: false,
                        timer: 1500
                    });
                    let c = []
                    c.push(r.data.created_comment);
                    renderComment($commentContainer, c);
                    trigger.find('span.comments').text(JSON.stringify(r.data.comments));
                    $comment.val('');
                } else if(r.status === 403){
                  swal({
                        title: "Access denied!",
                        type: "error",
                        text: "You can only add comments if you are a collaborator.",
                        timer: 1500
                    });
                }else {
                    swal({
                        title: "Oppss!",
                        type: "error",
                        text: "Seems there was a problem, try again later.",
                        timer: 3000
                    });
                }
            }).catch(err => {
                console.log(err);
            })
        }
    })
    //borrar comentario
    $(document).unbind("click").on("click", "a.comment-delete", function() {
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
                let $comment = $(this).parent().parent().parent();
                data.user_id = parseInt($comment.find('span.user_id').text());
                data.card_id = parseInt($comment.find('span.card_id').text());
                data.comment_id = parseInt($comment.find('span.comment_id').text());
                let board_id = parseInt(new URLSearchParams(window.location.search).get("board_id"));
                swal.showLoading();
                customFetch(data, "DELETE", `http://localhost:8080/Trello/CommentsServlet?board_id=${board_id}&comment_id=${data.comment_id}`)
                .then(r => {
                    switch (r.status) {
                        case 200:
                            {
                                swal({
                                    title: "Comment deleted successfully!",
                                    type: "success",
                                    showConfirmButton: false,
                                    timer: 1500
                                });
                                $(this).parent().parent().parent().remove();
                                trigger.find('span.comments').text(JSON.stringify(r.data));
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
};

function renderComment(container, comments) {
    $(container).append(`
    ${comments.map(comment => {
            return `
       <div class="row comment">
            <span class='user_id' hidden>${comment.user_id}</span>
            <span class='card_id' hidden>${comment.card_id}</span>
            <span class='comment_id' hidden>${comment.comment_id}</span>
           <div class="divider"></div>
           <div class="row">
               <br>
               <div class="col s1 m1">
                   <i class="material-icons circle medium">person</i>
               </div>
               <div class="col s9 m9">
                   <p>${comment.user_username}</p>
                   <br>
                   <textarea id="textarea1" class="materialize-textarea" style="border-bottom: none; position: fixed;" disabled maxlength="250">${comment.comment_text}</textarea>
                   <br>
               </div>
               <div class="col s1 m1">
                   <a class="secondary-content comment-delete">
                       <i class="material-icons red-text">delete</i>
                   </a>
               </div>
           </div>
       </div>`
        }).join('')}`);
}

function setComment(container, comments) {
    $(container).html(`
    ${comments.map(comment => {
            return `
       <div class="row comment">
        <div style="">
            <span class='user_id' hidden>${comment.user_id}</span>
            <span class='card_id' hidden>${comment.card_id}</span>
            <span class='comment_id' hidden>${comment.comment_id}</span>
           <div class="divider"></div>
           <div class="row">
               <br>
               <div class="col s1 m1">
                   <i class="material-icons circle medium">person</i>
               </div>
               <div class="col s9 m9">
                   <p>${comment.user_username}</p>
                   <br>
                </div>
                <div class="col s1 m1">
                   <a class="secondary-content comment-delete">
                       <i class="material-icons red-text">delete</i>
                   </a>
               </div>
            </div>
            <div class="row"
                <div class="col s2 m2">
                <p></p>
                </div>
                <div class="col s8 m8">
                <textarea id="textarea1" class="materialize-textarea" style="border-bottom: none; position: relative;margin-left: 20%; bottom:34;" disabled maxlength="250">${comment.comment_text}</textarea>
                </div>
            </div>
        </div>
       </div>`
        }).join('')}`);
}