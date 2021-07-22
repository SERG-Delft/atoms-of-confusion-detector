class MyClass {

    public int foo(int a) {
        int ret = (a == 4) ? 42 : 43;
        return ret;
    }

    public void bar(int a) {
        if (a == 2 || a++) {
            System.out.println(a);
        }
    }

    public void arithmetic(int a) {
        int b = a + 3 / 4;
    }

}
