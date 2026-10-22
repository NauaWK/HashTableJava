

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long seed = 1090;
        Random r = new Random(seed);

        for(int i = 0; i < 1000; i++){
            int randomInt = r.nextInt(100_001);
            HashTable.insert(randomInt);
        }

        for(int i = 0; i < HashTable.getCAP(); i++){
            HashTable.Node node = HashTable.getTable()[i];
            if(node != null) {
                if(node.getNext() == null) System.out.println(i + " - " +  node.getVal());
                else{
                    System.out.print("Lista encadeada detectada no indice " + i + " - ");
                    HashTable.Node actual = HashTable.getTable()[i];
                    while(actual != null){
                        System.out.print(" | " + actual.getVal() + " | ");
                        actual = actual.getNext();
                    }
                    System.out.println();
                }
            }
        }
        
        int TOTAL_LISTS = 0;
        int TOTAL_NODES = 0;
        int TOTAL_EMPTY = 0;
        int HIGHEST_LIST_LEN = 0;
        int HIGHEST_LIST_LEN_IDX = 0;
        HashTable.Node[] table = HashTable.getTable();

        for(HashTable.Node n : table){
            if(n == null) TOTAL_EMPTY++;
            else if(n.getNext() != null) TOTAL_LISTS++;
            else TOTAL_NODES++;
        }
        System.out.println("Total de listas encadeadas: " + TOTAL_LISTS);
        System.out.println("Total de elementos (nao listas): " + TOTAL_NODES);
        System.out.println("Total de buckets (posicoes) vazios: " + TOTAL_EMPTY);

        for(int i = 0; i < HashTable.getCAP(); i++){
            HashTable.Node n = table[i];
            if(n != null){
                int len = 0;
                HashTable.Node actual = n;
                while(actual != null){
                    len++;
                    actual = actual.getNext();
                }
                if(len > HIGHEST_LIST_LEN){
                    HIGHEST_LIST_LEN = len;
                    HIGHEST_LIST_LEN_IDX = i;
                }
            }
        }
        System.out.println("Indice da maior lista encadeada: " + HIGHEST_LIST_LEN_IDX + " | Comprimento da maior lista encadeada: " + HIGHEST_LIST_LEN);
        System.out.print("Ultimos 3 elementos inseridos em listas encadeadas: ");
        for(HashTable.Node n : HashTable.getLAST_THREE_INSERTS()){
            if(n != null) System.out.print(" | " + n.getVal()  + " | ");
        }
        System.out.println();
    }
}

class HashTable{

    private static final Node[] table = new Node[100];
    private static final int CAP = 100;
    private static int LEN = 0;
    private static Node[] LAST_THREE_INSERTS = new Node[3];

    public static int hash(int key){
        return key % CAP;
    }

    public static void insert(int key){

        int hashIndex = hash(key);
        Node newNode = new Node(key);

        if (table[hashIndex] == null) {
            table[hashIndex] = newNode;
            LEN++;
            return;
        }

        System.out.println("Colisao!");
        if(CAP - LEN <= 3 && CAP - LEN > 0){
            LAST_THREE_INSERTS[CAP - LEN - 1] = newNode;
        }

        Node actual = table[hashIndex];

        if (newNode.getVal() < actual.getVal()) {
            newNode.setNext(actual);
            table[hashIndex] = newNode;
            return;
        }

        while (actual.getNext() != null && actual.getNext().getVal() <= newNode.getVal()) {
            actual = actual.getNext();
        }

        newNode.setNext(actual.getNext());
        actual.setNext(newNode);
    }

    public static Node[] getTable(){
        return table;
    }

    public static int getLEN() {
        return LEN;
    }

    public static int getCAP(){
        return CAP;
    }

    public static Node[] getLAST_THREE_INSERTS() {
        return LAST_THREE_INSERTS;
    }

    static class Node{

        private int val;
        private Node next;

        Node(int val){
            this.val = val;
            this.next = null;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public int getVal() {
            return this.val;
        }

        public Node getNext(){
            return this.next;
        }

    }
}

