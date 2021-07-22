package ie.k9ros.stsenvironmentsurveyor.controller

enum class ControllerInput(val keyCode: Int) {
    A(0),
    B(1),
    X(2),
    Y(3),
    LB(4),
    RB(5),
    BACK(6),
    START(7),
    LT(-1004),
    RT(1004),
    D_UP(-2000),
    D_DOWN(2000),
    D_LEFT(-2001),
    D_RIGHT(2001)
}
