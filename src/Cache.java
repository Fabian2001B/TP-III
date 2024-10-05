import java.util.ArrayList;
import java.util.List;

public class Cache {
    long tamanhoMemoriaPrincipal;
    int numPalavrasPorBloco;
    int numtotalLinhasDaCache;
    int numViasDeConjunto;
    private List<Integer> enderecoDeMemoria;
    private String nomeArquivo;

    public Cache() {
        enderecoDeMemoria = new ArrayList<>();
    }

    public void setTamanhoMemoriaPrincipal(long tamanhoBytes_M_Principal) {
        if (tamanhoBytes_M_Principal >= 1 && tamanhoBytes_M_Principal <= 4294967296L) {
            this.tamanhoMemoriaPrincipal = tamanhoBytes_M_Principal;
        } else {
            throw new IllegalArgumentException("Tamanho da memória principal deve estar entre 1 e 4294967296.");
        }
    }

    public long getTamanhoMemoriaPrincipal() {
        return tamanhoMemoriaPrincipal;
    }

    public void setNumPalavrasPorBloco(int numPalavras) {
        if (numPalavras >= 1 && numPalavras <= 65536) {
            this.numPalavrasPorBloco = numPalavras;
        } else {
            throw new IllegalArgumentException("Número de palavras por bloco deve estar entre 1 e 65536.");
        }
    }

    public int getNumPalavrasPorBloco() {
        return numPalavrasPorBloco;
    }

    public void setNumtotalLinhasDaCache(int numLinhas) {
        if (numLinhas >= 1 && numLinhas <= 65536) {
            this.numtotalLinhasDaCache = numLinhas;
        } else {
            throw new IllegalArgumentException("Número total de linhas da cache deve estar entre 1 e 65536.");
        }
    }

    public int getNumtotalLinhasDaCache() {
        return numtotalLinhasDaCache;
    }

    public void setNumViasDeConjunto(int numVias) {
        if (numVias >= 1 && numVias <= 1024) {
            this.numViasDeConjunto = numVias;
        } else {
            throw new IllegalArgumentException("Número de vias de conjunto deve estar entre 1 e 1024.");
        }
    }

    public int getNumViasDeConjunto() {
        return numViasDeConjunto;
    }

    public void adicionarEnderecos(String enderecos) {
        String[] partes = enderecos.split(" ");
        for (String parte : partes) {
            int endereco = Integer.parseInt(parte);
            if (endereco < 0 || endereco >= tamanhoMemoriaPrincipal) {
                throw new IllegalArgumentException("Endereço " + endereco + " está fora do intervalo permitido.");
            }
            enderecoDeMemoria.add(endereco);
        }
    }

    public List<Integer> getEnderecoDeMemoria() {
        return enderecoDeMemoria;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    @Override
public String toString() {
    return "Cache{" +
            "tamanhoMemoriaPrincipal=" + tamanhoMemoriaPrincipal +
            ", numPalavrasPorBloco=" + numPalavrasPorBloco +
            ", numtotalLinhasDaCache=" + numtotalLinhasDaCache +
            ", numViasDeConjunto=" + numViasDeConjunto +
            ", enderecoDeMemoria=" + enderecoDeMemoria +
            '}';
}
}
