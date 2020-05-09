package jscolendar.router;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import jscolendar.util.I18n;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AppRouter {
  private final Map<String, Router> routers = new HashMap<>();
  private Route currentRoute;

  private AppRouter () {}

  private static class AppRouterHolder {
    private final static AppRouter router = new AppRouter();
  }

  private static AppRouter getInstance () {
    return AppRouterHolder.router;
  }

  private static class Router {
    private final Map<String, Route> routes = new HashMap<>();
    private final ContentManageable container;

    private Router (ContentManageable container) {
      this.container = container;
    }
  }

  private static class RoutePath {
    private final String base;
    private final String param;

    private RoutePath (String routeName) {
      var tokens = routeName.split("/");
      this.base = tokens[0];
      this.param = tokens[1];
    }
  }

  private static class Route {
    private final String path;
    private Object data;

    private Route (String path) {
      this.path = path;
    }

    private Route (String path, Object data) {
      this(path);
      this.data = data;
    }
  }

  public static void bind (ContentManageable container) {
    AppRouter.bind("", container);
  }

  public static void bind (String mountPath, ContentManageable container) {
    getInstance().routers.put(mountPath, new Router(container));
  }

  public static void unbind (String mountPath) {
    if (!isMounted(mountPath)) return;

    var routers = getInstance().routers;
    routers.get(mountPath).routes.clear();
    routers.remove(mountPath);
  }

  public static void when (String routeLabel, String path) {
    var routePath = new RoutePath(routeLabel);
    var route = new Route(path);

    getInstance().routers.get(routePath.base).routes.put(routePath.param, route);
  }

  public static void goTo (String routeLabel) {
    var routePath = new RoutePath(routeLabel);
    var router = getInstance().routers.get(routePath.base);
    var route = router.routes.get(routePath.param);

    getInstance().loadNewRoute(route, router.container);
  }

  public static void goTo (String routeLabel, Object data) {
    var routePath = new RoutePath(routeLabel);
    var router = getInstance().routers.get(routePath.base);
    var route = router.routes.get(routePath.param);

    route.data = data;
    getInstance().loadNewRoute(route, router.container);
  }

  private void loadNewRoute (Route route, ContentManageable container) {
    currentRoute = route;
    String fxml = "/fxml/".concat(route.path).concat(".fxml");

    try {
      Parent root = FXMLLoader.load(new Object() {}.getClass().getResource(fxml), I18n.getBundle());
      container.setContent(root);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Object getData () {
    return getInstance().currentRoute.data;
  }

  public static boolean isMounted (String mountPath) {
    return AppRouter.getInstance().routers.get(mountPath) != null;
  }
}
