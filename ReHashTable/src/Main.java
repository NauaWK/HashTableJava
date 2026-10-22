
import org.w3c.dom.Node;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        long seed = 1090;
        Random r = new Random(seed);

        for (int i = 0; i < 100; i++) {
            int randomInt = r.nextInt(100_001);
            HashTable.insert(randomInt);
        }

        for(int i = 0; i < HashTable.getCAP(); i++){
            HashTable.Node node = HashTable.getTable()[i];
            if(node != null) System.out.println(i + " - " + node.getVal());
            else System.out.println(i + " - Posicao vazia");
        }

        System.out.println("Total de colisoes: " + HashTable.getTotalColisions());
        System.out.print("Os elementos que mais colidiram:");
        for(HashTable.Node n : HashTable.getMostColisionNodes()){
            if(n != null ) System.out.print(" | Valor " + n.getVal() + " - Qtd colisoes " + n.getNumberOfColisions() + " | ");
            else System.out.print(" | Valor " + null + " - Qtd colisoes " + null + " | ");
        }
        System.out.println();
    }
}

class HashTable {

    private static final Node[] table = new Node[100];
    private static final int CAP = 100;
    private static final int MAX_CAP = (int) (CAP * 0.9);
    private static int LEN = 0;
    private static int TOTAL_COLISIONS = 0;
    private static final Node[] mostColisionNodes = new Node[3];

    private static int hash(int key) {
        return key % CAP;
    }

    private static int reHash(int key){
        return hash((key + 1));
    }

    private static int findAvaliableReHash(int key, Node n){
        int attempts = 0; //para controlar quando todas as posicoes ja estiverem ocupadas na tabela, impedindo loop infinito
        while(attempts < CAP && table[key] != null){
            System.out.println("Colisao do valor " + n.getVal() + " na posicao " + key);
            TOTAL_COLISIONS++;
            attempts++;
            n.setNumberOfColisions(n.getNumberOfColisions() + 1); //cada vez que o rehash é aplicado (nova colisao) o numero de colisoes daquele Node aumenta
            key = reHash(key);
        }

        for(int i = 0; i < 3; i++){
            if(mostColisionNodes[i] != null){       
                if(mostColisionNodes[i].getNumberOfColisions() < n.getNumberOfColisions()){ 
                    mostColisionNodes[i] = n; 
                    break; //saindo do loop garantindo que um unico valor nao ocupe as outras posicoes repetidamente (se tiver um alto nº de colisoes por exemplo)
                }
            }
            else{
                mostColisionNodes[i] = n;
                break; //saindo do loop tambem para evitar que o mesmo valor ocupe todas as posicoes
            }
        }

        return table[key] == null ? key : -1;
    }

    public static void insert(int key) {

        if(LEN > MAX_CAP){
            System.out.println("Taxa de ocupação chegou ao limite (90%)!");
            return;
        }

        int hashIndex = hash(key);
        Node newNode = new Node(key);

        if (table[hashIndex] == null) {
            table[hashIndex] = newNode;
            LEN++;
            System.out.println("Elemento " + newNode.getVal() + " inserido na posicao " + hashIndex);
            return;
        }

        int reHashIndex = findAvaliableReHash(hashIndex, newNode);
        if(reHashIndex != -1){
            table[reHashIndex] = newNode;
            LEN++;
            System.out.println("Elemento " + newNode.getVal() + " inserido na NOVA posicao: " + reHashIndex);
        }
    }

    public static Node[] getTable() {
        return table;
    }

    public static int getLEN() {
        return LEN;
    }

    public static int getCAP() {
        return CAP;
    }
    
    public static int getTotalColisions(){
        return TOTAL_COLISIONS;
    }

    public static Node[] getMostColisionNodes(){
        return mostColisionNodes;
    }

    static class Node {

        private int val;
        private int numberOfColisions;
        private Node next;

        Node(int val) {
            this.val = val;
            this.numberOfColisions = 0;
            this.next = null;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setNumberOfColisions(int numberOfColisions) {
            this.numberOfColisions = numberOfColisions;
        }

        public int getNumberOfColisions(){
            return this.numberOfColisions;
        }

        public int getVal() {
            return this.val;
        }

        public Node getNext() {
            return this.next;
        }
    }
}

