package jscolendar.router;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
    private final ContentManageable layout;

    private Router (ContentManageable layout) {
      this.layout = layout;
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

  public static void bind (String mountPath, ContentManageable layout) {
    getInstance().routers.put(mountPath, new Router(layout));
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

    getInstance().loadNewRoute(route, router.layout);
  }

  public static void goTo (String routeLabel, Object data) {
    var routePath = new RoutePath(routeLabel);
    var router = getInstance().routers.get(routePath.base);
    var route = router.routes.get(routePath.param);

    route.data = data;
    getInstance().loadNewRoute(route, router.layout);
  }

  private void loadNewRoute (Route route, ContentManageable layout) {
    currentRoute = route;
    String fxml = "/".concat(route.path);

    try {
      Parent root = FXMLLoader.load(new Object() {}.getClass().getResource(fxml));
      layout.setContent(root);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("");
    }
  }

  public static Object getData () {
    return getInstance().currentRoute.data;
  }

  public static boolean isMounted (String mountPath) {
    return AppRouter.getInstance().routers.get(mountPath) != null;
  }
}
