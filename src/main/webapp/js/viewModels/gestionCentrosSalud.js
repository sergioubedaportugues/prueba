define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.nombre = ko.observable("");
			self.direccion = ko.observable("");
			self.numVacunas = ko.observable("");
			self.cupo = ko.observable("");
			self.fInicio = ko.observable("");
			self.fFin = ko.observable("");

			
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
				cupo : this.cupo(),
				fInicio : this.fInicio(),
				fFin : this.fFin()

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
		
		deleteCenters(center) {
			let self = this;
			
			let data = {
				data : JSON.stringify(center),
				url : "gestionCentroSalud/deleteCenter",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Centro de Salud eliminado");
					self.getCentros();
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
                    self.limpiarMensajes();
                    self.mostrarMensajes(null, "Error, el centro no se ha actualizado correctamente.");
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
        	}, 3000);
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