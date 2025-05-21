package entidades;

import estruturas.RegistroHashExtensivel;

import java.io.*;

public class ParNomeID implements RegistroHashExtensivel<ParNomeID> {
    
    private String nome; // chave
    private int id;     // valor
    private final short TAMANHO = 44;  // tamanho em bytes

    public ParNomeID() {
        this.nome = "serie generica";
        this.id = -1;
    }

    public ParNomeID(String nome, int id) throws Exception {
        this.nome = nome;
        this.id = id;
    }

    public String getnome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Math.abs(this.nome.hashCode());
    }

    public short size() {
        return this.TAMANHO;
    }

    public String toString() {
        return "(" + this.nome + ";" + this.id + ")";
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeUTF(this.nome);
        dos.writeInt(this.id);

        byte[] bs = baos.toByteArray();
        byte[] bs2 = new byte[this.TAMANHO];

        for (int i = 0; i < this.TAMANHO; i++) {
            bs2[i] = ' ';
        }

        for (int i = 0; i < bs.length && i < this.TAMANHO; i++) {
            bs2[i] = bs[i];
        }

        return bs2;
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.nome = dis.readUTF();
        this.id = dis.readInt();
    }

    public static int hash(String nome) throws IllegalArgumentException {
        return Math.abs(nome.hashCode());
    }

    @Override
    public ParNomeID clone() {
        try {
            return new ParNomeID(this.nome, this.id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
