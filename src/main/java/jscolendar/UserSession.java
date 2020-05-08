package jscolendar;

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
