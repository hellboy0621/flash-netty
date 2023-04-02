package demo.im.protocol.command;

public interface Command {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte CREATE_GROUP_REQUEST = 5;
    Byte CREATE_GROUP_RESPONSE = 6;
    Byte JOIN_GROUP_REQUEST = 7;
    Byte JOIN_GROUP_RESPONSE = 8;
    Byte QUIT_GROUP_REQUEST = 9;
    Byte QUIT_GROUP_RESPONSE = 10;
    Byte LIST_GROUP_MEMBERS_REQUEST = 11;
    Byte LIST_GROUP_MEMBERS_RESPONSE = 12;

}
