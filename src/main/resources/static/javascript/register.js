
const registerForm = document.getElementById('register-form')
const registerUsername = document.getElementById('register-username')
const registerPassword = document.getElementById('register-password')
const registerName = document.getElementById('register-name')
const registerLastname = document.getElementById('register-lastname')
const registerPhone= document.getElementById('register-phone')
const registerEmail = document.getElementById('register-email')

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/v1/users'


const handleSubmit = async (e) =>{
    e.preventDefault()

    let bodyObj = {
        username: registerUsername.value,
        password: registerPassword.value,
        name: registerName.value,
        lastname: registerLastname.value,
        phonenumber: registerPhone.value,
        email: registerEmail.value 
    }

    const response = await fetch(`${baseUrl}/register`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))

    const responseArr = await response.json()

    if (response.status === 200){
        window.location.replace(responseArr[0])
    }
}

registerForm.addEventListener("submit", handleSubmit)


