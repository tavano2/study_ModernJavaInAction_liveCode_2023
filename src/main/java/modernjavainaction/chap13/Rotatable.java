package modernjavainaction.chap13;

public interface Rotatable {

    void setRotationAngle(int angleInDegress);
    int getRotationAngle();

    default void rotateBy(int angleInDegrees) {
        setRotationAngle((getRotationAngle() + angleInDegrees) % 360);
    }

}
