
//returns json from server
function customFetch(data, method, url) {
    switch (method) {
        case 'GET': { 
            return new Promise((resolve, reject)=>{
                let datajson = {
                    method: 'GET',
                    withCredentials: true,
                    credentials: 'same-origin'
                }; 
                fetch(url, datajson)
                .then((res) => res.json())
                .then((d) => {
                    resolve(d);
                })
                .catch((err) => {
                    reject(err);
                })
            })
        }
        break;
        case 'POST':
        {
            return new Promise((resolve, reject)=>{
              let datajson = {
                    method: 'POST',
                    body: JSON.stringify(data),
                    withCredentials: true,
                    credentials: 'same-origin'
                };  
                fetch(url, datajson)
                .then((res) => res.json())
                .then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err);
                })
            });   
        } 
        break;
        case 'DELETE':
        {
            return new Promise((resolve, reject)=>{
              let datajson = {
                    method: 'DELETE',
                    body: JSON.stringify(data),
                    withCredentials: true,
                    credentials: 'same-origin'
                };  
                fetch(url, datajson)
                .then((res) => res.json())
                .then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err);
                })
            });   
        } 
        break;
        case 'PUT':
        {
            return new Promise((resolve, reject)=>{
              let datajson = {
                    method: 'PUT',
                    body: JSON.stringify(data),
                    withCredentials: true,
                    credentials: 'same-origin'
                };  
                fetch(url, datajson)
                .then((res) => res.json())
                .then((data) => {
                    resolve(data)
                })
                .catch((err) => {
                    reject(err);
                })
            });   
        } 
        break;
    }

}