define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			var nativePicker = document.querySelector('.nativeDatePicker');
			var fallbackPicker = document.querySelector('.fallbackDatePicker');
			var fallbackLabel = document.querySelector('.fallbackLabel');
			var dateControl = document.querySelector('input[type="date"]');
			var yearSelect = document.querySelector('#year');
			var monthSelect = document.querySelector('#month');
			var daySelect = document.querySelector('#day');
			
			var test = document.createElement('input');
			
			
			
			self.centros = ko.observableArray([]);
			self.paciente = ko.observableArray([]);
			self.citas = ko.observableArray([]);
			
			self.message = ko.observable(null);
			self.error = ko.observable(null);
			
 			// Header Config
			self.headerConfig = ko.observable({
				'view' : [],
				'viewModel' : null
			});
			moduleUtils.createView({
				'viewPath' : 'views/header.html'
			}).then(function(view) {
				self.headerConfig({
					'view' : view,
					'viewModel' : app.getHeaderModel()
				})
			})
		}
		
		getCitasPorCentro() {
			let self = this;
			let data = {
				url : "vistaSanitario/getCitasPorCentro",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.citas(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		getConsultar() {
			let self = this;
			
			yearSelect = document.querySelector('#year');
			monthSelect = document.querySelector('#month');
			daySelect = daySelect.value;
			let data = {
				url : "vistaSanitario/getCitasPorCentro",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.citas(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		
		cerrarSesion(){
			let self = this;
			
			let data = {
				data : JSON.stringify(),
				url : "login/cerrarSesion",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Sesión cerrada correctamente.");
					app.router.go( { path : "login"} );
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		connected() {
			accUtils.announce('Mis Citas page loaded.');
			document.title = "Mis Citas";
			
			this.getCitasPorCentro();
			
		};
		disconnected() {
			// Implement if needed
		};
		
		populateDays(month) {
		  // delete the current set of <option> elements out of the
		  // day <select>, ready for the next set to be injected
		  while(daySelect.firstChild){
		    daySelect.removeChild(daySelect.firstChild);
		  }
		
		  // Create variable to hold new number of days to inject
		  var dayNum;
		
		  // 31 or 30 days?
		  if(month === 'January' || month === 'March' || month === 'May' || month === 'July' || month === 'August' || month === 'October' || month === 'December') {
		    dayNum = 31;
		  } else if(month === 'April' || month === 'June' || month === 'September' || month === 'November') {
		    dayNum = 30;
		  } else {
		  // If month is February, calculate whether it is a leap year or not
		    var year = yearSelect.value;
		    (year - 2016) % 4 === 0 ? dayNum = 29 : dayNum = 28;
		  }
		
		  // inject the right number of new <option> elements into the day <select>
		  for(i = 1; i <= dayNum; i++) {
		    var option = document.createElement('option');
		    option.textContent = i;
		    daySelect.appendChild(option);
		  }
		
		  // if previous day has already been set, set daySelect's value
		  // to that day, to avoid the day jumping back to 1 when you
		  // change the year
		  if(previousDay) {
		    daySelect.value = previousDay;
		
		    // If the previous day was set to a high number, say 31, and then
		    // you chose a month with less total days in it (e.g. February),
		    // this part of the code ensures that the highest day available
		    // is selected, rather than showing a blank daySelect
		    if(daySelect.value === "") {
		      daySelect.value = previousDay - 1;
		    }
		
		    if(daySelect.value === "") {
		      daySelect.value = previousDay - 2;
		    }
		
		    if(daySelect.value === "") {
		      daySelect.value = previousDay - 3;
		    }
		  }
		};
		
		populateYears() {
		  // get this year as a number
		  var date = new Date();
		  var year = date.getFullYear();
		
		  // Make this year, and the 100 years before it available in the year <select>
		  for(var i = 0; i <= 100; i++) {
		    var option = document.createElement('option');
		    option.textContent = year-i;
		    yearSelect.appendChild(option);
		  }
		};
		
		yearSelect.onchange = function() {
		  populateDays(monthSelect.value);
		};
		

		transitionCompleted() {
			// Implement if needed
		};
		
	}

	return MenuViewModel;
});