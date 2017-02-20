package vertx.nubes.example.controller;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.mixins.ContentType;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.services.Service;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@Controller("/json/")
@ContentType("application/json")
public class RestController {

    @Service("vertx")
    private Vertx vertx;

    @GET("jsonobject")
    public JsonObject sendJsonObject() {
        return new JsonObject().put("Bill", "Cocker");
    }

    @GET("async")
    public void executeAsync(final RoutingContext context) {
        context.next();
    }
}
