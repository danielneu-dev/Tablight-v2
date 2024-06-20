package main.api;

public abstract class BaseLoggerDecorator implements LoggerInterface {
  private final LoggerInterface wrapped;
  protected final String filePath;

  public BaseLoggerDecorator(LoggerInterface wrapped) {
    this.wrapped = wrapped;
    this.filePath = "src/log.txt";
  }

  @Override
  public void writeFile(String message) {
    wrapped.writeFile(message);
  }
}