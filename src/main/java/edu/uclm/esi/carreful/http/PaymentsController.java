package edu.uclm.esi.carreful.http;



import java.util.Map;


import javax.servlet.http.HttpServletRequest;



import org.json.JSONObject;

import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stripe.Stripe;

import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;




import edu.uclm.esi.carreful.model.Carrito;





@RestController
@RequestMapping("payments")
public class PaymentsController extends CookiesController {
	static {
		Stripe.apiKey = "sk_test_51Idbw2GXaessTZw7Okncx8iFGqc7BQkNoO0R8ZiaL41jGWEoC748lhYOz29Cwq1GjXZLbeps9c6QWaz6pkcUJEfm00KEvXXB0l";
	}

	@PostMapping("/solicitarPreautorizacion")
	public String solicitarPreautorizacion(HttpServletRequest request, @RequestBody Map<String, Object> info) {
		try {
			Carrito carrito = (Carrito) request.getSession().getAttribute("carrito");
			long precioEnLong = (long) carrito.getTotalPrice();
			precioEnLong = precioEnLong*100;
			PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
					.setCurrency("eur")
					.setAmount(precioEnLong)
					.build();
			// Create a PaymentIntent with the order amount and currency
			PaymentIntent intent = PaymentIntent.create(createParams);
			JSONObject jso = new JSONObject(intent.toJson());
			return jso.getString("client_secret");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}

	}
}