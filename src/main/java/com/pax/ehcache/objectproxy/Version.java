package com.pax.ehcache.objectproxy;

public class Version {

    private static final String VERSION = "1.0.0";
    private static final String TITLE = "EHCACHE OBJECT PROXY";

    private Version() {
        throw new AssertionError();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void main(String[] args) {
        System.out.print(TITLE + " " + VERSION);
    }

}
