package engine.Inputs;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector2f;

/**
 * Manages user inputs in a more useful way
 * 
 * Lets devs interact with input data without needing to use raw data
 *
 * @author Lex Stapleton  
 */
public final class Input {
    /**
     * Represents the key codes for GL's key listener
     */
    public enum KeyCode{
        UNKNOWN(-1),
        MOUSE_BUTTON_0(0),
        MOUSE_BUTTON_1(1),
        MOUSE_BUTTON_2(2),
        MOUSE_BUTTON_3(3),
        MOUSE_BUTTON_4(4),
        MOUSE_BUTTON_5(5),
        MOUSE_BUTTON_6(6),
        MOUSE_BUTTON_7(7),
        SPACE(32),
        APOSTROPHE(39),
        COMMA(44),
        MINUS(45),
        PERIOD(46),
        SLASH(47),
        ZERO(48),
        ONE(49),
        TWO(50),
        THREE(51),
        FOUR(52),
        FIVE(53),
        SIX(54),
        SEVEN(55),
        EIGHT(56),
        NINE(57),
        SEMICOLON(59),
        EQUAL(61),
        A(65),
        B(66),
        C(67),
        D(68),
        E(69),
        F(70),
        G(71),
        H(72),
        I(73),
        J(74),
        K(75),
        L(76),
        M(77),
        N(78),
        O(79),
        P(80),
        Q(81),
        R(82),
        S(83),
        T(84),
        U(85),
        V(86),
        W(87),
        X(88),
        Y(89),
        Z(90),
        LEFT_BRACKET(91),
        BACKSLASH(92),
        RIGHT_BRACKET(93),
        GRAVE_ACCENT(96),
        WORLD_1(161),
        WORLD_2(162),
        ESCAPE(256),
        ENTER(257),
        TAB(258),
        BACKSPACE(259),
        INSERT(260),
        DELETE(261),
        RIGHT(262),
        LEFT(263),
        DOWN(264),
        UP(265),
        PAGE_UP(266),
        PAGE_DOWN(267),
        HOME(268),
        END(269),
        CAPS_LOCK(280),
        SCROLL_LOCK(281),
        NUM_LOCK(282),
        PRINT_SCREEN(283),
        PAUSE(284),
        F1(290),
        F2(291),
        F3(292),
        F4(293),
        F5(294),
        F6(295),
        F7(296),
        F8(297),
        F9(298),
        F10(299),
        F11(300),
        F12(301),
        F13(302),
        F14(303),
        F15(304),
        F16(305),
        F17(306),
        F18(307),
        F19(308),
        F20(309),
        F21(310),
        F22(311),
        F23(312),
        F24(313),
        F25(314),
        KP_0(320),
        KP_1(321),
        KP_2(322),
        KP_3(323),
        KP_4(324),
        KP_5(325),
        KP_6(326),
        KP_7(327),
        KP_8(328),
        KP_9(329),
        KP_DECIMAL(330),
        KP_DIVIDE(331),
        KP_MULTIPLY(332),
        KP_SUBTRACT(333),
        KP_ADD(334),
        KP_ENTER(335),
        KP_EQUAL(336),
        LEFT_SHIFT(340),
        LEFT_CONTROL(341),
        LEFT_ALT(342),
        LEFT_SUPER(343),
        RIGHT_SHIFT(344),
        RIGHT_CONTROL(345),
        RIGHT_ALT(346),
        RIGHT_SUPER(347),
        MENU(348),
        LAST(348);

        private int value;

        private KeyCode(int value){
            this.value = value;
        }

        /**
         * Returns the value of the keycode
         * 
         * use this for indexing boolean array
         * 
         * @return
         */
        public int getValue(){
            return value;
        }
    }

    /**
     * Singleton construction
     */
    private static Input instance;

    private Input(){
        axes = new HashMap<>();
        for (int i = 0; i < keyboardButtonyStates.length; i++) {
            keyboardButtonyStates[i] = new ButtonState(false);
        }

        for (int i = 0; i < mouseButtonStates.length; i++) {
            mouseButtonStates[i] = new ButtonState(false);
        }
    }

