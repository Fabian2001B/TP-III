import java.util.*;
import java.io.*;

public class SimulaAcessoCache {
    private Cache cache;
    private List<LinkedHashMap<Integer, Integer>> conjuntos; // Usando LinkedHashMap para armazenar tags
    private final int MAX_VIAS; // Número máximo de vias (conjuntos)
    private int cacheHits = 0; // Contador para hits
    private int cacheMisses = 0; // Contador para misses

    public SimulaAcessoCache(Cache cache) {
        this.cache = cache;
        MAX_VIAS = cache.getNumViasDeConjunto();
        inicializarEstruturas();
    }

    public int calcularBitsDadoNoBloco() {
        int numPalavrasPorBloco = cache.getNumPalavrasPorBloco();
        int tamanhoBlocoEmBytes = numPalavrasPorBloco * 4; // Cada palavra tem 4 bytes
        return (int) (Math.log(tamanhoBlocoEmBytes) / Math.log(2)); // log2(tamanhoBlocoEmBytes)
    }

    public int calcularBitsConjunto() {
        int numLinhasCache = cache.getNumtotalLinhasDaCache();
        int numVias = cache.getNumViasDeConjunto();

        if (numLinhasCache <= 0 || numVias <= 0) {
            throw new IllegalArgumentException("Número de linhas e vias deve ser maior que zero.");
        }

        int totalConjuntos = numLinhasCache / numVias; // Total de conjuntos
        return (int) (Math.log(totalConjuntos) / Math.log(2)); // log2(totalConjuntos)
    }

    public int calcularBitsTag() {
        long tamanhoMemoriaPrincipal = cache.getTamanhoMemoriaPrincipal();
        if (tamanhoMemoriaPrincipal <= 0) {
            throw new IllegalArgumentException("Tamanho da memória principal deve ser maior que zero.");
        }
        int bitsEndereco = (int) (Math.log(tamanhoMemoriaPrincipal) / Math.log(2)); // log2(tamanhoMemoriaPrincipal)
        int bitsPorBloco = calcularBitsDadoNoBloco();
        int bitsConjunto = calcularBitsConjunto();
        int bitsTag = bitsEndereco - bitsPorBloco - bitsConjunto; // bits da tag

        if (bitsTag < 0) {
            throw new ArithmeticException("O número de bits da tag resultou em um valor negativo.");
        }
        return bitsTag;
    }

    private void inicializarEstruturas() {
        int numLinhas = cache.getNumtotalLinhasDaCache();
        conjuntos = new ArrayList<>(numLinhas);
        for (int i = 0; i < numLinhas; i++) {
            conjuntos.add(new LinkedHashMap<>()); // Usando LinkedHashMap para manter a ordem de inserção
        }
    }

    public void simularAcessos(List<Long> enderecos) {
        // Verifica se a lista tem pelo menos 5 linhas
        if (enderecos.size() < 5) {
            System.out.println("Não há endereços suficientes para processar.");
            return;
        }

        // Começa a partir do índice 4, que é a linha 5 (considerando índice 0)
        for (int i = 4; i < enderecos.size(); i++) {
            long endereco = enderecos.get(i);
            int conjuntoIndex = calcularConjunto(endereco);
            int tag = calcularTag(endereco);

            // Verifica se o bloco está presente no conjunto
            LinkedHashMap<Integer, Integer> conjunto = conjuntos.get(conjuntoIndex);
            if (conjunto.containsKey(tag)) {
                cacheHits++;
            } else {
                cacheMisses++;
                // Se o conjunto estiver cheio, remova o bloco menos recentemente usado (LRU)
                if (conjunto.size() >= MAX_VIAS) {
                    Iterator<Integer> iterator = conjunto.keySet().iterator();
                    if (iterator.hasNext()) {
                        iterator.next();
                        iterator.remove();
                    }
                }
                // Adiciona a nova tag ao conjunto
                conjunto.put(tag, 1);
            }
        }
    }

    private int calcularConjunto(long endereco) {
        int bitsConjunto = calcularBitsConjunto();
        int numConjuntos = (int) Math.pow(2, bitsConjunto);
        return (int) ((endereco >> calcularBitsDadoNoBloco()) % numConjuntos);
    }

    private int calcularTag(long endereco) {
        int bitsConjunto = calcularBitsConjunto();
        int bitsDadoNoBloco = calcularBitsDadoNoBloco();
        return (int) (endereco >> (bitsConjunto + bitsDadoNoBloco));
    }

    public void exibirResultados(String arquivoSaida) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida))) {
            writer.println(calcularBitsDadoNoBloco());
            writer.println(calcularBitsConjunto());
            writer.println(calcularBitsTag());
            writer.println( cacheMisses);
            writer.println( cacheHits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibirResultadosNoTerminal() {
        System.out.println("========================");
        System.out.println("Bits Dado no Bloco: " + calcularBitsDadoNoBloco());
        System.out.println("Bits Conjunto: " + calcularBitsConjunto());
        System.out.println("Bits Tag: " + calcularBitsTag());
        System.out.println("Misses: " + cacheMisses);
        System.out.println("Hits: " + cacheHits);
        System.out.println("========================");
    }
}
