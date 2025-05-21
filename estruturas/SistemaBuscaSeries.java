package estruturas;

import entidades.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia a busca por séries usando os seus índices
public class SistemaBuscaSeries {

    private HashExtensivel<ParNomeID> indiceNomeId;    // índice nome->id
    private ListaInvertida<ElementoLista> listaInvertida; // índice invertido termo -> lista de ElementoLista

    public SistemaBuscaSeries(HashExtensivel<ParNomeID> indiceNomeId,
                             ListaInvertida<ElementoLista> listaInvertida) {
        this.indiceNomeId = indiceNomeId;
        this.listaInvertida = listaInvertida;
    }

    // Cadastrar série no índice nome->id e inserir termos na lista invertida
    public void cadastrarSerie(String nomeSerie) throws IOException {
        // Gera um novo ID - pode ser a quantidade atual + 1, ou outra estratégia sua
        int novoId = (int)(indiceNomeId.tamanho() + 1);

        ParNomeID parNomeID = new ParNomeID(nomeSerie, novoId);
        indiceNomeId.inserir(parNomeID);

        // Quebra o nome em termos (minúsculos)
        String[] termos = nomeSerie.toLowerCase().split("\\s+");

        // Para cada termo insere na lista invertida o par (id da série, frequência = 1)
        for (String termo : termos) {
            List<ElementoLista> listaAtual = listaInvertida.buscar(termo);
            if (listaAtual == null) {
                listaAtual = new ArrayList<>();
            }
            boolean achou = false;
            for (ElementoLista el : listaAtual) {
                if (el.getId() == novoId) {
                    el.setFrequencia(el.getFrequencia() + 1); // Incrementa frequência se quiser
                    achou = true;
                    break;
                }
            }
            if (!achou) {
                listaAtual.add(new ElementoLista(novoId, 1.0f));
            }
            listaInvertida.inserir(termo, listaAtual);
        }
    }

    // Buscar séries por termo: retorna lista de IDs que têm o termo
    public List<Integer> buscarSeriesPorTermo(String termo) throws IOException {
        List<ElementoLista> lista = listaInvertida.buscar(termo.toLowerCase());
        List<Integer> ids = new ArrayList<>();
        if (lista != null) {
            for (ElementoLista el : lista) {
                ids.add(el.getId());
            }
        }
        return ids;
    }
}
