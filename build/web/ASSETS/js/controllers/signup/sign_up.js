let form = document.querySelector('form');

$('form').submit(function(e) {
    e.preventDefault();
    fd = $('form').formToJSON();
    $(this).customValidate({
        name: {
            required: true,
            lettersonlys: true
        },
        last_name: {
            required: true,
            lettersonlys: true
        },
        username: {
            required: true,
            lettersonlys: true
        },
        email: {
            required: true
        },
        password: {
            required: true
        }
    }).then(r => {
        let fd = $(this).formToJSON();
        console.log(fd);
        swal.showLoading();
        customFetch(fd, "POST", "http://localhost:8080/Trello/SignUpServlet")
            .then(r => {
                console.log(r.status);
                switch (r["status"]) {
                    case 200:
                        swal({
                            title: "Welcome To The Crew!",
                            type: "success"
                        }).then((ok) => {
                            if (ok) {
                                window.location.href = "http://localhost:8080/Trello/index.html";
                            }
                        })
                        break;
                    case 409:
                        swal({
                            title: "Suspicious..",
                            text: "Seems like you're already registered under that username or email",
                            type: "error"
                        })
                    break;
                    case 500:
                        swal({
                            title: "Oops!",
                            text: "register error",
                            type: "error"
                        })
                        break;

                    case 501:
                        swal({
                            title: "Oops!",
                            text: "server error",
                            type: "error"
                        })
                        break;
                }
            })
            .catch(err => { console.log(err); })
        }).catch(err => { console.log(err) })
});