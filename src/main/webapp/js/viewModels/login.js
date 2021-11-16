define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class LoginViewModel {
		constructor() {
			var self = this;
			
			self.login = ko.observable("");
			self.password = ko.observable("");
			self.message = ko.observable();
			self.error = ko.observable();
			
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

		login() {
			var self = this;
			var info = {
				login : this.login(),
				password : this.password()
			};
			var data = {
				data : JSON.stringify(info),
				url : "gestionUsuarios/iniciarSesion",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					//$("#navList").hide();
					self.mostrarMensajes("Hola :P");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		
		/*
		register() {
			app.router.go( { path : "register" } );
		}*/

		connected() {
			accUtils.announce('Login page loaded.');
			document.title = "Login";

		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
		
		mostrarMensajes(azul, rojo) {
			let self = this;
			setTimeout(function() {
          		self.message(azul);
          		self.error(rojo);
        	}, 3000);
		}	
	}

	return LoginViewModel;
});