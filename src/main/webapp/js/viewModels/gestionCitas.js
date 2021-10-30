define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.id = ko.observable("");
			self.horas = ko.observable("");
			self.dia = ko.observable("");
			self.nombreCentro = ko.observable("");
			
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
				nombreCentro : this.nombreCentro(),

			};
			let data = {
				data : JSON.stringify(info),
				url : "gestionCitas/insertCita",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Cita guardada en la base de datos");
					self.getCitas();
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
				url : "gestionCitas/findAllCitas",
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
		
		connected() {
			accUtils.announce('Gestión de Citas page loaded.');
			document.title = "Gestión de Citas";
			
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