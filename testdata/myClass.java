class MyClass {

    int a = 42;
    int b = 43 + --a;
    int c = 23 + a++;

    public void bar(int a) {
        if (a == 2 || a++) {
            System.out.println(a);
        }
        int b = (a == 3) ? 4 : 5;
    }
}
