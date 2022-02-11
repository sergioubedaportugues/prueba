define([ 'knockout', 'appController', 'ojs/ojmodule-element-utils', 'accUtils',
		'jquery' ], function(ko, app, moduleUtils, accUtils, $) {

	class MenuViewModel {
		constructor() {
			var self = this;
			
			self.idCategoria = ko.observable("");
			self.nombre = ko.observable("");
			self.numeroDeProductos = ko.observable("");
			
			self.categorias = ko.observableArray([]);

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
		
		addCategoria() {
			var self = this;
			let info = {
				nombre : this.nombre()
			};
			let data = {
				data : JSON.stringify(info),
				url : "categoria/addCategoria",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.message("Categoría guardada en la base de datos");
					self.getCategorias();
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
					self.categorias(response);
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
				}
			};
			$.ajax(data);
		}
		
		eliminarCategoria(idCategoria) {
			let self = this;
			let data = {
				url : "categoria/borrarCategoria/" + idCategoria,
				type : "delete",
				contentType : 'application/json',
				success : function(response) {
					self.message("Producto eliminado");
					self.getCategorias();
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
					self.mostrarMensajes(null, "Error, la categoria no se puede eliminar ya que hay productos con esa categoria.");
				}
			};
			$.ajax(data);
		}
		modificarCategoria(categoria) {
			var self = this;
			
			let data = {
				data : JSON.stringify(categoria),
				url : "categoria/modificarCategoria/",
				type : "post",
				contentType : 'application/json',
				success : function(response) {
					self.getCategorias();
					self.limpiarMensajes();
					self.mostrarMensajes("Categoria actualizada correctamente.");
				},
				error : function(response) {
					self.error(response.responseJSON.errorMessage);
					self.limpiarMensajes();
					self.mostrarMensajes(null, "Error, la categoria no se ha actualizada correctamente.");
				}
			};
			
			$.ajax(data);
		}
				
		register() {
			app.router.go( { path : "register" } );
		}
		
		connected() {
			accUtils.announce('Categoria page loaded.');
			document.title = "Categoria";
			this.getCategorias();
					
		};
		
	
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
        	}, 1);
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