    /**
     * Returns the static singleton
     * 
     * Instantiates the singleton is it hasn't already
     * 
     * @return the static singleton
     */
    private static Input get(){
        if(Input.instance == null){
            Input.instance = new Input();
        }

        return Input.instance;
    }

    // SECTION: Axes

    /**
     * Named axes that interpolate between the state of two keys 
     */
    private Map<String, InputAxis> axes;

    /**
     * Adds a new input axis to axes
     * 
     * @param name the name of the new axis
     * @param axis the new axis
     */
    public  static void addAxis(String name, InputAxis axis){
        get().axes.put(name, axis);
    }

    /**
     * Gets the input values of a given axis
     * 
     * @param name the name of the axis
     * @return its current value
     * 
     * @see InputAxis
     */
    public static float getAxis(String name){
        if(!get().axes.containsKey(name)) return 0f;

        return get().axes.get(name).getValue();
    }


    // SECTION: KEYBOARD
    /**
     * Gets state of key on the keyboard
     * 
     * @param i the key code
     * @return the state of the button
     * 
     * @see Input.KeyCode
     */
    private ButtonState keyboardButtonyStates[] = new ButtonState[350];
    public static boolean getKeyboardButtonPressed(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().keyboardButtonyStates.length) return false;
        return get().keyboardButtonyStates[i.getValue()].getPressed();
    } 
    
    public static boolean getKeyboardButtonReleased(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().keyboardButtonyStates.length) return false;
        return get().keyboardButtonyStates[i.getValue()].getReleased();
    }

    public static boolean getKeyboardButtonChanged(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().keyboardButtonyStates.length) return false;
        return get().keyboardButtonyStates[i.getValue()].getChanged();
    }

    public static boolean getKeyboardButtonHeld(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().keyboardButtonyStates.length) return false;
        return get().keyboardButtonyStates[i.getValue()].getValue() && 
               !get().keyboardButtonyStates[i.getValue()].getChanged();
    }  

    // SECTION: MOUSE
    /**
     * Gets state of key on the keyboard
     * 
     * @param i the key code
     * @return the state of the button
     * 
     * @see Input.KeyCode
     */
    private ButtonState mouseButtonStates[] = new ButtonState[8];
    public static boolean getMouseButtonPressed(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().mouseButtonStates.length) return false;
        return get().mouseButtonStates[i.getValue()].getPressed();
    } 
    
    public static boolean getMouseButtonReleased(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().mouseButtonStates.length) return false;
        return get().mouseButtonStates[i.getValue()].getReleased();
    }

    public static boolean getMouseButtonChanged(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().mouseButtonStates.length) return false;
        return get().mouseButtonStates[i.getValue()].getChanged();
    }

    public static boolean getMouseButtonHeld(KeyCode i){
        if(i.getValue() < 0 || i.getValue() >= get().mouseButtonStates.length) return false;
        return get().mouseButtonStates[i.getValue()].getValue() &&
              !get().mouseButtonStates[i.getValue()].getChanged();
    }

    public static boolean getMouseDragging(){
        return MouseListener.getDragging();
    }

    public static Vector2f getMousePosition(){
        return new Vector2f(
            (float)MouseListener.getMouseX(),
            (float)MouseListener.getMouseY()
        );
    }

    public static float getScrollPosition(){
        return (float)MouseListener.getScrollY();
    }

    
    /**
     * Updates the axis and button states
     * 
     * @param dt the time that has passed between frames
     */
    public static void Update(float dt){
        for (InputAxis axis : get().axes.values()) {
            axis.Update(dt);
        }

        /**
         * Update the key
         */
        for (int i = 0; i < get().keyboardButtonyStates.length; i++) {
            get().keyboardButtonyStates[i].Refresh(KeyListener.getKeyPressed(i));
        }

        /**
         * Update the mouse
         */
        for (int i = 0; i < get().mouseButtonStates.length; i++) {
            get().mouseButtonStates[i].Refresh(MouseListener.getMouseButtonDown(i));
        }
    }
}
