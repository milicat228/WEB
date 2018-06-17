// Closure koji će zapamtiti trenutni proizvod
function clickClosure(product){
	return function() {
		// Parametar product prosleđen u gornju funkciju će biti vidljiv u ovoj
		// Ovo znači da je funkcija "zapamtila" za koji je proizvod vezana
		$('tr.selected').removeClass('selected');
		$(this).addClass('selected');
		
		//kada je proizvod u tabeli selektovan popuni uredi formu
		$("#name").val(product.name);
		$("#price").val(product.price);
		$("#id").val(product.id);
		
	};
}

function clickObrisi(product){
	return function() {
	let tr = $(this).parent();
	let url = 'rest/products/';
	url += product.id;
	$.ajax({
	    type: 'DELETE',
	    url: url,
	    contentType: 'application/json',	    
	    success: function(product) {	   
	    	tr.remove();
	    	$('#successBrisanje').show().delay(3000).fadeOut();
		}
	})
	
	}	
}

function addProductTr(product) {
	let tr = $('<tr id = "' + product.id + '" ></tr>');
	let tdNaziv = $('<td>' + product.name + '</td>');
	let tdCena = $('<td>' + product.price + '</td>');
	let tdObrisi = $('<td>' + '<input type="submit" value="Obriši">' + '</td>');
	tdObrisi.click(clickObrisi(product));	
	tr.append(tdNaziv).append(tdCena).append(tdObrisi);
	tr.click(clickClosure(product));
	$('#tabela tbody').append(tr);
}

$(document).ready(function() {
	$.get({
		url: 'rest/products',
		success: function(products) {
			for (let product of products) {
				addProductTr(product);
			}
		}
	});
	
	$('form#urediForma').submit(function(event) {
		event.preventDefault();
		let name = $('input[name="nameUredi"]').val();
		let price = $('input[name="priceUredi"]').val();
		let id = $('input[name="id"]').val();	
		
		let url = 'rest/products/';
		url += id;
		
		$.ajax({
		    type: 'PUT',
		    url: url,
		    contentType: 'application/json',
		    data: JSON.stringify({id: id, name: name, price: price}),
		    success: function(product) {
				$('#successUredi').text('Proizvod uspešno uređen.');
				$('#successUredi').show().delay(3000).fadeOut();
				 if( $('tr#' + product.id).length )         // use this if you are using id to check
				 {
					 let tdNaziv = $('<td>' + product.name + '</td>');
					 let tdCena = $('<td>' + product.price + '</td>');
					 $('tr#' + product.id).empty().append(tdNaziv).append(tdCena);					
					 $('tr#' + product.id).click(clickClosure(product));
				 }
				 else{
					 addProductTr(product);
				 }
			}
		})
		
	});
	
	
	$('button#dodaj').click(function() {
		$('form#forma').toggle();
	});
	
	$('form#forma').submit(function(event) {
		event.preventDefault();
		let name = $('input[name="name"]').val();
		let price = $('input[name="price"]').val();
		if (!price || isNaN(price)) {
			$('#error').text('Cena mora biti broj!');
			$("#error").show().delay(3000).fadeOut();
			return;
		}
		$('p#error').hide();
		$.post({
			url: 'rest/products',
			data: JSON.stringify({id: '', name: name, price: price}),
			contentType: 'application/json',
			success: function(product) {
				$('#success').text('Novi proizvod uspešno kreiran.');
				$("#success").show().delay(3000).fadeOut();
				// Dodaj novi proizvod u tabelu
				addProductTr(product);
			}
		});
	});
});