function prepareComment(modal, data, trigger) {
    let $button = modal.find('a.comment-submit');
    let $comment = modal.find('textarea.comment');
    let $commentContainer = modal.find('div.comment-container');
        $button.unbind("click").on("click", function () {
            if ($comment.val() !== '') {
                data.comment_text = $comment.val();
                swal.showLoading();
                customFetch(data, "POST", "http://localhost:8080/Trello/CommentsServlet")
                    .then(r => {
                        if (r.status === 200) {
                            swal({
                                title: "Comment created successfully!",
                                type: "success",
                                showConfirmButton: false,
                                timer: 1500
                            });
                            renderComment($commentContainer, r.data);
                            $comment.val('');
                        } else {
                            form[0].reset();
                            swal({
                                title: "Oppss!",
                                type: "error",
                                text: "Seems there was a problem, try again later.",
                                timer: 1500
                            });
                        }
                    })
                    .catch(err => {
                        console.log(err);
                    })
            }
        })
};


function renderComment(container, comments) {
    $(container).append(`
    ${comments.map(comment => {
            return `
       <div class="row">
           <div class="divider"></div>
           <div class="row">
               <br>
               <div class="col s2 m2">
                   <i class="material-icons circle medium">person</i>
               </div>
               <div class="col s9 m9">
                   <p>${comment.user_username}</p>
                   <br>
                   <textarea id="textarea1" class="materialize-textarea" style="border-bottom: none; position: fixed;" disabled maxlength="250">${comment.comment_text}</textarea>
                   <br>
               </div>
               <div class="col s1 m1">
                   <a href="#!" class="secondary-content comment-delete">
                       <i class="material-icons red-text">delete</i>
                   </a>
               </div>
           </div>
           <div class="row">
               <p class="tiny-text right-align">21/08/18 12:30am</p>
           </div>
           <div class="divider"></div>

       </div>`
        }).join('')}`);

    $(document).unbind("click").on("click", "a.comment-delete", function () {
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
                swal(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                )
                $(this).parent().parent().parent().remove();
            } else if (
                result.dismiss === swal.DismissReason.cancel
            ) {
                swal(
                    'Cancelled',
                    'Your imaginary file is safe :)',
                    'error'
                )
            }
        })
    });
}