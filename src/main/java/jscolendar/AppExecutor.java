package jscolendar;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutor {
  private final ExecutorService executor = Executors.newFixedThreadPool(4);

  private AppExecutor () {}

  private static class AppExecutorHolder {
    private final static AppExecutor instance = new AppExecutor();
  }

  public static Executor getInstance () {
    return AppExecutorHolder.instance.executor;
  }
}
