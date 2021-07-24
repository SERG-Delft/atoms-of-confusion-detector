class MyClass {
    public void bar(int a) {
        if (a == 2 || a++) {
            System.out.println(a);
        }
        int b = (a == 3) ? 4 : 5;
    }
}
