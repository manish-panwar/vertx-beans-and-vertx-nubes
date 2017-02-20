package vertx.nubes.example;

import com.github.aesteve.vertx.nubes.NubesServer;
import com.github.aesteve.vertx.nubes.VertxNubes;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.Vertx;
import io.vertxbeans.rxjava.VertxBeans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;

/**
 * Created by Manish Kumar on 2/20/17.
 */
@SpringBootApplication
@Import(VertxBeans.class)
public class AppStarter extends NubesServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);

    @Autowired
    private Vertx vertx;

    public static void main(String[] args) {
        new SpringApplication(AppStarter.class).run(args);
    }

    @PostConstruct
    public void init() {
        JsonObject config = new JsonObject()
                .put("host", "localhost")
                .put("port", parseInt(getProperty("server.port", "8080")))
                .put("src-package", "com.http.load.tool");
        VertxNubes vertxNubes = new VertxNubes((io.vertx.core.Vertx) vertx.getDelegate(), config);
        vertxNubes.bootstrap(res -> {
            if (res.succeeded()) {
                LOGGER.info("Application started successfully!!!");
            } else {
                LOGGER.error("Error while starting application.", res.cause());
            }
        });
    }
}
