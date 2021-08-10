class MyClass {

    int a = 42;
    int b = 43 + --a;
    int c = 23 + a++;

    public void bar(int a, int c) {
        if (a == 2 || a++) {
            System.out.println(a);
        }
        a = 3;
        int b = (a == 3) ? 4 : 5;
        b = 34;
    }
}
