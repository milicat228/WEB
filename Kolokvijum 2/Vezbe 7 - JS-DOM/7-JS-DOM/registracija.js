
function validacija(forma){
	
	var greska = 0;
	var imeEl = forma.elements["ime"];
	// Vrednost unešena u polje za ime
	var ime = imeEl.value;
	// False ako je ime null, undefined ili prazan string
	if (!ime) {
		// Postavi boju inputa na crvenu
		imeEl.style.background = 'red';
		greska = 1;
	}
	else{
		if(!(ime[0] === ime[0].toUpperCase())){
			greska = 1;
			imeEl.style.background = 'pink';
		}
	}

	var prezimeEl = forma.elements["prezime"];
	var prezime = prezimeEl.value;
	if(!prezime){
		prezimeEl.style.background = 'red';
		greska = 1;
	}
	else{
		if(!(prezime[0] === prezime[0].toUpperCase())){
			greska = 1;
			prezimeEl.style.background = 'pink';
		}
	}

	var jmbgEl = forma.elements["jmbg"];
	var jmbg = jmbgEl.value;
	if(!jmbg){
		jmbgEl.style.background = 'red';
		greska = 1;
	}
	else{
		if(jmbg.length != 13){
			jmbgEl.style.background = 'orange';
			greska = 1;
		}
	}

	var registracijaEl = forma.elements["registracija"];
	var registracija = registracijaEl.value;
	if(!registracija){
		registracijaEl.style.background = 'red';
		greska = 1;
	}

	var markaVozilaEl = forma.elements["marka"];
	var marka = markaVozilaEl.options[markaVozilaEl.selectedIndex].text;
	if(!marka){
		markaVozilaEl.style.background = 'red';
		greska = 1;
	}

	var tipVozilaEl = forma.elements["tip"];
	var tip = tipVozilaEl.options[tipVozilaEl.selectedIndex].text;
	if(!tip){
		tipVozilaEl.style.background = 'red';
		greska = 1;
	}
	

	if(greska === 1){
		return false;
	}


	var users = [];	
	if( localStorage.getItem("users") == null ){
		localStorage.setItem("users", JSON.stringify(users));
	}
	users = JSON.parse(localStorage.getItem("users"));
	users.push({'ime': ime, 'prezime': prezime, 'jmbg': jmbg});
	localStorage.setItem("users", JSON.stringify(users));

	return true;
}

function proveraTipa(tip){
    kubikazaRed = document.getElementById("kubikazaRed");	
    paragraf = document.getElementById("paragraf");
	if( tip.selectedIndex == 1 ){
		kubikazaRed.style.visibility = "visible" ;
		paragraf.style.visibility = "visible";
	}
	else{
		kubikazaRed.style.visibility = "collapse" ;
		paragraf.style.visibility = "collapse";
	}
}

function proveraKubikaze(kubikaza){
	if( kubikaza.value ){
		var kub = parseInt(kubikaza.value, 10);
		paragraf = document.getElementById("paragraf");
		if( kub < 600 ){
			paragraf.style.color = 'yellow';
		}

		if( kub >= 600 && kub <= 1200){
			paragraf.style.color = 'orange';
		}

		if( kub > 1200){
			paragraf.style.color = 'red';
		}
	}
	

}