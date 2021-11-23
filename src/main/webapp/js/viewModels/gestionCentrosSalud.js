define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.nombre = ko.observable("");
			self.direccion = ko.observable("");
			self.numVacunas = ko.observable("");
			self.fInicio = ko.observable("08:00");
			self.fFin = ko.observable("14:00");
			self.franja = ko.observable("6");
			self.cupo = ko.observable("5");

			
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
				numVacunas : this.numVacunas(),
				fInicio : this.fInicio(),
				fFin : this.fFin(),
				franja : this.franja(),
				cupo : this.cupo()

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
		
		deleteCenters(center) {
			let self = this;
			
			let data = {
				data : JSON.stringify(center),
				url : "gestionCentroSalud/deleteCenter",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.getCentros();
					self.limpiarMensajes();
                    self.mostrarMensajes("Centro de Salud eliminado");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		modifyCenters(cs) {
            var self = this;

            let data = {
                data : JSON.stringify(cs),
                url : "gestionCentroSalud/modifyCenter",
                type : "post",
                contentType : 'application/json',
                success : function(response) {
                    self.getCentros();
                    self.limpiarMensajes();
                    self.mostrarMensajes("Centro actualizado correctamente.");
                    
                },
                error : function(response) {
                    self.error(response.responseJSON.errorMessage);
					self.getCentros();
                }
            };
           
            $.ajax(data);
        }
		
		limpiarMensajes() {
			let self = this;
			setTimeout(function() {
          		self.message(null);
          		self.error(null);
        	}, 3000);
		}
		
		mostrarMensajes(azul, rojo) {
			let self = this;
			setTimeout(function() {
          		self.message(azul);
          		self.error(rojo);
        	}, 0);
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