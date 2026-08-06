package behavioural;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Observer {

  void main() {
    MyEventManager eventManager = new MyEventManager();

    EventType<String> MEET = new EventType<>("meet");
    EventType<Integer> SCORE = new EventType<>("score");

    ListenerA listenerA = new ListenerA();
    ListenerB listenerB = new ListenerB();

    eventManager.subscribe(MEET, listenerA);
    eventManager.subscribe(SCORE, listenerB);
    eventManager.notify(MEET, "Meetup hogyi");
    eventManager.notify(SCORE, 100);
    eventManager.unsubscribe(MEET, listenerA);
    eventManager.notify(MEET, "Another meetup");
  }
}

record EventType<T>(String name) {}

interface MyEventListener<T> {
  void doAction(T someKindOfData);
}

class MyEventManager {

  private final Map<EventType<?>, List<MyEventListener<?>>> eventToListenerMap;

  public MyEventManager() {
    eventToListenerMap = new ConcurrentHashMap<>();
  }

  public <T> void subscribe(
          EventType<T> eventType,
          MyEventListener<T> listener
  ) {

    eventToListenerMap
            .computeIfAbsent(eventType, key -> new CopyOnWriteArrayList<>())
            .add(listener);
  }


  public <T> void unsubscribe(
          EventType<T> eventType,
          MyEventListener<T> listener
  ) {

    List<MyEventListener<?>> listeners =
            eventToListenerMap.get(eventType);

    if (listeners != null) {
      listeners.remove(listener);

      if (listeners.isEmpty()) {
        eventToListenerMap.remove(eventType);
      }
    }
  }


  @SuppressWarnings("unchecked")
  public <T> void notify(
          EventType<T> eventType,
          T someKindOfData
  ) {

    List<MyEventListener<?>> listeners =
            eventToListenerMap.get(eventType);

    if (listeners == null) {
      return;
    }

    for (MyEventListener<?> listener : listeners) {

      MyEventListener<T> typedListener =
              (MyEventListener<T>) listener;

      typedListener.doAction(someKindOfData);
    }
  }
}


class ListenerA implements MyEventListener<String> {

  @Override
  public void doAction(String someKindOfData) {
    System.out.println(
            "ListenerA here: " + someKindOfData
    );
  }
}


class ListenerB implements MyEventListener<Integer> {

  @Override
  public void doAction(Integer someKindOfData) {
    System.out.println(
            "ListenerB here: " + someKindOfData
    );
  }
}
