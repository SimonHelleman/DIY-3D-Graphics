package graphics;

public class Vector4 {
    public float x;
    public float y;
    public float z;
    public float w;

    // let java give everything else 0.0f by default
    public Vector4() {
        w = 1.0f;
    }

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(float x, float y, float z) {
        this(x, y, z, 1.0f);
    }

    public Vector4(Vector4 other) {
        x = other.x;
        y = other.y;
        z = other.z;
        w = other.w;
    }

    public Vector4(Vector3 other, float w) {
        x = other.x;
        y = other.y;
        z = other.z;
        this.w = w;
    }

    public float magnitude() {
        return MathUtil.sqrtf(x * x + y * y + z * z + w * w);
    }

    public Vector4 normalize() {
        float mag = magnitude();

        return new Vector4(x / mag, y / mag, z / mag, w / mag);
    }

    public Vector4 add(Vector4 other) {
        return new Vector4(x + other.x, y + other.y, z + other.z, w + other.w);
    }

    public Vector4 subtract(Vector4 other) {
        return new Vector4(x - other.x, y - other.y, z - other.z, w - other.w);
    }

    public Vector4 multiplyPairwise(Vector4 other) {
        return new Vector4(x * other.x, y * other.y, z * other.z, w * other.w);
    }

    public Vector4 dividePairwise(Vector4 other) {
        return new Vector4(x / other.x, y / other.y, z / other.z, w / other.w);
    }

    public Vector4 multiplyScaler(float val) {
        return new Vector4(x / val, y / val, z / val, w / val);
    }

    public Vector4 divideScaler(float val) {
        return new Vector4(x / val, y / val, z / val, w / val);
    }
}
