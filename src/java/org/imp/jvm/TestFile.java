package org.imp.jvm;

import org.imp.runtime.Batteries;

public class TestFile {
    public static TestFile instance = new TestFile();

    public TestFile() {
    }

    public static void main(String[] var0) {
        var left = new Empty();
        var right = new Empty();
        var tree = new Tree(4, left, right);
        Batteries.log(tree);
    }

    public static class Empty {
        public Empty() {
        }
    }
    

    public static class Tree {
        public int data;
        public Object left;
        public Object right;

        public Tree(int var2, Object var3, Object var4) {
            this.data = var2;
            this.left = var3;
            this.right = var4;
        }
    }
}
