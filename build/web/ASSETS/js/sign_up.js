let form = document.querySelector('form');

form.addEventListener('submit', e => {
    e.preventDefault();
    fd = $('form').formToJSON();
    let config = {
        "method": "POST",
        "headers": {
            "Content-Type": "application/json"
        },
        "body": fd
    };
    swal({
        title: 'Processing data..'
    });
    swal.showLoading();
    fetch('http://localhost:8080/Trello/SignUp', config)
        .then(response => {
            return response.json();
        }).then(r => {
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
                        text: "Seems like you're already registered..",
                        type: "error"
                    }).then((ok) => {
                        if (ok) {
                            window.location.href = "http://localhost:8080/Trello/index.html";
                        }
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
        }).catch(error => {
            console.log(error);
        });
})