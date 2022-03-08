package org.imp.jvm.lib;

public class date {
    public static date instance;

    static {
        instance = new date();
    }

    public static void main(String[] var0) {
    }

    public class Date {
        public int day;
        public int month;
        public int year;

        public Date(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public class Something {
        public String data;

        public Something() {
        }
    }
}
