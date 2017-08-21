/**
 * Spring MVC Security Example javascript initialization.
 * Copyright 2017 Bernardo Martínez Garrido
 * Licensed under the MIT License (http://www.opensource.org/licenses/mit-license.php)
 * 
 * Loads all the Javascript functions used by the site.
 */

/**
 * Initializes scripts when the document loads.
 */
$(document).ready(function() {

   // Initializes data tables
	$(".dataTable").DataTable({
		"renderer" : "bootstrap",
		"order" : [],
		"columnDefs" : [ {
			"targets" : "no-sort",
			"orderable" : false,
		}, {
			"targets" : "hidden",
			"visible" : false,
		} ]
	});

});
