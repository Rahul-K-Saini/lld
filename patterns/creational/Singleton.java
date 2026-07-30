package creational;

public class Singleton {

  public static void main(String[] args) {
    var obj = SingletonWithStaticExample.getInstance();
    var obj2 = SingletonWithDLC.getInstance();

    System.out.println(obj.value);
    System.out.println(obj2.value);

    EnumSingleton.INSTANCE.doSomething();
  }

}

// holder idiom singleton implementation
class SingletonWithStaticExample {
  public final String value = "Hello World";

  private SingletonWithStaticExample() {
  }

  private static class Holder {
    private static final SingletonWithStaticExample INSTANCE = new SingletonWithStaticExample();
  }

  public static SingletonWithStaticExample getInstance() {
    return Holder.INSTANCE;
  }

}

// DLC (Double-Checked Locking) Singleton implementation
class SingletonWithDLC {

  public final String value = "Hello World";

  private SingletonWithDLC() {
  }

  private volatile static SingletonWithDLC instance;

  public static SingletonWithDLC getInstance() {
    if (instance == null) {
      synchronized (SingletonWithDLC.class) {
        if (instance == null) {
          instance = new SingletonWithDLC();
        }
      }
    }
    return instance;
  }
}

// best implementation in java is using enum, because it handles serialization
// and reflection attacks
enum EnumSingleton {
  INSTANCE;

  public void doSomething() {
    System.out.println("Doing something");
  }
}
