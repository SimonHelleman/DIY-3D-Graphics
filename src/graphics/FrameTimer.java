package graphics;

public class FrameTimer {
    private long lastTime;

    public FrameTimer() {
        lastTime = System.nanoTime();
    }

    public float mark() {
        long dt = System.nanoTime() - lastTime;
        lastTime = System.nanoTime();

        return dt / 1E+9f;
    }
}
