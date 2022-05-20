package org.imp.jvm;

public class TestFile {
    public static TestFile instance = new TestFile();

    public TestFile() {
    }

    public static void main(String[] param0) {
        var top = new Top(new Middle(new Bottom(2), new Bottom(9)));
        TestFile.log(top.middle.value);
    }

    public static void log(Object o) {
    }

    public static class Top {
        public TestFile.Middle middle;

        public Top(TestFile.Middle var1) {
            this.middle = var1;
        }

        public String toString() {
            return "Top[middle=" + this.middle + "]";
        }
    }

    public static class Middle {
        public TestFile.Bottom value;
        public TestFile.Bottom other;

        public Middle(TestFile.Bottom var1, TestFile.Bottom var2) {
            this.value = var1;
            this.other = var2;
        }

        public String toString() {
            return "Middle[value=" + this.value + ", other=" + this.other + "]";
        }
    }

    public static class Bottom {
        public int data;

        public Bottom(int var1) {
            this.data = var1;
        }

        public String toString() {
            return "Bottom[data=" + this.data + "]";
        }
    }
}
