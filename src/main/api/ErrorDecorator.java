package main.api;

public class ErrorDecorator extends BaseLoggerDecorator {
  public ErrorDecorator(LoggerInterface wrapped) {
    super(wrapped);
  }

  @Override
  public void writeFile(String message) {
    super.writeFile("🟥 [ERROR] " + message);
  }
}