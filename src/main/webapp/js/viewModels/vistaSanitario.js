define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

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

		transitionCompleted() {
			// Implement if needed
		};
	}

	return MenuViewModel;
});