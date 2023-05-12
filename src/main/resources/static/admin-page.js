const url = "http://localhost:8080/api/admin/";
const table = document.getElementById("main-tbody");
const roles = [{id: 1, name: 'ROLE_USER'}, {id: 2, name: 'ROLE_ADMIN'}]
let result = ''
let id = 0

const loadTable = () => {
    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            result = ''
            showTable(data);
        })
        .catch((error) => console.log(error));
};

const showTable = (users) => {
    Array.from(users).forEach(
        (user) =>
            (result += `<tr>
          <td>${user.id}</td>
          <td>${user.firstName}</td>
          <td>${user.lastName}</td>
          <td>${user.email}</td>
          <td>${user.roles.map((r) => r.name.substring(5))}</td>
          <td><a class="editButton btn btn-primary">Edit</a></td>
          <td><a class="deleteButton btn btn-danger">Delete</a></td>
        </tr>`)
    );
    table.innerHTML = result;
};

loadTable();

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

const newFirstName = document.getElementById("newName")
const newLastName = document.getElementById("newLastName")
const newUsername = document.getElementById("newUsername")
const newEmail = document.getElementById("newEmail")
const newPassword = document.getElementById("newPassword")
const newRoles = document.getElementById("newRoles")
const newUserForm = document.getElementById("newUserForm")

newUserForm.addEventListener('submit', e => {
    e.preventDefault()
    let selectedRoles = Array.from(newRoles.selectedOptions, option => ({id: parseInt(option.value), name: option.text}));
    fetch(url, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName: newFirstName.value,
            lastName: newLastName.value,
            username: newUsername.value,
            email: newEmail.value,
            password: newPassword.value,
            roles: selectedRoles
        })
    })
        .then(response => response.json())
        .then(data => showTable(data))
        .then(newUserForm.reset())
        .then(() => {
            $('.nav-tabs a:first').tab('show')
        })
        .then(loadTable)
        .catch(error => console.log(error))
})


const editId = document.getElementById("editId")
const editName = document.getElementById("editName")
const editLastName = document.getElementById("editLastName")
const editEmail = document.getElementById("editEmail")
const editUsername = document.getElementById("editUsername")
const editPassword = document.getElementById("editPassword")
const editRoles = document.getElementById("editRoles")

const editForm = document.getElementById("editModal")
const editModal = new bootstrap.Modal(document.getElementById("editModal"))

on(document, 'click', '.editButton', e => {
    const string = e.target.parentNode.parentNode
    id = string.firstElementChild.innerHTML
    fetch(url + id, {
        method: 'GET'
    }).then(response => response.json())
        .then(data => userInfo(data))
        .catch(error => console.log(error))
    const userInfo = (user) => {
        editId.value = user.id
        editName.value = user.firstName
        editLastName.value = user.lastName
        editEmail.value = user.email
        editUsername.value = user.username
        editPassword.value = user.password
        editRoles.innerHTML = `
        <option value=${roles[0].id}>User</option>
        <option value=${roles[1].id}>Admin</option>\``
    }
    editModal.show()
})

editForm.addEventListener('submit', (e) => {
    e.preventDefault()
    let selectedRoles = Array.from(editRoles.selectedOptions, option => ({id: parseInt(option.value), name: option.text}));
    fetch(url + id, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: id,
            firstName: editName.value,
            lastName: editLastName.value,
            email: editEmail.value,
            username: editUsername.value,
            password: editPassword.value,
            roles: selectedRoles
        })
    })

        .then(data => showTable(data))
        .then(loadTable)
        .catch(error => console.log(error))

    editModal.hide()
})


const deleteId = document.getElementById("deletedId")
const deleteName = document.getElementById("deletedName")
const deleteLastName = document.getElementById("deletedSurname")
const deleteEmail = document.getElementById("deletedEmail")
const deleteUsername = document.getElementById("deletedUsername")
const deleteRoles = document.getElementById("deletedRoles")

const deleteForm = document.getElementById("deleteModal")
const deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"))


on(document, 'click', '.deleteButton', e => {
    const string = e.target.parentNode.parentNode
    id = string.firstElementChild.innerHTML
    fetch(url + id, {
        method: 'GET'
    }).then(response => response.json())
        .then(data => userInfo(data))
        .catch(error => console.log(error))
    const userInfo = (user) => {
        deleteId.value = user.id
        deleteName.value = user.firstName
        deleteLastName.value = user.lastName
        deleteEmail.value = user.email
        deleteUsername.value = user.username
        deleteRoles.value = user.roles.map((r) => r.name.substring(5))
    }
    deleteModal.show()
})

deleteForm.addEventListener('submit', (e) => {
    e.preventDefault()
    fetch(url + id, {
        method: 'DELETE'
    })
        .then(data => showTable(data))
        .then(loadTable)
        .catch(error => console.log(error))
    deleteModal.hide()
})
