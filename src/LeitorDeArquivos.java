import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List;

public class LeitorDeArquivos {
    public List<Cache> lerArquivosDaPasta(String caminhoPasta) {
        List<Cache> caches = new ArrayList<>();
        File pastaDir = new File(caminhoPasta);
    
        if (!pastaDir.isDirectory()) {
            throw new IllegalArgumentException("O caminho especificado não é um diretório: " + caminhoPasta);
        }
    
        File[] arquivos = pastaDir.listFiles((dir, name) -> name.endsWith(".txt"));
    
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo encontrado na pasta: " + caminhoPasta);
            return caches;
        }
    
        for (File arquivo : arquivos) {
            try {
                String[] dados = lerLinhasArquivo(arquivo.getAbsolutePath());
                Cache cache = ObjetosCache.criarCache(dados);
                
                // Defina o nome do arquivo de endereços
                cache.setNomeArquivo(arquivo.getAbsolutePath()); // Armazena o caminho do arquivo
                
                caches.add(cache);
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo " + arquivo.getAbsolutePath() + ": " + e.getMessage());
            }
        }
        return caches;
    }
    

    private String[] lerLinhasArquivo(String caminhoArquivo) throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            return leitor.lines().toArray(String[]::new);
        }
    }

    public List<Long> lerEnderecosDeArquivo(String caminhoArquivo) {
        List<Long> enderecos = new ArrayList<>();
        File arquivo = new File(caminhoArquivo);
    
        // Verifica se o arquivo existe
        if (!arquivo.exists()) {
            System.out.println("O arquivo não existe: " + caminhoArquivo);
            return enderecos; // Retorna uma lista vazia
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Divide a linha em partes e adiciona cada endereço à lista
                String[] partes = linha.split(" ");
                for (String parte : partes) {
                    enderecos.add(Long.parseLong(parte.trim())); // Adiciona cada endereço convertido
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler os endereços do arquivo " + caminhoArquivo + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            //System.out.println("Formato inválido em: " + e.getMessage());
        }
        return enderecos;
    }
}
