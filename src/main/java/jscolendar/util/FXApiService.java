package jscolendar.util;

import io.swagger.client.ApiException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FXApiService<Request, Response> extends Service<Response> {

  public interface APIHandler<Request, Response> {
    Response handle(Request request) throws ApiException;
  }

  private final Request request;
  private final APIHandler<Request, Response> method;

  private final StringProperty errorMessage = new SimpleStringProperty();

  public FXApiService(Request request, APIHandler<Request, Response> method) {
    this.request = request;
    this.method = method;
  }

  public String getErrorMessage() {
    return errorMessage.getValue();
  }

  @Override
  protected Task<Response> createTask()  {
    return new Task<>() {
      @Override
      protected Response call() throws Exception {
        return method.handle(request);
      }
    };
  }

}
