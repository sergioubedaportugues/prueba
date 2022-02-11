define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class ProductViewModel {
		constructor() {
			var self = this;
			
			self.nombre = ko.observable("");
			self.precio = ko.observable("");
			self.amount = ko.observable("");
			self.categoria = ko.observableArray([]);
			/*self.nombre = ko.observable("");*/
			/*self.nombre = ko.observable("");*/

			self.productos = ko.observableArray([]);
			self.carrito = ko.observableArray([]);
			self.totalPrice = ko.observable(null);
			
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

		add() { 
			var self = this;
			let info = {
				nombre : this.nombre(),
				precio : this.precio()
			};
			let data = {
				data : JSON.stringify(info),
				url : "product/add",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Producto guardado");
					self.getProductos();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		getProductos() {
			let self = this;
			let data = {
				url : "product/getTodos",
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
		
		getCategoriaProducto(idCategoria) {
			let self = this;
			let data = {
				url : "product/getCategoriaProducto/"+idCategoria,
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
		
		eliminarProducto(idProduct) {
			let self = this;
			let data = {
				url : "product/borrarProducto/" + idProduct,
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
		
		addAlCarrito(idProduct) {
			let self = this;
			let data = {
				url : "product/addAlCarrito/" + idProduct,
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Producto añadido al carrito");
					self.carrito(response.products);
					self.totalPrice(response.totalPrice);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
					self.limpiarMensajes();
                    self.mostrarMensajes(null, "Error, el producto no tiene stock.");
				}
			};
			$.ajax(data);
		}
		
		borrarCarrito(idProduct) {
			let self = this;
			let data = {
				url : "product/borrarCarrito/" + idProduct,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Producto eliminado del carrito.");
					self.carrito(response.products);
					self.totalPrice(response.totalPrice);

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
			accUtils.announce('Login page loaded.');
			document.title = "Login";
			
			this.carrito.products;
			this.getProductos();
			this.getCategorias();
		};

		disconnected() {
			// Implement if needed
		};

		transitionCompleted() {
			// Implement if needed
		};
	}

	return ProductViewModel;
});
