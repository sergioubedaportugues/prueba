define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.nombre = ko.observable("");
			self.direccion = ko.observable("");
			self.num_vacunas = ko.observable("");
			
			self.centros = ko.observableArray([]);
			
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
		
		insertCenters() {
			var self = this;
			let info = {
				nombre : this.nombre(),
				direccion : this.direccion(),
				num_vacunas : this.num_vacunas()
			};
			let data = {
				data : JSON.stringify(info),
				url : "gestionCentroSalud/insertCenter",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Centro guardado en la base de datos");
					self.getCentros();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
					

				}
			};
			$.ajax(data);
		}
		
		getCentros() {
			let self = this;
			let data = {
				url : "gestionCentroSalud/findAllCenters",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.centros(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		
		
		connected() {
			accUtils.announce('Gestión de centros de salud page loaded.');
			document.title = "Gestión de Centros de Salud";
			
			this.getCentros();
			
		};
		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return MenuViewModel;
});