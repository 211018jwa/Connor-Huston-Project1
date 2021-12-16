window.addEventListener('load', async () => {

    let res = await fetch ('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObject = await res.json();

        if(userObject.userRole === 'Employee') {
            window.location.href = 'Employee-homepage.html';
        }
    }
    else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    populateTableWithReimbursements();
});

let logoutButton = document.querySelector('#logout');

logoutButton.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }
})

async function populateTableWithReimbursements() {
    let res = await fetch('http://localhost:8080/reimbursements', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector('#reimbursement-table tbody');
    tbodyElement.innerHTML = '';

    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        let td1 = document.createElement('td');
        td1.innerHTML = reimbursement.id;

        let td2 = document.createElement('td');
        td2.innerHTML = reimbursement.amount;

        let td3 = document.createElement('td');
        td3.innerHTML = reimbursement.submitted;

        let td4 = document.createElement('td');

        if (reimbursement.resolved != 0) {
            td4.innerHTML = reimbursement.resolved;
        }
        else {
            td4.innerHTML = "Reimbursement has not been resolved";
        }

        let td5 = document.createElement('td');

        if (reimbursement.status != 0) {
            td5.innerHTML = reimbursement.status;
        }
        else {
            td5.innerHTML = "Pending";
        }

        let td6 = document.createElement('td');
        td6.innerHTML = reimbursement.type;

        let td7 = document.createElement('td');
        td7.innerHTML = reimbursement.description;

        let td8 = document.createElement('td');
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Image';
        td8.appendChild(viewImageButton);

        viewImageButton.addEventListener('click', () => {

            let reimbursementImageModal = document.querySelector('#reimbursement-image-modal');

            let modalCloseElement = reimbursementImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimbursementImageModal.classList.remove('is-active');
            });

            let modalContentElement = reimbursementImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http:/localhost:8080/reimbursements/${reimbursement.id}/image`);
            modalContentElement.appendChild(imageElement);

            reimbursementImageModal.classList.add('is-active');
        })

        let td9 = document.createElement('td');
        td9.innerHTML = reimbursement.author;

        let td10 = document.createElement('td');

        if(reimbursement.resolverId != 0) {
            td10.innerHTML = reimbursement.resolver;
        }
        else {
            td10.innerHTML = "Reimbursement has not been resolved";
        }

        let td11 = document.createElement('td');

        let td12 = document.createElement('td');

        let approveButton = document.createElement('button');
        approveButton.innerText = 'Approve';
        td11.appendChild(approveButton);

        let denyButton = document.createElement('button');
        denyButton.innerText = 'Deny';
        td12.appendChild(denyButton);

        approveButton.addEventListener('click', async () => {

            let reimbursementStatus = document.querySelector('#status');

            let res = await fetch (`http://localhost:8080/reimbursements/${reimbursement.id}`, {

                credentials: 'include',
                method: 'PUT',
                body: JSON.stringify({
                    status: "Approved"
                })
            });

            if (res.status === 200) {
                populateTableWithReimbursements();
            }
        });

        denyButton.addEventListener('click', async () => {

            let reimbursementStatus = document.querySelector('#status');

            let res = await fetch (`http://localhost:8080/reimbursements/${reimbursement.id}`, {

                credentials: 'include',
                method: 'PUT',
                body: JSON.stringify({
                    status: "Denied"
                })
            });

            if (res.status === 200) {
                populateTableWithReimbursements();
            }
        });

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.appendChild(td10);
        tr.appendChild(td11);
        tr.appendChild(td12);

        tbodyElement.appendChild(tr);

    }
}