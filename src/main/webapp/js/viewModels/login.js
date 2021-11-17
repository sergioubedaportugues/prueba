define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class LoginViewModel {
		constructor() {
			var self = this;
			
			self.login = ko.observable("");
<<<<<<< HEAD
			self.pwd = ko.observable("");
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
				1234 : this.login(),
				1234 : this.pwd()
			};
			var data = {
				data : JSON.stringify(info),
				url : "user/login",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					//$("#navList").hide();
					app.router.go( { path : "gestionCentrosSalud"} );
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
	}

	return LoginViewModel;
});

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

		iniciarSesion() {
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
					if(response.rol=="Administrador") {
						app.router.go( { path : "gestionCentrosSalud"} );
					} else if (response.rol=="Sanitario"){
						app.router.go( { path : "vistaSanitario"} );
					} else {
						app.router.go( { path : "vistaPaciente"} );
					}
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}

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
	}

	return LoginViewModel;
});
>>>>>>> refs/remotes/origin/Christopher
