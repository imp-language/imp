package org.imp.jvm;

import org.imp.runtime.Batteries;

import java.util.Objects;

public class TestFile {
    public static TestFile instance = new TestFile();

    public TestFile() {
    }

    public static void main(String[] var0) {
        TestFile var10002 = instance;
        Objects.requireNonNull(var10002);
        TestFile.Leaf var1 = var10002.new Leaf(4);
        Batteries.log(var1);
    }

    public class Empty {
        public Empty() {
        }
    }

    public class Leaf {
        public int data;

        public Leaf(int var2) {
            this.data = var2;
        }
    }

    public class Node {
        public Object left;
        public Object right;

        public Node(Object var2, Object var3) {
            this.left = var2;
            this.right = var3;
        }
    }
}
