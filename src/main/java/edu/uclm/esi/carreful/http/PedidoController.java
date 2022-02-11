package edu.uclm.esi.carreful.http;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.uclm.esi.carreful.dao.PedidoDao;
import edu.uclm.esi.carreful.model.Carrito;
import edu.uclm.esi.carreful.model.Pedido;


@RestController
@RequestMapping("pedido")
public class PedidoController extends CookiesController {

	private static final String CAR = "carrito";
	
	@Autowired
	private PedidoDao pedidoDao;

	@GetMapping("/getTodos")
	public List<Pedido> get() {
		try {
			return pedidoDao.findAll();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping("/nuevoPedido")
	public void nuevoPedido(HttpServletRequest request) {
		try {
			Carrito carrito = (Carrito) request.getSession().getAttribute(CAR);
			if (carrito == null) {
				carrito = new Carrito();
				request.getSession().setAttribute(CAR, carrito);
			}
			Pedido pedido = new Pedido(carrito.productosCarrito());
			pedidoDao.save(pedido);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}