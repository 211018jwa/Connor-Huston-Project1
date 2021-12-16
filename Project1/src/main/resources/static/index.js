window.addEventListener('load', async () => {

    console.log('EXECUTED');

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include'
    });

    if (res.status == 200) {
        let userObject = await res.json();

        if (userObject.userRole === 'Employee') {
            window.location.herf = 'Employee-homepage.html';
        }
        else if (userObject.userRole === 'Finance Manager') {
            window.location.herf = 'Finance-Manager-homepage.html';
        }
    }
});


let loginButton = document.querySelector('#login-button');

loginButton.addEventListener('click', loginButtonClicked);

function loginButtonClicked() {
    login();
}

async function login() {
    let usernameInput = document.querySelector('#username');
    let passwordInput = document.querySelector('#password');

    try {
        let res = await fetch('http://localhost:8080/login', {
            method: 'POST',
            credentials: 'include',
            body: JSON.stringify({
                username: usernameInput.value,
                password: passwordInput.value
            })
        });

        let data = await res.json();

        if (res.status === 400) {
            let loginErrorMessage = document.createElement('p');
            let loginDiv = document.querySelector('#login-error');

            loginDiv.innerHTML = '';

            loginErrorMessage.innerHTML = data.message;
            loginErrorMessage.style.color = 'orange';
            loginDiv.appendChild(loginErrorMessage);
        }

        if (res.status === 200) {
            console.log(data.role);
            if (data.role === 'Employee') {
                window.location.href = 'Employee-homepage.html';
            }
            else if (data.role === 'Finance Manager') {
                window.location.href = 'Finance-Manager-homepage.html';
            }
        }
    } catch(err) {
        console.log(err);
    }
}