public class BoundedStackTest {

    private static int passCount = 0;
    private static int failCount = 0;

    public static void main(String[] args) {
        
        // --- Creator ---
        testCustomCapacity();
        testNewStackPeekThrows();
        testNewStackPopThrows();
        testNewStackIsNotFull();
        testInvalidCapacityThrows();
        testNewStackIsEmpty();

        // --- Producer (push) + Observer (peek) ---
        testPushThenPeekReturnsSameElement();
        testPushTwiceThenPeekReturnsLastPushed();
        testPushMultipleThenPeekIsLIFO();
        testPushMakesStackNotEmpty();
        testSizeAfterPushAndPop();
        testPeekDoesNotRemoveElement();

        // --- Mutator (pop) ---
        testPushThenPopReturnsElementAndEmptiesStack();
        testPushTwiceThenPopReturnsInLIFOOrder();
        testPopAfterEmptyingThrowsAgain();
        testPopMakesStackEmpty();

        // --- Capacity boundary ---
        testPushUntilCapacityMakesFull();
        testPushBeyondCapacityThrows();
        testPeekAfterFillingReturnsLastPushed();
        testPopFromFullStackMakesItNotFull();

        // --- Invalid input ---
        testPushNullThrows();
        testPushEmptyStringSucceeds();

        // --- push/pop สลับกันไปมา ---
        testInterleavedPushPopMaintainsLIFO();
        testEmptyThenPushAgainWorks();

        System.out.println();
        System.out.println("SUMMARY");
        System.out.println("PASS: " + passCount + "  FAIL: " + failCount + "  TOTAL: " + (passCount + failCount));

        if (failCount > 0) {
            System.out.println("RESULT: FAIL");
        } else {
            System.out.println("RESULT: PASS");
        }
    }

    // ---------- เคสเทสต่างๆ ----------

    private static void testPopMakesStackEmpty() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.pop();

