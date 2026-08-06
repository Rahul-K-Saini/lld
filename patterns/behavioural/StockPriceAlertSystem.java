package behavioural;

import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class StockPriceAlertSystem {

  void main() {
    Stock stock = new Stock('L', 150.0);

    stock.subscribe(
        new AuditLogger(),
        new PriceDisplayAdapter(),
        new TradingBot(),
        new Threshold(155.0));

    stock.setPrice(160.0);
    stock.setPrice(152.0);

    TradingBot bot = new TradingBot();

    stock.subscribe(bot);
    stock.unsubscribe(bot);
    stock.setPrice(170.0);
  }
}

record StockEventType(String name) {
}

interface StockEventListener extends EventListener {
  void onPriceUpdate(double price, StockEventType type);
}

class PriceDisplayAdapter implements StockEventListener {
  @Override
  public void onPriceUpdate(double price, StockEventType type) {
    System.out.println("Displaying price for " + type.name() + " new price: " + price);
  }
}

class Threshold implements StockEventListener {
  private final double threshold;

  public Threshold(double threshold) {
    this.threshold = threshold;
  }

  @Override
  public void onPriceUpdate(double price, StockEventType type) {
    if (price > threshold) {
      System.out.println("Threshold exceeded for " + type.name() + " new price: " + price);
    }
  }
}

class TradingBot implements StockEventListener {
  @Override
  public void onPriceUpdate(double price, StockEventType type) {
    System.out.println("Trading bot for " + type.name() + " new price: " + price);
  }
}

class AuditLogger implements StockEventListener {
  @Override
  public void onPriceUpdate(double price, StockEventType type) {
    System.out.println("Audit for " + type.name() + " new price: " + price);
  }
}

class StockEventManager {
  private final ConcurrentHashMap<StockEventType, List<StockEventListener>> eventToListenersMap;

  StockEventManager() {
    eventToListenersMap = new ConcurrentHashMap<>();
  }

  public void notify(double price, StockEventType type) {
    List<StockEventListener> listeners = eventToListenersMap.get(type);
    if (listeners == null) {
      return;
    }
    for (StockEventListener listener : listeners) {
      listener.onPriceUpdate(price, type);
    }
  }

  public void subscribe(StockEventType eventType, StockEventListener... listeners) {
    List<StockEventListener> list = eventToListenersMap.computeIfAbsent(eventType, key -> new CopyOnWriteArrayList<>());
    Collections.addAll(list, listeners);
  }

  public void unsubscribe(StockEventType eventType, StockEventListener... listeners) {
    List<StockEventListener> list = eventToListenersMap.get(eventType);
    if (list == null) {
      return;
    }

    for (StockEventListener l : listeners) {
      list.remove(l);
    }

    if (list.isEmpty()) {
      eventToListenersMap.remove(eventType, list);
    }
  }
}

class Stock {
  private final StockEventManager eventManager;
  private final StockEventType type = new StockEventType("STOCK_PRICE_UPDATE");

  private Character symbol;
  private volatile double price;

  Stock(Character symbol, double price) {
    this.symbol = symbol;
    this.price = price;
    this.eventManager = new StockEventManager();
  }

  public void subscribe(StockEventListener... listeners) {
    eventManager.subscribe(type, listeners);
  }

  public void unsubscribe(StockEventListener... listeners) {
    eventManager.unsubscribe(type, listeners);
  }

  public Character getSymbol() {
    return symbol;
  }

  public void setSymbol(Character symbol) {
    this.symbol = symbol;
  }

  public double getPrice() {
    return price;
  }

  public synchronized void setPrice(double price) {
    this.price = price;
    eventManager.notify(this.price, type);
  }
}
