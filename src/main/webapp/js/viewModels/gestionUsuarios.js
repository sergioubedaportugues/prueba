define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.id = ko.observable("");
			self.login = ko.observable("");
			self.password = ko.observable();
			self.nombre = ko.observable("");
			self.apellidos = ko.observable("");
			self.telefono = ko.observable("");
			self.dni= ko.observable("");
			self.rol = ko.observable("");

			self.usuarios = ko.observableArray([]);
			
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
		
		insertUsers() {
			var self = this;
			let info = {
				login : this.login(),
				password : this.password(),
				nombre : this.nombre(),
				apellidos : this.apellidos(),
				telefono : this.telefono(),
				dni : this.dni(),
				rol : this.rol()
			};
			let data = {
				data : JSON.stringify(info),
				url : "gestionUsuarios/insertUsers",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Usuario guardado en la base de datos");
					self.getUsuarios();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);

				}
			};
			$.ajax(data);
		}
		
		getUsuarios() {
			let self = this;
			let data = {
				url : "gestionUsuarios/findAllUsers",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.usuarios(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		/*deleteUsuarios(usuario) {
			let self = this;
			
			let data = {
				data : JSON.stringify(usuario),
				url : "gestionUsuarios/delete",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Usuario eliminado");
					self.getUsuarios();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		modificarProducto(usuario) {
            var self = this;

            let data = {
                data : JSON.stringify(product),
                url : "menu/modificarProducto/",
                type : "post",
                contentType : 'application/json',
                success : function(response) {
                    self.getProductos();
                    self.limpiarMensajes();
                    self.mostrarMensajes("Producto actualizado correctamente.");
                },
                error : function(response) {
                    self.error(response.responseJSON.errorMessage);
                    self.limpiarMensajes();
                    self.mostrarMensajes(null, "Error, el producto no se ha actualizado correctamente.");
                }
            };
           
            $.ajax(data);
        }*/
		
		connected() {
			accUtils.announce('Gestión de usuarios page loaded.');
			document.title = "Gestión Usuarios";
			
			this.getUsuarios();
			
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