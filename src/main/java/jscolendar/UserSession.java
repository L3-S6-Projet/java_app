package jscolendar;

import io.swagger.client.ApiClient;
import io.swagger.client.Configuration;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.SuccessfulLoginResponse;
import io.swagger.client.model.SuccessfulLoginResponseUser;

public class UserSession {

  SuccessfulLoginResponse response;

  private static UserSession instance;

  public static UserSession getInstance() {
    if (instance == null)
      instance = new UserSession();

    return instance;
  }

  public void init(SuccessfulLoginResponse response) {
    this.response = response;
    configureApiClient();
  }

  private void configureApiClient() {
    // TODO: change this if the client changes ony day
    ApiClient client = Configuration.getDefaultApiClient();

    // TODO: undo this when logging out
    ApiKeyAuth auth = (ApiKeyAuth) client.getAuthentication("token");
    auth.setApiKey(response.getToken());
    auth.setApiKeyPrefix("Bearer");
  }

  public boolean isValid() {
    return this.response != null;
  }

  public String getToken() {
    assert this.isValid();
    return this.response.getToken();
  }

  public SuccessfulLoginResponseUser getUser() {
    assert this.isValid();
    return this.response.getUser();
  }

}
