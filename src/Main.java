import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static final String PASTA_PATH = "C:\\Users\\fabia\\testes222";
    private static final String PASTA_SAIDA = "C:\\Users\\fabia\\testes222";
    private static final String EXTENSAO_TEXTO = ".txt";
    private static final String SUFIXO_RESULTADO = "-RESULTADO.txt";

    public static void main(String[] args) {
        LeitorDeArquivos leitor = new LeitorDeArquivos();
        List<Cache> caches = leitor.lerArquivosDaPasta(PASTA_PATH);

        for (Cache cache : caches) {
            String caminhoEnderecos = cache.getNomeArquivo();
            List<Long> enderecos = leitor.lerEnderecosDeArquivo(caminhoEnderecos);

            SimulaAcessoCache simulacache = new SimulaAcessoCache(cache);
            simulacache.simularAcessos(enderecos);
            simulacache.exibirResultadosNoTerminal();

            String nomeArquivoOriginal = new File(caminhoEnderecos).getName();
            String arquivoSaida = gerarNomeArquivoSaida(nomeArquivoOriginal);

            simulacache.exibirResultados(arquivoSaida);
        }
    }

    private static String gerarNomeArquivoSaida(String nomeArquivoOriginal) {

        String nomeBase = new File(nomeArquivoOriginal).getName();

        int posTxt = nomeBase.indexOf(EXTENSAO_TEXTO);
        String nomeExtraido;

        if (posTxt != -1) {
            nomeExtraido = nomeBase.substring(0, posTxt);
        } else {
            nomeExtraido = nomeBase;
        }

        String nomeFinal = nomeExtraido.split("-")[0] + "-" + nomeExtraido.split("-")[1];

        nomeFinal += SUFIXO_RESULTADO + EXTENSAO_TEXTO;

        System.out.println("Nome final: " + nomeFinal);

        // Criar o arquivo de saída
        String caminhoCompleto = PASTA_SAIDA + "\\" + nomeFinal;

        // Verificar se o diretório de saída existe
        File diretorioSaida = new File(PASTA_SAIDA);
        if (!diretorioSaida.exists()) {
            System.out.println("A pasta de saída não existe: " + PASTA_SAIDA);
            return null;
        }

        // Criar o arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompleto))) {

            System.out.println("Arquivo criado com sucesso: " + caminhoCompleto);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        return caminhoCompleto;
    }

}