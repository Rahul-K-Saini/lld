package creational;

public class FactoryExample {

  static void main(String[] args) {
    Fan fan = SimpleFanFactory.createFan("ceiling");
    fan.turnOn();
    fan.changeSpeed(2.0f);
    fan.turnOff();

    FanFactory fanFactory = new CeilingFanFactory();
    FanFactory tableFanFactory = new TableFanFactory();

    Fan tableFan = tableFanFactory.createFan();
    Fan ceilingFan = fanFactory.createFan();

    tableFan.turnOn();
    tableFan.changeSpeed(2.0f);
    tableFan.turnOff();

    ceilingFan.turnOn();
    ceilingFan.changeSpeed(2.0f);
    ceilingFan.turnOff();

    UIFactoryB windowFactoryB = new WindowFactoryB();

    ButtonB windowButton = windowFactoryB.createButton();
    CheckboxB windowCheckbox = windowFactoryB.createCheckbox();

    windowButton.click();
    windowCheckbox.check();
  }

  // ---- Simple Factory ----------------------------------------

  // product
  interface Fan {
    void turnOn();

    void turnOff();

    void changeSpeed(float speed);
  }

  // different implementations of that product
  static class CeilingFan implements Fan {
    public void turnOn() {
      System.out.println("Ceiling fan turned on");
    }

    public void turnOff() {
      System.out.println("Ceiling fan turned off");
    }

    public void changeSpeed(float speed) {
      System.out.println("Ceiling fan speed changed to " + speed);
    }
  }

  // different implementations of that product
  static class TableFan implements Fan {
    public void turnOn() {
      System.out.println("Table fan turned on");
    }

    public void turnOff() {
      System.out.println("Table fan turned off");
    }

    public void changeSpeed(float speed) {
      System.out.println("Table fan speed changed to " + speed);
    }
  }

  // factory of that product which decide which product to give based on type
  // parameter using if/else or switch case
  static class SimpleFanFactory {
    public static Fan createFan(String type) {
      return switch (type) {
        case "ceiling" -> new CeilingFan();
        case "table" -> new TableFan();
        default -> throw new IllegalArgumentException("Unknown fan type");
      };
    }
  }

  // 2. ---- Factory Method ------------------------------------------------

  // this instead of having if else for that responsibility of creating the object
  // this delegates
  // this to the class of those individuals factory
  static class CeilingFanFactory implements FanFactory {
    @Override
    public Fan createFan() {
      return new CeilingFan();
    }
  }

  static class TableFanFactory implements FanFactory {
    @Override
    public Fan createFan() {
      return new TableFan();
    }
  }

  // main factory (open of extension and closed for modification)
  interface FanFactory {
    Fan createFan();
  }

  // 3. ---- Abstract Factory Method -----------------------------------------

  static abstract class UIFactoryB {
    protected abstract ButtonB createButton();

    protected abstract CheckboxB createCheckbox();
  }

  static class WindowFactoryB extends UIFactoryB {
    @Override
    protected ButtonB createButton() {
      return new WindowButtonB();
    }

    @Override
    protected CheckboxB createCheckbox() {
      return new WindowsCheckboxB();
    }
  }

  static class MacFactoryB extends UIFactoryB {
    @Override
    protected ButtonB createButton() {
      return new MacButtonB();
    }

    @Override
    protected CheckboxB createCheckbox() {
      return new MacCheckBoxB();
    }
  }

  // family of product
  interface ButtonB {
    void click();
  }

  interface CheckboxB {
    void check();
  }

  static class WindowButtonB implements ButtonB {
    @Override
    public void click() {
      System.out.println("Window button clicked");
    }
  }

  static class WindowsCheckboxB implements CheckboxB {
    @Override
    public void check() {
      System.out.println("Windows checkbox clicked");
    }
  }

  static class MacButtonB implements ButtonB {
    @Override
    public void click() {
      System.out.println("Mac button clicked");
    }
  }

  static class MacCheckBoxB implements CheckboxB {
    @Override
    public void check() {
      System.out.println("Mac checkbox checked");
    }
  }

}
