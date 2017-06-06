package redis.clients.jedis.params.set;

import java.util.ArrayList;

import redis.clients.jedis.params.Params;
import redis.clients.util.SafeEncoder;

public class SetParams extends Params {

//SET key value [EX seconds] [PX milliseconds] [NX|XX]
//  EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
//  PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
//  NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
//  XX ：只在键已经存在时，才对键进行设置操作。

  private static final String XX = "xx";
  private static final String NX = "nx";
  private static final String PX = "px";
  private static final String EX = "ex";

  private SetParams() {
  }

  public static SetParams setParams() {
    return new SetParams();
  }

  /**
   * Set the specified expire time, in seconds.
   * @param secondsToExpire
   * @return SetParams
   */
  public SetParams ex(int secondsToExpire) {
    addParam(EX, secondsToExpire);
    return this;
  }

  /**
   * Set the specified expire time, in milliseconds.
   * @param millisecondsToExpire
   * @return SetParams
   */
  public SetParams px(long millisecondsToExpire) {
    addParam(PX, millisecondsToExpire);
    return this;
  }

  /**
   * Only set the key if it does not already exist.
   * @return SetParams
   */
  public SetParams nx() {
    addParam(NX);
    return this;
  }

  /**
   * Only set the key if it already exist.
   * @return SetParams
   */
  public SetParams xx() {
    addParam(XX);
    return this;
  }

  public byte[][] getByteParams(byte[]... args) {
    ArrayList<byte[]> byteParams = new ArrayList<byte[]>();
    for (byte[] arg : args) {
      byteParams.add(arg);
    }

    if (contains(NX)) {
      byteParams.add(SafeEncoder.encode(NX));
    }
    if (contains(XX)) {
      byteParams.add(SafeEncoder.encode(XX));
    }

    if (contains(EX)) {
      byteParams.add(SafeEncoder.encode(EX));
      byteParams.add(SafeEncoder.encode(String.valueOf(getParam(EX))));
    }
    if (contains(PX)) {
      byteParams.add(SafeEncoder.encode(PX));
      byteParams.add(SafeEncoder.encode(String.valueOf(getParam(PX))));
    }

    return byteParams.toArray(new byte[byteParams.size()][]);
  }

}
