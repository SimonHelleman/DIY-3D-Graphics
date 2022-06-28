package graphics;

import hsa_ufa.Console;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Application extends Console {
    Mesh mesh;
    Matrix4 projection;

    // Not really a camera, but close enough for now
    Vector4 camera;
    Vector3 lookDirection;

    Vector3 light;

    float theta = 0.0f;

    boolean wireframeMode = false;

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    Random rng;
    FrameTimer ft;

    long lastTime;

    public Application() throws IOException {
        super(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackgroundColor(Color.BLACK);

        mesh = Mesh.generateFromObj("axis.obj");
        // mesh = Mesh.getCube();
        projection = Matrix4.projection(0.1f, 1000.0f, 90.0f, (WINDOW_HEIGHT * 1.0f) / (WINDOW_WIDTH * 1.0f));
        theta = 0.0f;

        rng = new Random();

        camera = new Vector4(0.0f, 0.0f, 0.0f);

        light = new Vector3(0.0f, 1.0f, -1.0f);
        light = light.normalize();

        ft = new FrameTimer();

        lastTime = System.currentTimeMillis();

    }

    public void draw() {
        Matrix4 rotZ = Matrix4.rotationZ(theta);
        Matrix4 rotX = Matrix4.rotationX(theta * 0.5f);
        Matrix4 translation = Matrix4.translation(0.0f, 0.0f, 16.0f);
        Matrix4 world = rotZ.multiply(rotX);
        world = world.multiply(translation);

        lookDirection = new Vector3(0.0f, 0.0f, 1.0f);
        Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
        Vector3 target = (new Vector3(camera)).add(lookDirection);

        Matrix4 cameraMatrix = Matrix4.pointAt(new Vector3(camera), target, up);
        Matrix4 viewMatrix = Matrix4.quickInverse(cameraMatrix);

        ArrayList<Triangle> renderList = new ArrayList<Triangle>();
        Iterator<Triangle> iter = mesh.iterator();
        while (iter.hasNext()) {
            Triangle t = iter.next();

            Triangle projected = new Triangle();
            Triangle transformed = new Triangle();
            Triangle viewed = new Triangle();

            transformed.vertices[0] = Matrix4.multiply(t.vertices[0], world);
            transformed.vertices[1] = Matrix4.multiply(t.vertices[1], world);
            transformed.vertices[2] = Matrix4.multiply(t.vertices[2], world);

            Vector4 line1 = transformed.vertices[1].subtract(transformed.vertices[0]);
            Vector4 line2 = transformed.vertices[2].subtract(transformed.vertices[0]);

            Vector3 normal = (new Vector3(line1)).crossProduct(new Vector3(line2));

            normal = normal.normalize();

            // don't render any triangles out of view by the camera (i.e. triangle culling)
            // any vertex on the triangle can be used since they all lie in the same plane
            if (normal.dotProduct(new Vector3(transformed.vertices[0].subtract(camera))) < 0) {

                projected.luminescence = normal.dotProduct(light);

                viewed.vertices[0] = Matrix4.multiply(transformed.vertices[0], viewMatrix);
                viewed.vertices[1] = Matrix4.multiply(transformed.vertices[1], viewMatrix);
                viewed.vertices[2] = Matrix4.multiply(transformed.vertices[2], viewMatrix);

                projected.vertices[0] = Matrix4.multiply(viewed.vertices[0], projection);
                projected.vertices[1] = Matrix4.multiply(viewed.vertices[1], projection);
                projected.vertices[2] = Matrix4.multiply(viewed.vertices[2], projection);

                projected.vertices[0] = projected.vertices[0].divideScaler(projected.vertices[0].w);
                projected.vertices[1] = projected.vertices[1].divideScaler(projected.vertices[1].w);
                projected.vertices[2] = projected.vertices[2].divideScaler(projected.vertices[2].w);

                projected.vertices[0].x *= -1;
                projected.vertices[1].x *= -1;
                projected.vertices[2].x *= -1;
                projected.vertices[0].y *= -1;
                projected.vertices[1].y *= -1;
                projected.vertices[2].y *= -1;

                // Translate NDC from [-1, 1] to [0, 2]
                projected.vertices[0].x += 1.0f;
                projected.vertices[1].x += 1.0f;
                projected.vertices[2].x += 1.0f;

                projected.vertices[0].y += 1.0f;
                projected.vertices[1].y += 1.0f;
                projected.vertices[2].y += 1.0f;

                

                // scale to window
                projected.vertices[0].x *= 0.5f * (float) WINDOW_WIDTH;
                projected.vertices[1].x *= 0.5f * (float) WINDOW_WIDTH;
                projected.vertices[2].x *= 0.5f * (float) WINDOW_WIDTH;

                projected.vertices[0].y *= 0.5f * (float) WINDOW_HEIGHT;
                projected.vertices[1].y *= 0.5f * (float) WINDOW_HEIGHT;
                projected.vertices[2].y *= 0.5f * (float) WINDOW_HEIGHT;

                renderList.add(projected);

            }
        }

        Collections.sort(renderList);

        synchronized (this) {
            clear();

            for (Triangle t : renderList) {
                int colorVal = Math.round(t.luminescence * 255);
                if (colorVal >= 0 && colorVal <= 255) {
                    setColor(new Color(colorVal, colorVal, colorVal));
                } else {
                    setColor(Color.BLACK);
                    // System.err.printf("INVALID COLOUR: %d theta = %.2f\n", colorVal,
                    // Math.toDegrees(Math.acos(t.luminescence)));
                }

                if (wireframeMode) {
                    drawTriangle(t.vertices[0].x, t.vertices[0].y, t.vertices[1].x, t.vertices[1].y, t.vertices[2].x,
                            t.vertices[2].y);
                } else {
                    fillTriangle(t.vertices[0].x, t.vertices[0].y, t.vertices[1].x, t.vertices[1].y, t.vertices[2].x,
                            t.vertices[2].y);
                }
            }
        }
    }

    public void update(float dt) {

        if (System.currentTimeMillis() - lastTime > 1000) {
            lastTime = System.currentTimeMillis();
            setTitle(String.format("%.2f FPS", 1 / dt));
        }

        if (!isKeyDown(VK_CONTROL)) {
            //theta += 0.97f * dt;
        }

        if (isKeyDown(VK_UP)) {
            camera.y += 8.0f * dt;
        }

        if (isKeyDown(VK_DOWN)) {
            camera.y -= 8.0f * dt;
        }

        if (isKeyDown(VK_LEFT)) {
            camera.x -= 8.0f * dt;
        }

        if (isKeyDown(VK_RIGHT)) {
            camera.x += 8.0f * dt;
        }

        if (isKeyDown('W')) {
            camera.z += 8.0f * dt;
        }

        if (isKeyDown('S')) {
            camera.z -= 8.0f * dt;
        }

        wireframeMode = isKeyDown(' ');
    }

    public void run() throws InterruptedException {
        float dt = ft.mark();
        update(dt);
        draw();
        //System.out.printf("%s %s\n", camera, light);
    }

    public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        drawLine(x1, y1, x2, y2);
        drawLine(x2, y2, x3, y3);
        drawLine(x3, y3, x1, y1);
    }

    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        drawTriangle(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2), Math.round(x3), Math.round(y3));
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] xPoints = new int[] { x1, x2, x3 };
        int[] yPoints = new int[] { y1, y2, y3 };
        fillPolygon(xPoints, yPoints, 3);
    }

    public void fillTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        fillTriangle(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2), Math.round(x3), Math.round(y3));
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Application app = new Application();
        while (true) {
            app.run();
        }
    }
}
