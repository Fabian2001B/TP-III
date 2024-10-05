import java.util.Arrays;

public class ObjetosCache {
    public static Cache criarCache(String[] dados) {
        Cache cache = new Cache();

        try {
            long tamanhoMemoriaPrincipal = Long.parseLong(dados[0]);
            cache.setTamanhoMemoriaPrincipal(tamanhoMemoriaPrincipal);

            int numPalavrasPorBloco = Integer.parseInt(dados[1]);
            cache.setNumPalavrasPorBloco(numPalavrasPorBloco);

            int numLinhasCache = Integer.parseInt(dados[2]);
            cache.setNumtotalLinhasDaCache(numLinhasCache);

            int numViasDeConjunto = Integer.parseInt(dados[3]);
            cache.setNumViasDeConjunto(numViasDeConjunto);

            cache.adicionarEnderecos(dados[4]);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter número: " + e.getMessage());
        }

        return cache;
    }
}

