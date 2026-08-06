public class Template {

  void main() {
    DataProcessor jProcessor = new JSONDataProcessor();
    jProcessor.process();
  }

}

abstract class DataProcessor {

  public final void process() {
    readData();
    parseData();
    validateData();
    saveToDatabase();
  }

  protected abstract void readData();

  protected abstract void parseData();

  protected void validateData() {
    System.out.println("Running generic validation");
  }

  protected void saveToDatabase() {
    System.out.println("Saving to database");
  }
}

class CSVDataProcessor extends DataProcessor {
  protected void readData() {
    System.out.println("Reading CSV file");
  }

  protected void parseData() {
    System.out.println("Parsing CSV rows");
  }
}

class JSONDataProcessor extends DataProcessor {
  protected void readData() {
    System.out.println("Reading JSON file");
  }

  protected void parseData() {
    System.out.println("Parsing JSON");
  }
}
