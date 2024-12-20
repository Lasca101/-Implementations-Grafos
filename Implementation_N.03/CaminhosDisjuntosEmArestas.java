import java.io.*;
import java.util.*;

public class CaminhosDisjuntosEmArestas {
    private static final int INF = Integer.MAX_VALUE;

    private int[][] redeResidual;
    private List<List<Integer>> caminhosDisjuntos;
    private int numVertices;

    public CaminhosDisjuntosEmArestas(int numVertices) {
        this.numVertices = numVertices;
        this.redeResidual = new int[numVertices][numVertices];
        this.caminhosDisjuntos = new ArrayList<>();
    }

    public void lerGrafo(BufferedReader br) throws IOException {
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(" ");
            int u = Integer.parseInt(partes[0]) - 1;
            int v = Integer.parseInt(partes[1]) - 1;
            redeResidual[u][v] = 1; // capacidade binária
        }
        br.close();
    }

    public List<List<Integer>> encontrarCaminhosDisjuntos(int origem, int destino) {
        int[] predecessor = new int[numVertices];
        Arrays.fill(predecessor, -1);

        while (buscaLargura(origem, destino, predecessor)) {
            List<Integer> caminhoAtual = new ArrayList<>();
            int fluxo = INF;

            // reconstrói o caminho da origem ao destino usando predecessores
            for (int v = destino; v != origem; v = predecessor[v]) {
                int u = predecessor[v];
                fluxo = Math.min(fluxo, redeResidual[u][v]);
                caminhoAtual.add(0, v + 1);
            }
            caminhoAtual.add(0, origem + 1);

            // armazena o caminho e ajusta o grafo residual
            if (fluxo > 0) {
                caminhosDisjuntos.add(caminhoAtual);

                // atualiza o grafo residual
                for (int v = destino; v != origem; v = predecessor[v]) {
                    int u = predecessor[v];
                    redeResidual[u][v] -= fluxo;
                    redeResidual[v][u] += fluxo;
                }
            }
        }
        return caminhosDisjuntos;
    }

    // encontrar o caminho aumentante e preencher o predecessor
    private boolean buscaLargura(int origem, int destino, int[] predecessor) {
        boolean[] visitado = new boolean[numVertices];
        Queue<Integer> fila = new LinkedList<>();
        fila.add(origem);
        visitado[origem] = true;

        while (!fila.isEmpty()) {
            int u = fila.poll();
            for (int v = 0; v < numVertices; v++) {
                if (!visitado[v] && redeResidual[u][v] > 0) {
                    fila.add(v);
                    predecessor[v] = u;
                    visitado[v] = true;
                    if (v == destino) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void exibirResultados(int origem, int destino) {
        System.out.println("Caminhos disjuntos em arestas entre " + (origem + 1) + " e " + (destino + 1) + ":");
        System.out.println("Total: " + caminhosDisjuntos.size());
        for (List<Integer> caminho : caminhosDisjuntos) {
            System.out.println(caminho);
        }
    }

    public static void main(String[] args) throws IOException {
        System.err.println("");
        String path = "bipartido/bipartido_500_500.txt";
    
        BufferedReader br = new BufferedReader(new FileReader(path));
        int numVertices = Integer.parseInt(br.readLine().split(" ")[0]);
        CaminhosDisjuntosEmArestas grafo = new CaminhosDisjuntosEmArestas(numVertices); // número de vértices
        grafo.lerGrafo(br); // caminho do arquivo contendo o grafo
    
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o vértice de origem: ");
        int origem = Integer.parseInt(sc.nextLine()) - 1;
        System.out.println("Digite o vértice de destino: ");
        int destino = Integer.parseInt(sc.nextLine()) - 1;
        sc.close();
    
        // Definindo o número de execuções para obter a média do tempo
        int numExecucoes = 5;
        double tempoTotal = 0;
    
        // Medindo o consumo de memória e o tempo de uma única execução para o consumo de memória
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Sugere a limpeza da memória antes da medição
        long memoriaAntes = runtime.totalMemory() - runtime.freeMemory();
    
        long inicio = System.nanoTime();
        grafo.encontrarCaminhosDisjuntos(origem, destino);
        long fim = System.nanoTime();
    
        long memoriaDepois = runtime.totalMemory() - runtime.freeMemory();
        long consumoMemoria = memoriaDepois - memoriaAntes; // em bytes
        double tempoExecucaoPrimeiraExecucao = (fim - inicio) / 1e6; // Tempo da primeira execução em milissegundos
        tempoTotal += tempoExecucaoPrimeiraExecucao;
    
        // Executando múltiplas vezes (exceto a primeira) e acumulando o tempo para a média
        for (int i = 1; i < numExecucoes; i++) {
            inicio = System.nanoTime();
            grafo.encontrarCaminhosDisjuntos(origem, destino);
            fim = System.nanoTime();
            tempoTotal += (fim - inicio) / 1e6; // Convertendo para milissegundos
        }
    
        // Calculando o tempo médio de execução
        double tempoMedioExecucao = tempoTotal / numExecucoes;
    
        // Exibindo resultados
        grafo.exibirResultados(origem, destino);
    
        System.out.println("\nTempo médio de execução: " + tempoMedioExecucao + " ms");
        System.out.println("Consumo de memória: " + consumoMemoria / 1024 + " KB\n");
    
        // Escrevendo os dados no arquivo CSV
        try (RandomAccessFile rf = new RandomAccessFile("resultadosBipartido.csv", "rw")) {
            rf.seek(rf.length()); // Move para o final do arquivo para adicionar nova linha
            rf.writeBytes(numVertices + "," + tempoMedioExecucao + "," + (consumoMemoria / 1024) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}