package main.api;

public class InfoDecorator extends BaseLoggerDecorator {
  public InfoDecorator(LoggerInterface wrapped) {
    super(wrapped);
  }

  @Override
  public void writeFile(String message) {
    super.writeFile("🟩 [INFO] " + message);
  }
}