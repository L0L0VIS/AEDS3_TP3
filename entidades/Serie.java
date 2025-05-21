package entidades;

import java.io.*;

public class Serie implements Registro, Comparable<Serie>, Cloneable {

    private int id;
    private String nome;
    private int ano;
    private String sinopse;
    private String streaming;

    public Serie() {
        this(-1, "", -1, "", "");
    }

    public Serie(int id, String nome, int ano, String sinopse, String streaming) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.sinopse = sinopse;
        this.streaming = streaming;
    }

    public Serie(String nome, int ano, String sinopse, String streaming) {
        this(-1, nome, ano, sinopse, streaming);
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getAno() { return ano; }
    public String getSinopse() { return sinopse; }
    public String getStreaming() { return streaming; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setAno(int ano) { this.ano = ano; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public void setStreaming(String streaming) { this.streaming = streaming; }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeInt(ano);
        dos.writeUTF(sinopse);
        dos.writeUTF(streaming);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.ano = dis.readInt();
        this.sinopse = dis.readUTF();
        this.streaming = dis.readUTF();
    }

    @Override
    public String toString() {
        return "(" + id + ";" + nome + ";" + ano + ";" + sinopse + ";" + streaming + ")";
    }

    @Override
    public int compareTo(Serie outra) {
        return Integer.compare(this.id, outra.id);
    }

    @Override
    public Serie clone() {
        return new Serie(this.id, this.nome, this.ano, this.sinopse, this.streaming);
    }
}
