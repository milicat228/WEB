window.onload = function(event) {

	 var users = [];
	 if( localStorage.getItem("users") == null){
	 	users.push( {'ime': 'Petar', 'prezime': 'Petrović', 'jmbg': '123321'});
	 	localStorage.setItem("users", JSON.stringify(users));
	 }
	 else{
	 	users = JSON.parse(localStorage.getItem("users"));
	 }	

	 let tabela = document.getElementsByTagName('table')[0];
	 for (user of users) {
		// Kreiraj novi red (tr)
		let userTr = document.createElement('tr');
		// Kreiraj polja reda (td)
		let imeTd = document.createElement('td');
		let prezimeTd = document.createElement('td');
		let jmbgTd = document.createElement('td');
		// Dodaj tekst u svako polje
		imeTd.appendChild(document.createTextNode(user.ime));
		prezimeTd.appendChild(document.createTextNode(user.prezime));
		jmbgTd.appendChild(document.createTextNode(user.jmbg));
		// Dodaj polja u red
		userTr.appendChild(imeTd);
		userTr.appendChild(prezimeTd);
		userTr.appendChild(jmbgTd);
		// Dodaj red u tabelu
		tabela.appendChild(userTr);
	 }
};