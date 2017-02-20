package vertx.nubes.example;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.mixins.ContentType;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

@Controller("/diagnostic/")
@ContentType("application/json")
public class RestController {

    public RestController() {

    }

    @GET("nocontent")
    public void noContent() {
        System.out.println("No content!");
    }
}
