define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.idProduct = ko.observable("");
			self.nombre = ko.observable("");
			self.precio = ko.observable();
			self.codigo = ko.observable("");
			self.stock = ko.observable("");
			self.categoria = ko.observableArray([]);
			self.picture=ko.observable();
			
			self.productos = ko.observableArray([]);
			
			self.message = ko.observable(null);
			self.error = ko.observable(null);
			
			self.setPicture = function(widget, event) {
			var file = event.target.files[0];
			var reader = new FileReader();
			reader.onload = function () {
				self.picture ("data:image/png;base64," + btoa(reader.result));
			}
			reader.readAsBinaryString(file);
		}
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
		
		setNuevaPicture(event, product) {
            var reader = new FileReader();
            reader.onload = function () {
                product.picture="data:image/png;base64," + btoa(reader.result);
            }
            reader.readAsBinaryString(event.currentTarget.files[0]);
        }
		
		add() {
			var self = this;
			var cat = document.getElementById("cat")
			let info = {
				nombre : this.nombre(),
				precio : this.precio(),
				codigo : this.codigo(),
				stock : this.stock(),
				categoria : this.categoria()[cat.selectedIndex],
				picture : this.picture()
			};
			let data = {
				data : JSON.stringify(info),
				url : "menu/add",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					//var successmessage = 
					self.message("Producto guardado en la base de datos");
					//var t = setTimeout(successmessage,3000);
					//clearTimeout(t);
					self.getProductos();
				},
				error : function(response) {
					//var errormessage = 
					self.error(response.responseJSON.errorMessage);
					//var z = setTimeout(errormessage,3000);
					//clearTimeout(z)
				}
			};
			$.ajax(data);
		}
		
		getProductos() {
			let self = this;
			let data = {
				url : "menu/getTodos",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.productos(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		getCategorias() {
			let self = this;
			let data = {
				url : "categoria/getCategorias",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					self.categoria(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		eliminarProducto(product) {
			let self = this;
			
			let data = {
				data : JSON.stringify(product),
				url : "menu/borrarProducto",
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Producto eliminado");
					self.getProductos();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		modificarProducto(product) {
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
        }
		
		register() {
			app.router.go( { path : "register" } );
		}
		
		connected() {
			accUtils.announce('Menu page loaded.');
			document.title = "Menu";
			this.comprobarCredenciales();
			
			this.getProductos();
			this.getCategorias();
			
		};
		
		comprobarCredenciales() {
			let self = this;
			let data = {
				url : "user/comprobarCredenciales",
				type : "get",
				contentType : 'application/json',
				success : function(response) {
					app.router.go( { path : "menu" } );
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
        	}, 3000);
		}
		
		mostrarMensajes(azul, rojo) {
			let self = this;
			setTimeout(function() {
          		self.message(azul);
          		self.error(rojo);
        	}, 3000);
		}	

		

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return MenuViewModel;
});