        assertTrue(
            "after pop last element, stack should be empty",
            s.isEmpty()
        );
    }
    
    private static void testInvalidCapacityThrows() {
        assertThrows(
            "capacity <= 0 must throw IllegalArgumentException",
            IllegalArgumentException.class,
            () -> new BoundedStack(0)
        );

        assertThrows(
            "negative capacity must throw IllegalArgumentException",
            IllegalArgumentException.class,
            () -> new BoundedStack(-1)
        );
    }
    
    private static void testCustomCapacity() {
        BoundedStack s = new BoundedStack(3);

        s.push("Pikachu");
        s.push("Charizard");
        s.push("Bulbasaur");

        assertTrue(
            "stack with capacity 3 should be full",
            s.isFull()
        );

        assertThrows(
            "pushing over custom capacity should throw",
            IllegalStateException.class,
            () -> s.push("Squirtle")
        );
    }

    private static void testNewStackPeekThrows() {
        BoundedStack s = new BoundedStack(100);

        assertThrows(
            "new stack: peek() must throw IllegalStateException",
            IllegalStateException.class,
            s::peek
        );
    }

    private static void testNewStackPopThrows() {
        BoundedStack s = new BoundedStack(100);

        assertThrows(
            "new stack: pop() must throw IllegalStateException",
            IllegalStateException.class,
            s::pop
        );
    }

    private static void testNewStackIsNotFull() {
        BoundedStack s = new BoundedStack(100);

        assertFalse(
            "new stack: isFull() must be false",
            s.isFull()
        );
    }

    private static void testPushThenPeekReturnsSameElement() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");

        assertEquals(
            "push 1 element: peek() must return that same element",
            "Pikachu",
            s.peek()
        );
    }

    private static void testPushTwiceThenPeekReturnsLastPushed() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.push("Charizard");

        assertEquals(
            "peek() must return the last pushed element (LIFO)",
            "Charizard",
            s.peek()
        );
    }

    private static void testPushMultipleThenPeekIsLIFO() {
        BoundedStack s = new BoundedStack(100);

        s.push("Bulbasaur");
        s.push("Squirtle");
        s.push("Charmander");

        assertEquals(
            "peek() must return the top element (Charmander)",
            "Charmander",
            s.peek()
        );
    }

    private static void testPushThenPopReturnsElementAndEmptiesStack() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");

        String popped = s.pop();

        assertEquals(
            "pop() must return the element just pushed",
            "Pikachu",
            popped
        );

        assertThrows(
            "after popping to empty, peek() must throw",
            IllegalStateException.class,
            s::peek
        );
    }

    private static void testPushTwiceThenPopReturnsInLIFOOrder() {
        BoundedStack s = new BoundedStack(100);

        s.push("Bulbasaur");
        s.push("Squirtle");

        assertEquals(
            "first pop() must return Squirtle (last pushed)",
            "Squirtle",
            s.pop()
        );

        assertEquals(
            "second pop() must return Bulbasaur",
            "Bulbasaur",
            s.pop()
        );
    }

    private static void testPopAfterEmptyingThrowsAgain() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.pop();

        assertThrows(
            "pop() on an already-empty stack must throw again",
            IllegalStateException.class,
            s::pop
        );
    }

    // ---------- Capacity boundary ----------

    private static void testPushUntilCapacityMakesFull() {
        BoundedStack s = new BoundedStack(100);

        for (int i = 0; i < 100; i++) {
            s.push("Pokemon" + i);
        }

        assertTrue(
            "after pushing 100 Pokemon (capacity), isFull() must be true",
            s.isFull()
        );
    }

    private static void testPushBeyondCapacityThrows() {
        BoundedStack s = new BoundedStack(100);

        for (int i = 0; i < 100; i++) {
            s.push("Pokemon" + i);
        }

        assertThrows(
            "pushing the 101st Pokemon must throw IllegalStateException",
            IllegalStateException.class,
            () -> s.push("Mewtwo")
        );
    }

    private static void testPeekAfterFillingReturnsLastPushed() {
        BoundedStack s = new BoundedStack(100);

        for (int i = 0; i < 100; i++) {
            s.push("Pokemon" + i);
        }

        assertEquals(
            "once full, peek() must return the last pushed Pokemon (Pokemon99)",
            "Pokemon99",
            s.peek()
        );
    }

    private static void testPopFromFullStackMakesItNotFull() {
        BoundedStack s = new BoundedStack(100);

        for (int i = 0; i < 100; i++) {
            s.push("Pokemon" + i);
        }

        s.pop();

        assertFalse(
            "after popping once from a full stack, isFull() must be false",
            s.isFull()
        );
    }

    // ---------- Invalid input ----------

    private static void testPushNullThrows() {
        BoundedStack s = new BoundedStack(100);

        assertThrows(
            "push(null) must throw IllegalArgumentException",
            IllegalArgumentException.class,
            () -> s.push(null)
        );
    }

    private static void testPushEmptyStringSucceeds() {
        BoundedStack s = new BoundedStack(100);

        s.push("");

        assertEquals(
            "push(\"\") should allow empty string",
            "",
            s.peek()
        );
    }

    // ---------- Batch 2: push/pop สลับกันไปมา ----------

    private static void testInterleavedPushPopMaintainsLIFO() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.push("Charizard");

        assertEquals(
            "push Pikachu, Charizard then first pop must return Charizard",
            "Charizard",
            s.pop()
        );

        s.push("Mew");

        assertEquals(
            "push Mew after popping Charizard: peek() must return Mew",
            "Mew",
            s.peek()
        );

        assertEquals(
            "pop must return Mew",
            "Mew",
            s.pop()
        );

        assertEquals(
            "pop must return Pikachu",
            "Pikachu",
            s.pop()
        );
    }

    private static void testEmptyThenPushAgainWorks() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.pop();

        assertThrows(
            "stack is empty: peek() must throw",
            IllegalStateException.class,
            s::peek
        );

        s.push("Eevee");

        assertEquals(
            "pushing again after being emptied must work normally",
            "Eevee",
            s.peek()
        );

        assertFalse(
            "after pushing 1 Pokemon, isFull() must still be false",
            s.isFull()
        );
    }

    private static void testNewStackIsEmpty() {
        BoundedStack s = new BoundedStack(100);

        assertTrue(
            "new stack should be empty",
            s.isEmpty()
        );
    }

    private static void testPushMakesStackNotEmpty() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");

        assertFalse(
            "stack should not be empty after push",
            s.isEmpty()
        );
    }

    private static void testSizeAfterPushAndPop() {
        BoundedStack s = new BoundedStack(100);

        assertEquals(
            "new stack size",
            0,
            s.size()
        );

        s.push("Pikachu");

        assertEquals(
            "size after push",
            1,
            s.size()
        );

        s.pop();

        assertEquals(
            "size after pop",
            0,
            s.size()
        );
    }

    private static void testPeekDoesNotRemoveElement() {
        BoundedStack s = new BoundedStack(100);

        s.push("Pikachu");
        s.peek();

        assertEquals(
            "peek should not remove Pokemon",
            1,
            s.size()
        );
    }

    // ---------- ตัวช่วย assert ----------

    private interface ThrowingAction {
        void run();
    }

    private static void assertEquals(
            String testName,
            Object expected,
            Object actual) {

        boolean ok = (expected == null)
                ? (actual == null)
                : expected.equals(actual);

        report(
            testName,
            ok,
            "expected=<" + expected + "> actual=<" + actual + ">"
        );
    }

    private static void assertTrue(
            String testName,
            boolean condition) {

        report(
            testName,
            condition,
            "expected true, got false"
        );
    }

    private static void assertFalse(
            String testName,
            boolean condition) {

        report(
            testName,
            !condition,
            "expected false, got true"
        );
    }

    private static void assertThrows(
            String testName,
            Class<? extends Throwable> expectedType,
            ThrowingAction action) {

        try {
            action.run();

            report(
                testName,
                false,
                "no exception was thrown (expected "
                + expectedType.getSimpleName() + ")"
            );

        } catch (Throwable t) {

            boolean ok = expectedType.isInstance(t);

            report(
                testName,
                ok,
                "expected=" + expectedType.getSimpleName()
                + " actual=" + t.getClass().getSimpleName()
            );
        }
    }

    private static void report(
            String testName,
            boolean passed,
            String detail) {

        if (passed) {
            passCount++;
            System.out.println("[PASS] " + testName);

        } else {
            failCount++;
            System.out.println(
                "[FAIL] " + testName + "  (" + detail + ")
            );
        }
    }
}
