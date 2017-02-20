package vertx.nubes.example;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.mixins.ContentType;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

/**
 * Created by manishk on 2/20/17.
 */
@Controller("/seg/diagnostic/")
@ContentType("application/json")
public class RestController {

    public RestController() {

    }

    @GET("nocontent")
    public void noContent() {
        System.out.println("No content!");
    }
}
