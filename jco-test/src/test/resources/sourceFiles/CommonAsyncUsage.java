import ranttu.rapid.jco.core.annotations.async;

public class CommonAsyncUsage {
    @async
    public int func(int a, int b) {
        double c, d;
        invoked();
        c = a + b;
        d = b;
        invoked2(c, d);
        return a + b;
    }

    public void invoked() {

    }

    public void invoked2(double c, double d) {

    }
}