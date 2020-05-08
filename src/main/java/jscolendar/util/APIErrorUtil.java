package jscolendar.util;

import com.google.gson.annotations.SerializedName;
import io.swagger.client.*;
import io.swagger.client.model.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

public class APIErrorUtil {

  // TODO: add proper translations
  private static Map<ErrorResponse.CodeEnum, String> messages = Map.ofEntries(
    Map.entry(ErrorResponse.CodeEnum.INVALIDCREDENTIALS, "InvalidCredentials"),
    Map.entry(ErrorResponse.CodeEnum.INSUFFICIENTAUTHORIZATION, "InsufficientAuthorization"),
    Map.entry(ErrorResponse.CodeEnum.MALFORMEDDATA, "MalformedData"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDOLDPASSWORD, "InvalidOldPassword"),
    Map.entry(ErrorResponse.CodeEnum.PASSWORDTOOSIMPLE, "PasswordTooSimple"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDEMAIL, "InvalidEmail"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDPHONENUMBER, "InvalidPhoneNumber"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDRANK, "InvalidRank"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDID, "InvalidID"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDCAPACITY, "InvalidCapacity"),
    Map.entry(ErrorResponse.CodeEnum.TEACHERINCHARGE, "TeacherInCharge"),
    Map.entry(ErrorResponse.CodeEnum.CLASSROOMUSED, "ClassroomUsed"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDLEVEL, "InvalidLevel"),
    Map.entry(ErrorResponse.CodeEnum.CLASSUSED, "ClassUsed"),
    Map.entry(ErrorResponse.CodeEnum.STUDENTINCLASS, "StudentInClass"),
    Map.entry(ErrorResponse.CodeEnum.SUBJECTUSED, "SubjectUsed"),
    Map.entry(ErrorResponse.CodeEnum.TEACHERNOTINCHARGE, "TeacherNotInCharge"),
    Map.entry(ErrorResponse.CodeEnum.LASTTEACHERINSUBJECT, "LastTeacherInSubject"),
    Map.entry(ErrorResponse.CodeEnum.LASTGROUPINSUBJECT, "LastGroupInSubject"),
    Map.entry(ErrorResponse.CodeEnum.CLASSROOMALREADYOCCUPIED, "ClassroomAlreadyOccupied"),
    Map.entry(ErrorResponse.CodeEnum.CLASSORGROUPALREADYOCCUPIED, "ClassOrGroupAlreadyOccupied"),
    Map.entry(ErrorResponse.CodeEnum.INVALIDOCCUPANCYTYPE, "InvalidOccupancyType"),
    Map.entry(ErrorResponse.CodeEnum.ENDBEFORESTART, "EndBeforeStart"),
    Map.entry(ErrorResponse.CodeEnum.TEACHERDOESNOTTEACH, "TeacherDoesNotTeach"),
    Map.entry(ErrorResponse.CodeEnum.ILLEGALOCCUPANCYTYPE, "IllegalOccupancyType"),
    Map.entry(ErrorResponse.CodeEnum.TEACHERALREADYOCCUPIED, "TeacherAlreadyOccupied"),
    Map.entry(ErrorResponse.CodeEnum.UNKNOWN, "Unknown")
  );

  public static String getDefaultMessage() {
    return messages.get(ErrorResponse.CodeEnum.UNKNOWN);
  }

  public static String getErrorMessage(Throwable exception) {
    // TODO: change this if the client changes ony day
    ApiClient client = Configuration.getDefaultApiClient();

    if (exception instanceof ApiException)
      return APIErrorUtil.getErrorMessage(client, (ApiException) exception);
    else
      return APIErrorUtil.getDefaultMessage();
  }

  public static String getErrorMessage(ApiClient client, ApiException e) {
    // Local / Network error
    if (e.getResponseBody() == null) {
      return e.getLocalizedMessage();
    }

    JSON json = client.getJSON();

    // Try to parse the error code out of the response
    try {
      ErrorResponse parsed = json.deserialize(e.getResponseBody(), ErrorResponse.class);
      return messages.getOrDefault(parsed.getCode(), messages.get(ErrorResponse.CodeEnum.UNKNOWN));
    } catch (Exception exc) {
      return getDefaultMessage();
    }
  }

}
