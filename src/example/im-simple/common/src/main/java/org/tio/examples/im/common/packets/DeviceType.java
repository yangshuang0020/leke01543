// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/resources/chat.proto

package org.tio.examples.im.common.packets;

/**
 * Protobuf enum {@code org.tio.examples.im.common.packets.DeviceType}
 */
public enum DeviceType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>DEVICE_TYPE_UNKNOW = 0;</code>
   */
  DEVICE_TYPE_UNKNOW(0),
  /**
   * <code>DEVICE_TYPE_PC = 1;</code>
   */
  DEVICE_TYPE_PC(1),
  /**
   * <code>DEVICE_TYPE_ANDROID = 2;</code>
   */
  DEVICE_TYPE_ANDROID(2),
  /**
   * <code>DEVICE_TYPE_IOS = 3;</code>
   */
  DEVICE_TYPE_IOS(3),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>DEVICE_TYPE_UNKNOW = 0;</code>
   */
  public static final int DEVICE_TYPE_UNKNOW_VALUE = 0;
  /**
   * <code>DEVICE_TYPE_PC = 1;</code>
   */
  public static final int DEVICE_TYPE_PC_VALUE = 1;
  /**
   * <code>DEVICE_TYPE_ANDROID = 2;</code>
   */
  public static final int DEVICE_TYPE_ANDROID_VALUE = 2;
  /**
   * <code>DEVICE_TYPE_IOS = 3;</code>
   */
  public static final int DEVICE_TYPE_IOS_VALUE = 3;


  @Override
public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static DeviceType valueOf(int value) {
    return forNumber(value);
  }

  public static DeviceType forNumber(int value) {
    switch (value) {
      case 0: return DEVICE_TYPE_UNKNOW;
      case 1: return DEVICE_TYPE_PC;
      case 2: return DEVICE_TYPE_ANDROID;
      case 3: return DEVICE_TYPE_IOS;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<DeviceType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      DeviceType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<DeviceType>() {
          @Override
		public DeviceType findValueByNumber(int number) {
            return DeviceType.forNumber(number);
          }
        };

  @Override
public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  @Override
public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return org.tio.examples.im.common.packets.Chat.getDescriptor().getEnumTypes().get(0);
  }

  private static final DeviceType[] VALUES = values();

  public static DeviceType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private DeviceType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:org.tio.examples.im.common.packets.DeviceType)
}

