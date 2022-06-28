package graphics;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3() {

    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // truncate the w component
    public Vector3(Vector4 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public float magnitude() {
        return MathUtil.sqrtf(x * x + y * y + z * z);
    }

    public Vector3 normalize() {
        float mag = magnitude();

        return new Vector3(x / mag, y / mag, z / mag);
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }

    public Vector3 multiplyPairwise(Vector3 other) {
        return new Vector3(x * other.x, y * other.y, z * other.z);
    }

    public Vector3 dividePairwise(Vector3 other) {
        return new Vector3(x / other.x, y / other.y, z / other.z);
    }

    public Vector3 multiplyScaler(float val) {
        return new Vector3(x * val, y * val, z * val);
    }

    public Vector3 divideScaler(float val) {
        return new Vector3(x / val, y / val, z / val);
    }

    public float dotProduct(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public Vector3 crossProduct(Vector3 other) {
        return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
    }

    @Override
    public String toString() {
        return String.format("|{%f, %f, %f}| = %f", x, y, z, magnitude());
    }
}