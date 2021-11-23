define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	function appendLeadingZeroes(n) {
			if (n <= 9) {
				return "0" + n;
			}
			return n
		}

	class MenuViewModel {
		constructor() {
			var self = this;

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
		
		aplicarVacuna(cita) {
			var self = this;
			let data = {
				data : JSON.stringify(cita),
				url : "vistaSanitario/aplicarVacuna",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.getCitasPorCentro();
					self.limpiarMensajes();
                    self.mostrarMensajes("Vacuna aplicada correctamente.");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		getConsultar() {
			let self = this;
			var date = new Date(document.getElementById('start').value);
			let formatted_date = appendLeadingZeroes(date.getDate()) + "-" + appendLeadingZeroes(date.getMonth() + 1) + "-" + date.getFullYear()
			let data = {
				url : "vistaSanitario/getConsultar",
				type : "get",
				data : {
					fecha : formatted_date
				},
				contentType : 'application/json',
				success : function(response) {
					self.citas(response);
					self.limpiarMensajes();
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
		
		limpiarMensajes() {
			let self = this;
			setTimeout(function() {
          		self.message(null);
          		self.error(null);
        	}, 0);
		}
		
		mostrarMensajes(azul, rojo) {
			let self = this;
			setTimeout(function() {
          		self.message(azul);
          		self.error(rojo);
        	}, 0);
		}	
		
		connected() {
			accUtils.announce('Mis Citas page loaded.');
			document.title = "Mis Citas";
			
			this.getCitasPorCentro();
			
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