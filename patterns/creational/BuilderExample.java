package creational;

import java.util.Map;

public class BuilderExample {
  public static void main(String[] args) {
    var http = new HttpRequest.Builder("https://example.com")
        .setHeaders(Map.of("Content-Type", "application/json"))
        .setBody("{\"key\": \"value\"}")
        .setTimeout(5000)
        .setRetries(-1)
        .build();
    System.out.println(http.getUrl());
  }
}

class HttpRequest {
  private final String url;
  private final String method;
  private final Map<String, String> headers;
  private final String body;
  private final int timeout;
  private final int retries;

  public String getUrl() {
    return url;
  }

  public String getMethod() {
    return method;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public int getTimeout() {
    return timeout;
  }

  public int getRetries() {
    return retries;
  }

  private HttpRequest(Builder builder) {
    this.url = builder.url;
    this.method = builder.method;
    this.headers = builder.headers;
    this.body = builder.body;
    this.timeout = builder.timeout;
    this.retries = builder.retries;
  }

  static class Builder {
    private String url;
    private String method = "GET";
    private Map<String, String> headers;
    private String body;
    private int timeout;
    private int retries;

    public Builder(String url) {
      this.url = url;
    }

    public Builder setUrl(String url) {
      this.url = url;
      return this;
    }

    public Builder setMethod(String method) {
      this.method = method;
      return this;
    }

    public Builder setHeaders(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder setBody(String body) {
      this.body = body;
      return this;
    }

    public Builder setTimeout(int timeout) {
      this.timeout = timeout;
      return this;
    }

    public Builder setRetries(int retries) {
      this.retries = retries;
      return this;
    }

    public HttpRequest build() {
      if (this.timeout < 0) {
        throw new IllegalArgumentException("Timeout must be non-negative");
      }
      if (this.retries < 0) {
        throw new IllegalArgumentException("Retries must be non-negative");
      }
      return new HttpRequest(this);
    }
  }
}
