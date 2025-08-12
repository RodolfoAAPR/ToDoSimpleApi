const url = "http://localhost:8080/task/user";

function hideLoader(){
    document.getElementById("loading").style.display = "none";
}

function show(tasks){

    let tab = `<thead>
                    <th scope="col">#<th>
                    <th scope="col">Descrição</th>
                    <th scope="col">Usuário</th>
                    <th scope="col">Username</th>
                    <th scope="col">User ID</th>
                <thead>`;

                for (let task of tasks) {
                    tab += `
                        <tr>
                            <td scope="row">${task.id}</td>
                            <td>${task.description}</td>
                        </tr>    
                    `;
                }

    document.getElementById("tasks").innerHTML = tab;
}

async function getTasks(){
    let key = "Authorization";
    const response = await fetch(taskEndpoint, {
        method: "GET",
        headres: new Headers({
            Authorization: localStorage.getItem(key),
        }),
    });

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    show(data);
}

document.addEventListene("DOMContentLoaded", function (event){
    if (!localStorage.getItem("Authorization"))
        window.location = "view/login.html";
});

getTasks();
