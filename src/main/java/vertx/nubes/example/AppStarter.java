package vertx.nubes.example;

import com.github.aesteve.vertx.nubes.NubesServer;
import com.github.aesteve.vertx.nubes.VertxNubes;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertxbeans.ContextRunner;
import io.vertxbeans.VertxBeans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@Import(VertxBeans.class)
public class AppStarter extends NubesServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppStarter.class);

    @Autowired
    private Vertx vertx;
    @Autowired
    private VertxOptions vertxOptions;
    @Autowired
    private ContextRunner contextRunner;

    public static void main(String[] args) {
        new SpringApplication(AppStarter.class).run(args);
    }

    @PostConstruct
    public void init() {
        JsonObject config = new JsonObject()
                .put("src-package", "vertx.nubes.example")
                .put("controller-packages", new JsonArray().add("vertx.nubes.example.controller"));
        VertxNubes vertxNubes = new VertxNubes(vertx, config);
        vertxNubes.registerService("vertx", vertx);
        vertxNubes.bootstrap(res -> {
            if (res.succeeded()) {
                createMainHttpServer(res.result());
            } else {
                LOGGER.error("Error while starting application.", res.cause());
            }
        });
    }

    private void createMainHttpServer(final Router router) {
        try {
            contextRunner.executeBlocking(vertxOptions.getEventLoopPoolSize(),
                    (Handler<AsyncResult<HttpServer>> handler) ->
                            vertx.createHttpServer()
                                    .requestHandler(request -> router.accept(request))
                                    .listen(8080, handler),
                    1, TimeUnit.MINUTES);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("Error while launching HTTP Server.", e);
        }
    }
}