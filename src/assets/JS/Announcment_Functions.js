const onlyNumbersRegex = /^\d+$/;
const onlyLettersRegex = /^[a-zA-Z]+$/;
const numbersFloatRegex = /^\d+[.,]?\d*$/;
function checkEmpty() {
    const requiredElements = document.querySelectorAll('.required');
    let allFieldsFilled = true;
    requiredElements.forEach(element => {
        if (!element.value || element.value.trim() === '') {
            allFieldsFilled = false;
        }
    });
    if(!allFieldsFilled){
        alert('CI SONO DEI CAMPI OBBLIGATORI CHE NON SONO STATI COMPILATI. DEVI COMPILARLI ' +
            'SE VUOI PROSEGUIRE OLTRE!');
    }
    return allFieldsFilled;
}

function checkFirstPage(){
    let valid = true;
    if (checkEmpty()){
        const civicNumber = document.getElementById('civic_number').value;
        const littleTown = document.getElementById('town').value;

        if(!onlyLettersRegex.test(littleTown)){
            alert("Puoi inserire solo lettere nel campo relativo al comune!!!");
            valid = false;
        }
        else if (!onlyNumbersRegex.test(civicNumber)){
            alert("Puoi inserire solo numeri nel campo relativo al numero civico!!!");
            valid = false;
        }
        else {
            return valid;
        }
    }
    else{
        valid = false;
    }
    return valid;
}

function checkCharacteristics(){
    let valid = true;
    if(checkEmpty()){
        const surface = document.getElementById('surface').value;
        if(!numbersFloatRegex.test(surface)){
            alert("La superficie deve essere composta da cifre seguite da eventuali '.' o ',' e da altre cifre !!!");
            valid = false;
        }
        else{
            return valid;
        }
    }
    else{
        valid = false;
    }
    return valid;
}

function checkFinalFields(){
    let valid = true;
    if(checkEmpty()){
        const price = document.getElementById('price').value;
        const description = document.getElementById('description').value; // Recupero la descrizione

        // Verifica che la descrizione abbia almeno 16 caratteri
        if (description.length < 16) {
            alert("La descrizione deve essere di almeno 16 caratteri!");
            valid = false;
        }
        if(!numbersFloatRegex.test(price)){
            alert("Il prezzo deve essere composto da delle cifre eventualmente seguite da '.' o ',' e da altre cifre!!!");
            valid = false;
        }
        else{
            return valid;
        }
    }
    else{
        valid = false;
    }
    return valid;
}

function checkContacts(){
    if (checkEmpty()) {
        let phoneNumber = document.getElementById('phoneNumber').value;
        let email = document.getElementById('email').value;
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!onlyNumbersRegex.test(phoneNumber) || phoneNumber.length!==10) {
            alert("Controlla che il numero di telefono sia corretto!");
            return false;
        }
        else if (!emailPattern.test(email)) {
            alert("Controlla di aver inserito bene l'email!!");
            return false;
        }
        return true;
    }
    else{
        return false;
    }

}

function upgradeFirstBar(){
    const typeBar = document.getElementById('typeBar');
    const requiredElements = document.getElementsByClassName('required');
    const percentLabel = document.getElementById('percentLabel');
    let totalElements = 0;
    let completedElements = 0;
    for(let i=0; i<requiredElements.length; i++){
        totalElements++;
    }
    for(let i = 0; i< requiredElements.length; i++){
        if(requiredElements[i].value !== ''){
            completedElements++;
        }
    }
    typeBar.value = (completedElements/totalElements) *100;

}

function upgradeCharBar(){
    const charBar = document.getElementById('charactheristics_bar');
    const requiredElements = document.getElementsByClassName('required');
    const percentLabel = document.getElementById('percentLabel');
    let totalElements = 0;
    let completedElements = 0;
    for(let i=0; i<requiredElements.length; i++){
        totalElements++;
    }
    for(let i = 0; i< requiredElements.length; i++){
        if(requiredElements[i].value !== ''){
            completedElements++;
        }
    }
    charBar.value = (completedElements/totalElements)*100;
}

function upgradePriceBar(images){
    const priceBar = document.getElementById('priceBar');
    const requiredElements = document.getElementsByClassName('required');
    const percentLabel = document.getElementById('percentLabel');
    let totalElements = 3;
    let completedElements = 0;
    for(let i = 0; i< requiredElements.length; i++){
        if(requiredElements[i].value !== ''){
            completedElements++;
        }
    }
    if(images){
        priceBar.value = 33.33 * completedElements + 33.33;
    }
    else{
        priceBar.value = (completedElements/totalElements)*100;
    }
}

function upgradeContactsBar(){
    const contactsBar = document.getElementById('contactsBar');
    const requiredElements = document.getElementsByClassName('required');
    const percentLabel = document.getElementById('percentLabel');
    let totalElements = 2;
    let completedElements = 0;
    for(let i = 0; i< requiredElements.length; i++){
        if(requiredElements[i].value !== ''){
            completedElements++;
        }
    }
    contactsBar.value = (completedElements/totalElements)*100;
    let newValue = contactsBar.value / 4;
    let finalValue  = newValue + 75;
    percentLabel.textContent = finalValue.toFixed(2) + '% completato';
}
