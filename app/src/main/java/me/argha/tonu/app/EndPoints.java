package me.argha.tonu.app;

public class EndPoints {

    // localhost url -
    public static String BASE = "192.168.0.102";
    public static String BASE_URL = "http://"+BASE+"/tonu/v1";
    public static final String LOGIN = BASE_URL + "/user/login";
    public static final String UPDATEGCM = BASE_URL + "/user/";
    public static final String SENDMESSAGE = BASE_URL + "/user/sendmessage";
    public static final String SENDLOCATIONBYNUMBER = BASE_URL + "/user/sendLocationByNumber";
    public static final String ADDDANGER = BASE_URL + "/user/addDanger";
    public static final String GETDANGERZONES = BASE_URL + "/getDangerZones";
//    public static final String CHAT_ROOMS = BASE_URL + "/chat_rooms";
//    public static final String CHAT_THREAD = BASE_URL + "/chat_rooms/_ID_";
//    public static final String CHAT_ROOM_MESSAGE = BASE_URL + "/chat_rooms/_ID_/message";
}