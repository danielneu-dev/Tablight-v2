package main.api;

public class PrintDecorator extends BaseLoggerDecorator {
  public PrintDecorator(LoggerInterface wrapped) {
    super(wrapped);
  }

  @Override
  public void writeFile(String message) {
    super.writeFile("Print: " + message);
    System.out.println(message);
  }
}