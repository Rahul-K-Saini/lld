public class ReportGenerationSystem {
  void main() {
    var csv = new CSVReport();
    csv.process();
  }
}

class PdfReport extends ReportGeneration {

  @Override
  protected void formatData() {
    System.out.println("formating data for PDF");
  }

}

class CSVReport extends ReportGeneration {

  @Override
  protected void formatData() {
    System.out.println("formating data for CSV");
  }

  @Override
  protected boolean sendsNotification() {
    return true;
  }

}

abstract class ReportGeneration {

  public final void process() {
    fetchData();
    formatData();
    generateOutputFile();
    if (sendsNotification()) {
      sendNotification();
    }
  }

  protected boolean sendsNotification() {
    return false;
  }

  protected void sendNotification() {
    System.out.println("Sending notification");
  }

  protected abstract void formatData();

  protected void generateOutputFile() {
    System.out.println("Generating Output file");
  }

  protected void fetchData() {
    System.out.println("fetching data");
  }

}
