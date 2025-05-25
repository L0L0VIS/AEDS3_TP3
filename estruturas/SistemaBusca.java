package estruturas;

import entidades.*;

import java.util.ArrayList;
import java.util.List;

// Classe que gerencia a busca usando um índice invertido
public class SistemaBusca {

    private ListaInvertida listaInvertida; // índice invertido termo -> lista de ElementoLista

    public SistemaBusca(ListaInvertida listaInvertida) {
        this.listaInvertida = listaInvertida;
    }

    // Inserir termos na lista invertida pós cadastro
    public void cadastrar(String nome, int id) throws Exception {
        // Quebra o nome em termos (minúsculos)
        String[] termos = nome.toLowerCase().split("\s+");
        float frequencia = (float) (1.0 / (termos.length));

        //System.out.println("Cadastrando termos para nome: " + nome + " - frequencia base: " + frequencia);
        //System.out.print("Termos separados: ");
        for (String termo : termos) {
            System.out.print(termo + ", ");
        }
        System.out.println("");

        // Para cada termo insere na lista invertida o par ou o atualiza se existir
        for (String termo : termos) {
            ElementoLista[] listaAtual = listaInvertida.read(termo);
            // Se não existir, criar
            if (listaAtual == null) {
                listaInvertida.create(termo, new ElementoLista(id, frequencia));
            }
            else {
                boolean achou = false;
                for (ElementoLista el : listaAtual) {
                    if (el.getId() == id) {
                        // Incrementa frequência para termos repetidos
                        el.setFrequencia(el.getFrequencia() + frequencia);
                        achou = true;
                        // Atualiza na lista
                        listaInvertida.update(termo, el);
                        break;
                    }
                }
                if (!achou) {
                    listaInvertida.create(termo, new ElementoLista(id, frequencia));
                }
            }

        }
        // Incrementa o contador de entidades no início do dicionário
        listaInvertida.incrementaEntidades();
    }

    // Buscar por termo: retorna lista de IDs que têm o termo
    public List<Integer> buscarPorTermo(String termo) throws Exception {
        ElementoLista[] lista = listaInvertida.read(termo.toLowerCase());
        List<Integer> ids = new ArrayList<>();
        if (lista != null) {
            for (ElementoLista el : lista) {
                ids.add(el.getId());
            }
        }
        return ids;
    }


    // Busca e retorna lista de ElementoLista contendo id e frequência
    // Com aplicação do IDF para vários termos
    public ElementoLista[] listaIDF(String busca) throws Exception {
        ElementoLista[] resultado = new ElementoLista[0];
        String[] termos = busca.toLowerCase().split("\s+");

        // Para cada termo da busca
        for (String termo : termos) {
            ElementoLista[] lista_parcial = listaIDF_unica(termo);
            //System.out.println("Fim análise resultado parcial");

            // Se o resultado não for nulo, usar o processo de adição
            if (lista_parcial != null) {
                // Para cada elemento retornado da busca do termo
                for (ElementoLista el : lista_parcial) {
                    //System.out.println("Analisando elemento: " + el.getId() + " - " + el.getFrequencia());
                    // Percorrer lista de resultado para conferir se já existe
                    boolean achou = false;
                    for (int x = 0; x < resultado.length; x++) {
                        // Se já existe, soma a frequência
                        if (resultado[x].getId() == el.getId()) {
                            resultado[x].setFrequencia(resultado[x].getFrequencia() + el.getFrequencia());
                            achou = true;
                            break;
                        }
                    }
                    // Se não tiver encontrado, criar uma nova lista com o novo elemento
                    // para substituir o resultado atual
                    if (!achou) {
                        ElementoLista[] novoResultado = new ElementoLista[resultado.length + 1];
                        for (int x = 0; x < resultado.length; x++) {
                            novoResultado[x] = resultado[x];
                        }
                        novoResultado[resultado.length] = new ElementoLista(el.getId(), el.getFrequencia());
                        resultado = novoResultado;
                    }


                }
            }


        }

        return resultado;
    }

    // Busca e retorna lista de ElementoLista contendo id e frequência
    // Com aplicação do IDF
    public ElementoLista[] listaIDF_unica(String termo) throws Exception {
        ElementoLista[] lista = listaInvertida.read(termo.toLowerCase());
        int numeroEntidade = listaInvertida.numeroEntidades();
        float idf = (float) (numeroEntidade * 1.0 / lista.length);

        //System.out.println("Formando lista IDF para o termo: " + termo);
        //System.out.println("N entidades: " + numeroEntidade + ", idf: " + idf);
        if (lista != null) {
            for (ElementoLista el : lista) {
                
                //System.out.println("Analisando elemento: " + el.getId() + " - " + el.getFrequencia());
                float freq = el.getFrequencia();
                el.setFrequencia(freq * idf);
            }
        }
        return lista;
    }

}
