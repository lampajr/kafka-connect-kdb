package kx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KxConnectionIT {

  private String host = "127.0.0.1";
  private int port = 5010;
  private C kxConn;

  @Before
  public void setUp() throws Exception {
    kxConn = new C(host, port, "user:pwd", false);
  }

  @Test
  public void writeAsyncTest() {

  }

}