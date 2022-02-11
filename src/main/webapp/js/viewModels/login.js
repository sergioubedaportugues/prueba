define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class LoginViewModel {
		constructor() {
			var self = this;
			
			self.email = ko.observable("practicadis@outlook.es");
			self.pwd = ko.observable("DISENOpractica24");
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
				email : this.email(),
				pwd : this.pwd()
			};
			var data = {
				data : JSON.stringify(info),
				url : "user/login",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					//$("#navList").hide();
					app.router.go( { path : "menu"} );
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		recoverPwd() {
			var self = this;
			var data = {
				url : "user/recoverPwd?email="+ self.email(),
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.message("Si está dado de alta le aparece un correo electronico");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		register() {
			app.router.go( { path : "register" } );
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
