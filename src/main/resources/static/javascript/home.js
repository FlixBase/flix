const noteForm = document.getElementById('note-form')
const noteDate = document.getElementById('note-date')
const noteName = document.getElementById('note-name')
const noteOrderid = document.getElementById('note-orderid')
const noteLastname = document.getElementById('note-lastname')
const noteCustomerid = document.getElementById('note-customerid')
const noteProductid = document.getElementById('note-productid')
const noteCity = document.getElementById('note-city')
const noteState = document.getElementById('note-state')
const noteZip= document.getElementById('note-zip')
const noteRegion = document.getElementById('note-region')
const noteSales = document.getElementById('note-sales')
const noteQuantity= document.getElementById('note-quantity')

//cookie array
const cookieArr = document.cookie.split("=")
const user_id = cookieArr[1];

const headers = {
    'Content-Type':'application/json'
}

const baseUrl = 'http://localhost:8080/api/v1/movies'

function handleLogout(){
    let c = document.cookie.split(";");
    for(let i in c){
        document.cookie = /^[^=]+/.exec(c[i])[0]+"=;expires=Thu, 01 Jan 2030 00:00:00 GMT"
    }
}

const handleSubmit = async (e) =>{
    e.preventDefault();
    //console.log("Reach this point HAHA"); 

    let bodyObj = {
        orderid: noteOrderid.value,
        name: noteName.value,
        date: noteDate.value,
        lastname:noteLastname.value,
        customerid:noteCustomerid.value,
        productid:noteProductid.value,
        city:noteCity.value,
        state:noteState.value,
        zip:noteZip.value,
        region:noteRegion.value,
        sales:noteSales.value,
        quantity:noteQuantity.value
    }

    const response = await fetch(`${baseUrl}/note/${user_id}`, {
        method: "POST",
        body: JSON.stringify(bodyObj),
        headers: headers
    })
        .catch(err => console.error(err.message))
        //console.log("Reach this point HAHA"); 

    if (response.status === 200){
        window.location.replace(responseArr[0])
    }
}

noteForm.addEventListener("submit", handleSubmit)


