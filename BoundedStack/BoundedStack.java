import java.util.*;

/**
 * BoundedStack
 */
public class BoundedStack {

    //
    // AF:
    //   pokemon แทน Stack ที่เก็บข้อมูลชนิด String
    //   โดยสมาชิกตัวสุดท้ายของ pokemon คือ Top ของ Stack
    //
    // RI:
    //   pokemon != null
    //   pokemon.size() <= capacity
    //   pokemon ต้องไม่มีสมาชิกที่เป็น null
    //

    // Safety from rep exposure:
    //   - pokemon เป็น private final จึงไม่สามารถเข้าถึงจากภายนอกได้
    //   - ไม่มีเมธอดที่คืน reference ของ pokemon ให้ผู้ใช้
    //   - การแก้ไขข้อมูลทำได้ผ่าน push() และ pop() เท่านั้น

    private final List<String> pokemon;
    private final int capacity;

    /**
     * @param capacity รับค่าข้อมูลสูงสุดใน Stack
     * @throws IllegalArgumentException ถ้า capacity เป็น 0 หรือเป็นค่าติดลบ
     */
    public BoundedStack(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Capacity must be greater than 0"
            );
        }

        this.capacity = capacity;
        this.pokemon = new ArrayList<>();

        checkRep();
    }

    /**
     * ตรวจสอบว่า Stack เต็มหรือไม่
     *
     * @return true ถ้า Stack เต็ม, false ถ้ายังไม่เต็ม
     */
    public boolean isFull() {
        return pokemon.size() == capacity;
    }

    /**
     * ตรวจสอบว่า Stack ว่างหรือไม่
     *
     * @return true ถ้า Stack ว่าง, false ถ้าไม่ว่าง
     */
    public boolean isEmpty() {
        return pokemon.isEmpty();
    }

    /**
     * คืนค่าจำนวนสมาชิกใน Stack
     *
     * @return จำนวน Pokémon ปัจจุบัน
     */
    public int size() {
        return pokemon.size();
    }

    /**
     * ตรวจสอบ Representation Invariant
     */
    private void checkRep() {
        assert pokemon != null :
            "Pokemon list must not be null";

        assert pokemon.size() <= capacity :
            "Stack size exceeds capacity";

        assert !pokemon.contains(null) :
            "Stack contains null Pokemon";
    }

    /**
     * เพิ่มชื่อ Pokémon เข้า Stack
     *
     * @param name_pokemon ชื่อ Pokémon ที่ต้องการเพิ่ม
     * @throws IllegalStateException ถ้า Stack เต็ม
     * @throws IllegalArgumentException ถ้าข้อมูลเป็น null
     */
    public void push(String name_pokemon) {

        

        pokemon.add(name_pokemon);

        checkRep();
    }

    /**
     * นำ Pokémon บนสุดออกจาก Stack
     *
     * @return ชื่อ Pokémon บนสุดของ Stack
     * @throws IllegalStateException ถ้า Stack ว่าง
     */
    public String pop() {

       

        String result = pokemon.remove(
            pokemon.size() - 1
        );

        checkRep();

        return result;
    }

    /**
     * คืนค่าชื่อ Pokémon บนสุดของ Stack
     * โดยไม่ลบออก
     *
     * @return ชื่อ Pokémon บนสุดของ Stack
     * @throws IllegalStateException ถ้า Stack ว่าง
     */
    public String peek() {

        checkRep();

        if (pokemon.isEmpty()) {
            throw new IllegalStateException(
                "Stack is Empty"
            );
        }

        return pokemon.get(
            pokemon.size() - 1
        );
    }
}
