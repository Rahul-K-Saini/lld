package creational;

public class Factory {

  // 1. Simple Factory
  // -------------------------------------------------------------------------------

  // What it is: A single class with one method that takes a parameter and
  // internally decides (via if/switch) which concrete class to instantiate and
  // return.
  // Not in the GoF book — it's a common, pragmatic pattern people reach for
  // before they need something heavier.
  // Why it exists: You want to hide new SomeConcreteClass() calls from the client
  // so the client only depends on an interface/abstract type, not concrete
  // classes.

  // Example:
  interface ShapeA {
    void draw();
  }

  static class CircleA implements ShapeA {
    public void draw() {
      System.out.println("Circle");
    }
  }

  static class SquareA implements ShapeA {
    public void draw() {
      System.out.println("Square");
    }
  }

  static class ShapeFactory {
    public ShapeA createShape(String type) {
      if (type.equals("circle"))
        return new CircleA();
      if (type.equals("square"))
        return new SquareA();
      throw new IllegalArgumentException("Unknown type: " + type);
    }
  }

  // Client
  ShapeA s = new ShapeFactory().createShape("circle");

  // How it works: One factory class. One method. Branching logic inside that
  // method based on input. Client never writes new Circle() directly.
  // When to use: Small, stable number of product types where you just want the
  // creation logic centralized in one place — you're not expecting frequent new
  // types, or you're okay editing this one method when you add one.

  // Pros:
  // Dead simple, easy to understand, low ceremony.
  // Client fully decoupled from concrete classes.
  // Centralizes creation logic in one place — easy to find, easy to reason about.

  // Cons:
  // Violates OCP — adding a new product type means editing the if/switch inside
  // the factory method.
  // Doesn't scale well if the number of types grows large or changes often.
  // The factory method becomes a god-method as types grow — this is literally
  // your LightFactory's LightType branching right now.

  // 2. Factory Method
  // --------------------------------------------------------------------------

  // What it is: A GoF creational pattern. You define an abstract creator class
  // with an abstract (or virtual) method that subclasses override to decide what
  // concrete product to instantiate. The decision of "what to create" is
  // delegated to which subclass you're using, not to a parameter passed at
  // runtime.

  // Why it exists: You want to let subclasses control the "type" of object
  // created, while the base class defines the surrounding algorithm/workflow that
  // uses that object — without the base class knowing concrete product classes at
  // all.

  // Example:
  interface ShapeB {
    void draw();
  }

  static class CircleB implements ShapeB {
    public void draw() {
      System.out.println("Circle");
    }
  }

  static class SquareB implements ShapeB {
    public void draw() {
      System.out.println("Square");
    }
  }

  abstract static class ShapeCreator {
    // The "template" workflow — same for all subclasses
    public void render() {
      ShapeB shape = createShape(); // factory method — subclass decides
      shape.draw();
    }

    protected abstract ShapeB createShape(); // <-- the Factory Method
  }

  static class CircleCreator extends ShapeCreator {
    protected ShapeB createShape() {
      return new CircleB();
    }
  }

  static class SquareCreator extends ShapeCreator {
    protected ShapeB createShape() {
      return new SquareB();
    }
  }

  // Client
  ShapeCreator creator = new CircleCreator();
  // creator.render(); // "Circle"

  // How it works: The type of object created is baked into which subclass you
  // instantiate — CircleCreator always makes circles. No if/switch anywhere.
  // Adding a new shape means adding a new Creator subclass, not editing an
  // existing one.

  // When to use: When you have a fixed algorithm/workflow (like render() above)
  // that needs to work with different product types, and you want each variant of
  // that workflow bundled with its own product-creation logic, extensibly,
  // without touching existing code.

  // Pros:

  // True OCP compliance — new product = new subclass, zero edits to existing
  // classes.
  // Creation logic lives next to the subclass that "owns" that variant — good
  // cohesion.
  // Client (or the base class) depends only on abstractions (ShapeB,
  // ShapeCreator), never concretes.

  // Cons:

  // More classes, more indirection — can feel like overkill for simple cases.
  // Requires subclassing the creator, which can be awkward if you don't naturally
  // have a "creator" hierarchy already, or if your language/design leans toward
  // composition over inheritance.
  // Harder to add "runtime, data-driven" type selection (e.g., "create based on a
  // string from a config file") since the type is essentially fixed by which
  // subclass you compiled/instantiated — you often end up needing Simple Factory
  // or a registry anyway to pick which Creator subclass to use, ironically.

  // 3. Abstract Factory
  // ----------------------------------------------------------------------------

  // What it is: A GoF pattern one level above Factory Method. Instead of
  // producing one product, an Abstract Factory produces a family of related
  // products that are meant to be used together, and you can swap the entire
  // family by swapping which concrete factory you use.

  // Why it exists: You want to guarantee that a set of objects are consistent
  // with each other (e.g., all from the same theme/platform/vendor) and swapping
  // between families should be a single decision point, not scattered across the
  // codebase.

  // Example:

  // Family of related products
  interface Button {
    void render();
  }

  interface Checkbox {
    void render();
  }

  static class WinButton implements Button {
    public void render() {
      System.out.println("Windows button");
    }
  }

  static class WinCheckbox implements Checkbox {
    public void render() {
      System.out.println("Windows checkbox");
    }
  }

  static class MacButton implements Button {
    public void render() {
      System.out.println("Mac button");
    }
  }

  static class MacCheckbox implements Checkbox {
    public void render() {
      System.out.println("Mac checkbox");
    }
  }

  // Abstract Factory
  interface UIFactory {
    Button createButton();

    Checkbox createCheckbox();
  }

  static class WinFactory implements UIFactory {
    public Button createButton() {
      return new WinButton();
    }

    public Checkbox createCheckbox() {
      return new WinCheckbox();
    }
  }

  static class MacFactory implements UIFactory {
    public Button createButton() {
      return new MacButton();
    }

    public Checkbox createCheckbox() {
      return new MacCheckbox();
    }
  }

  // Client
  UIFactory factory = new MacFactory(); // one decision, whole family follows
  Button b = factory.createButton();
  Checkbox c = factory.createCheckbox();
  // b.render();
  // c.render();

  // How it works: One factory interface declares creation methods for multiple
  // related product types. Each concrete factory implementation produces one
  // consistent "family" (all-Windows or all-Mac). The client picks one factory
  // instance and everything it builds afterward stays consistent, automatically.

  // When to use: When your system needs to support multiple
  // themes/platforms/variants, and objects from different families should never
  // be mixed (e.g., you never want a WinButton paired with a MacCheckbox).

  // Pros:

  // Guarantees consistency across a family of related objects — impossible to
  // accidentally mix incompatible products.
  // Swapping an entire product family = swapping one factory object. Very clean
  // for theming/platform-switching.
  // Still OCP-friendly for adding new families (new factory implementation) —
  // though NOT for adding new product types to the family (see cons).

  // Cons:

  // Adding a new product type to the family (e.g., adding Slider alongside
  // Button/Checkbox) means editing the UIFactory interface and every concrete
  // factory implementing it — this is the classic Abstract Factory pain point,
  // and it does violate OCP in that direction.
  // More upfront complexity/class count than you often need — overkill if you
  // don't actually have multiple families.
  // Can be harder to test/mock because of the layered interfaces.

}
