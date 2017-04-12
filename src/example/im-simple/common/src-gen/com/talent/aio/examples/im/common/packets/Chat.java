// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: main/resources/chat.proto

package org.tio.examples.im.common.packets;

public final class Chat {
  private Chat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_BaseReqBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_BaseReqBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_AuthReqBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_AuthReqBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_AuthRespBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_AuthRespBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_JoinReqBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_JoinReqBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_JoinRespBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_JoinRespBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_ChatReqBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_ChatReqBody_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_talent_aio_examples_im_common_packets_ChatRespBody_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_talent_aio_examples_im_common_packets_ChatRespBody_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031main/resources/chat.proto\022)com.talent." +
      "aio.examples.im.common.packets\"\033\n\013BaseRe" +
      "qBody\022\014\n\004time\030\001 \001(\003\"\327\001\n\013AuthReqBody\022\014\n\004t" +
      "ime\030\001 \001(\003\022\020\n\010deviceId\030\002 \001(\t\022\r\n\005token\030\003 \001" +
      "(\t\022I\n\ndeviceType\030\004 \001(\01625.org.tio." +
      "examples.im.common.packets.DeviceType\022\013\n" +
      "\003cid\030\005 \001(\t\022\022\n\nappVersion\030\006 \001(\t\022\022\n\ndevice" +
      "Info\030\007 \001(\t\022\013\n\003seq\030\010 \001(\003\022\014\n\004sign\030\t \001(\t\"\034\n" +
      "\014AuthRespBody\022\014\n\004time\030\001 \001(\003\"*\n\013JoinReqBo" +
      "dy\022\014\n\004time\030\001 \001(\003\022\r\n\005group\030\002 \001(\t\"w\n\014JoinR",
      "espBody\022\014\n\004time\030\001 \001(\003\022J\n\006result\030\002 \001(\0162:." +
      "org.tio.examples.im.common.packet" +
      "s.JoinGroupResult\022\r\n\005group\030\003 \001(\t\"\231\001\n\013Cha" +
      "tReqBody\022\014\n\004time\030\001 \001(\003\022A\n\004type\030\002 \001(\01623.c" +
      "om.tio.examples.im.common.packets" +
      ".ChatType\022\014\n\004text\030\003 \001(\t\022\r\n\005group\030\004 \001(\t\022\014" +
      "\n\004toId\030\005 \001(\005\022\016\n\006toNick\030\006 \001(\t\"\274\001\n\014ChatRes" +
      "pBody\022\014\n\004time\030\001 \001(\003\022A\n\004type\030\002 \001(\01623.com." +
      "tio.examples.im.common.packets.Ch" +
      "atType\022\014\n\004text\030\003 \001(\t\022\016\n\006fromId\030\004 \001(\005\022\020\n\010",
      "fromNick\030\005 \001(\t\022\014\n\004toId\030\006 \001(\005\022\016\n\006toNick\030\007" +
      " \001(\t\022\r\n\005group\030\010 \001(\t*f\n\nDeviceType\022\026\n\022DEV" +
      "ICE_TYPE_UNKNOW\020\000\022\022\n\016DEVICE_TYPE_PC\020\001\022\027\n" +
      "\023DEVICE_TYPE_ANDROID\020\002\022\023\n\017DEVICE_TYPE_IO" +
      "S\020\003*\320\001\n\017JoinGroupResult\022\034\n\030JOIN_GROUP_RE" +
      "SULT_UNKNOW\020\000\022\030\n\024JOIN_GROUP_RESULT_OK\020\001\022" +
      "\037\n\033JOIN_GROUP_RESULT_NOT_EXIST\020\002\022 \n\034JOIN" +
      "_GROUP_RESULT_GROUP_FULL\020\003\022!\n\035JOIN_GROUP" +
      "_RESULT_IN_BACKLIST\020\004\022\037\n\033JOIN_GROUP_RESU" +
      "LT_TAKEOUTED\020\005*M\n\010ChatType\022\024\n\020CHAT_TYPE_",
      "UNKNOW\020\000\022\024\n\020CHAT_TYPE_PUBLIC\020\001\022\025\n\021CHAT_T" +
      "YPE_PRIVATE\020\002B-\n)org.tio.examples" +
      ".im.common.packetsP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_talent_aio_examples_im_common_packets_BaseReqBody_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_talent_aio_examples_im_common_packets_BaseReqBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_BaseReqBody_descriptor,
        new java.lang.String[] { "Time", });
    internal_static_com_talent_aio_examples_im_common_packets_AuthReqBody_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_talent_aio_examples_im_common_packets_AuthReqBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_AuthReqBody_descriptor,
        new java.lang.String[] { "Time", "DeviceId", "Token", "DeviceType", "Cid", "AppVersion", "DeviceInfo", "Seq", "Sign", });
    internal_static_com_talent_aio_examples_im_common_packets_AuthRespBody_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_talent_aio_examples_im_common_packets_AuthRespBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_AuthRespBody_descriptor,
        new java.lang.String[] { "Time", });
    internal_static_com_talent_aio_examples_im_common_packets_JoinReqBody_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_talent_aio_examples_im_common_packets_JoinReqBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_JoinReqBody_descriptor,
        new java.lang.String[] { "Time", "Group", });
    internal_static_com_talent_aio_examples_im_common_packets_JoinRespBody_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_talent_aio_examples_im_common_packets_JoinRespBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_JoinRespBody_descriptor,
        new java.lang.String[] { "Time", "Result", "Group", });
    internal_static_com_talent_aio_examples_im_common_packets_ChatReqBody_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_talent_aio_examples_im_common_packets_ChatReqBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_ChatReqBody_descriptor,
        new java.lang.String[] { "Time", "Type", "Text", "Group", "ToId", "ToNick", });
    internal_static_com_talent_aio_examples_im_common_packets_ChatRespBody_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_com_talent_aio_examples_im_common_packets_ChatRespBody_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_talent_aio_examples_im_common_packets_ChatRespBody_descriptor,
        new java.lang.String[] { "Time", "Type", "Text", "FromId", "FromNick", "ToId", "ToNick", "Group", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
