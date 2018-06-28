function customFetch(data, method, url, cb) {
    switch (method) {
        case 'GET':
            {
                fetch(url, {
                    method: 'GET'
                })
                .then((res) => res.json())
                .then((data) => {
                    cb(data);
                })
                .catch((err) => {
                    alert('Error while request...' + err.message);
                })
                break;
            }

        case 'POST':
            {
                let datajson = {
                    method: 'POST',
                    body: JSON.stringify(data),
                    withCredentials: true,
                    credentials: 'same-origin',
                    headers: {
                        "Content-Type" : "application/json"
                    }
                };
                fetch(url, datajson)
                .then((res) => res.json())
                .then((data) => {
                    cb(data);
                })
                .catch((err) => {
                    console.log(err);
                })
                break;
            }
    }

}