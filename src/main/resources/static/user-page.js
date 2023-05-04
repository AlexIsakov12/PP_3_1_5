const url = "http://localhost:8080/api/user"

fetch(url).then(response => response.json()).catch(error => console.log(error))

const table = document.querySelector("tbody")
let result = ''

const showTable = (user) => {
    result +=
        `<tr>
            <td>${user.id}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(r => r.name.substring(5))}</td>
        </tr>`
    table.innerHTML = result
}

fetch(url)
    .then(response => response.json())
    .then(data => showTable(data))
    .catch(error => console.log(error))