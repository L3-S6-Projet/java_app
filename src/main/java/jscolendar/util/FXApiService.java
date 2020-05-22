package jscolendar.util;

import io.swagger.client.ApiException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import jscolendar.AppExecutor;

public class FXApiService<Request, Response> extends Service<Response> {

  public interface APIHandler<Request, Response> {
    Response handle(Request request) throws ApiException;
  }

  private final ObjectProperty<Request> request = new SimpleObjectProperty<>();
  private final APIHandler<Request, Response> method;

  public FXApiService(APIHandler<Request, Response> method) {
    this.method = method;
    this.setExecutor(AppExecutor.getInstance());
  }

  public void setRequest(Request request) {
    this.request.set(request);
  }

  @Override
  protected Task<Response> createTask()  {
    return new Task<>() {
      @Override
      protected Response call() throws Exception {
        return method.handle(request.get());
      }
    };
  }
}
