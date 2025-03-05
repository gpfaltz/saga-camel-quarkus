package com.gpfaltz;

import org.apache.camel.CamelContext;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("saga-camel-order")
public class CamelOrderResource {

	@Inject
	CamelContext context;

	@Path("test")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response saga() {

		try {
			Long id = 0L;

			buy(++id, 20);
			buy(++id, 30);
			buy(++id, 30);
			buy(++id, 25);

			return Response.ok().entity("All good").build();

		} catch (Exception e) {
			return Response.status(500).entity("Something went wrong").build();
		}
	}

	private void buy(Long id, int value) {
		System.out.println("Order: " + id + " value: " + value);

		try {
			context.createFluentProducerTemplate()
				.to("direct:saga")
				.withHeader("id", id)
				.withHeader("value", value)
				.request();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
}
