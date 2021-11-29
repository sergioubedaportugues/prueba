define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.id = ko.observable("");
			self.horas = ko.observable("");
			self.dia = ko.observable("");
			self.numCita = ko.observable("");

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
		
		insertCita() {
			var self = this;
			let info = {
				horas : this.horas(),
				dia : this.dia(),
				numCita : this.numCita()
			};
			let data = {
				data : JSON.stringify(info),
				url : "vistaPaciente/insertCita",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.getCitas();
					self.limpiarMensajes();
                    self.mostrarMensajes("Sus citas se han generado correctamente.");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		getCitas() {
			let self = this;
			let data = {
				url : "vistaPaciente/mostrarCitasPedidas",
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
		
		deleteCita(cita){
			let self = this;
			
			let data = {
				data : JSON.stringify(cita),
				url : "vistaPaciente/deleteCita",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.getCitas();
					self.limpiarMensajes();
                    self.mostrarMensajes("Cita cancelada.");
					
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
					self.getCitas();
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
		
		modifyCita(cita) {
            var self = this;

            let data = {
                data : JSON.stringify(cita),
                url : "vistaPaciente/modifyCita",
                type : "post",
                contentType : 'application/json',
                success : function(response) {
                    self.getCitas();
                    self.limpiarMensajes();
                    self.mostrarMensajes("Cita actualizada correctamente.");
                },
                error : function(response) {
                    self.error(response.responseJSON.errorMessage);
                    self.getCitas();

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
			accUtils.announce('Calendario page loaded.');
			document.title = "Calendario";
			
			this.getCitas();
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