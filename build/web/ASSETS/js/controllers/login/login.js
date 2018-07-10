let form = document.querySelector('form');

$('form').submit(function(e){
    e.preventDefault();
    let fd = $('form').formToJSON();
    $(this).customValidate({
        username: {
            required: true,
            lettersonlys: true
        },
        password: {
            required: true
        }
    }).then(r => {
        swal({
            title: 'Processing data..'
        });
        swal.showLoading();
        customFetch(fd, "POST", "http://localhost:8080/Trello/LoginServlet")
            .then(r => {
                switch (r["status"]) {
                    case 200:
                        swal({
                            title: "Welcome Back!",
                            type: "success"
                        }).then((ok) => {
                            if (ok) {
                                window.location.href = "http://localhost:8080/Trello/views/board.html";
                            }
                        })
                        break;

                    case 403:
                        swal({
                            title: "OH!",
                            text: "Wrong password, try again please.",
                            type: "error"
                        })
                        break;
                    case 409:
                        swal({
                            title: "Hi!",
                            text: "Seems like you're not registered, click the OK button to sign up.",
                            type: "error"
                        }).then((ok) => {
                            if (ok) {
                                window.location.href = "http://localhost:8080/Trello/views/signup.html";
                            }
                        })
                        break;
                    case 500:
                        swal({
                            title: "Oops!",
                            text: "Mistakes were made, respawn and try again",
                            type: "error"
                        })
                        break;
                }
            })
            .catch(err => { console.log(err) });
    })
})
    