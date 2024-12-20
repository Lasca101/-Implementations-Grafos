import java.io.RandomAccessFile;
import java.util.ArrayList;

class Lista {
    private Lista proximo;
    private int vertice;

    public Lista(int vertice) {
        this.proximo = null;
        this.vertice = vertice;
    }

    public int getVertice() {
        return this.vertice;
    }

    public void setVertice(int vertice) {
        this.vertice = vertice;
    }

    public Lista getProximo() {
        return this.proximo;
    }

    public void setProximo(Lista proximo) {
        this.proximo = proximo;
    }
}

class Main {
    public static void main(String[] args) {
        String nomeArq = null;
        System.out.print("\nDigite o nome do arquivo com sua extensão (.txt): ");
        nomeArq = System.console().readLine();
        System.out.print("Digite o número do vertice: ");
        int vertice = Integer.parseInt(System.console().readLine());
        Lista[] lista = null;

        // Leitura do arquivo
        try {
            RandomAccessFile rf = new RandomAccessFile(nomeArq, "r");
            int[] primeiraLinha = processLine(rf);

            lista = new Lista[primeiraLinha[0]];
            for (int i = 0; i < primeiraLinha[0]; i++) {
                lista[i] = new Lista(i+1);
            }

            for (int i = 0; i < primeiraLinha[1]; i++) {
                int[] valores = processLine(rf);

                Lista aux = lista[valores[0]-1];
                while (aux.getProximo() != null) {
                    aux = aux.getProximo();
                }
                aux.setProximo(new Lista(valores[1]));
            }

            rf.close();
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo");
        }

        // Get sucessores e grau de saida
        ArrayList<Lista> sucessores = new ArrayList<Lista>();
        int grauSaida = 0;
        Lista aux1 = lista[vertice-1];
        while (aux1.getProximo() != null) {
            sucessores.add(aux1.getProximo());
            grauSaida++;
            aux1 = aux1.getProximo();
        }

        // Get predecessores e grau de entrada
        ArrayList<Lista> predecessores = new ArrayList<Lista>();
        int grauEntrada = 0;
        for (int i = 0; i < lista.length; i++) {
            Lista aux2 = lista[i];
            Lista raiz = lista[i];
            while (aux2.getProximo() != null) {
                if (aux2.getProximo().getVertice() == vertice) {
                    predecessores.add(raiz);
                    grauEntrada++;
                    break;
                }
                aux2 = aux2.getProximo();
            }
        }

        // Print variaveis
        System.out.println("\nGrau de saida: " + grauSaida);
        System.out.println("Grau de entrada: " + grauEntrada);
        System.out.print("Conjunto de sucessores: ");
        for (int i = 0; i < sucessores.size(); i++) {
            System.out.print(sucessores.get(i).getVertice() + " ");
        }
        System.out.print("\nConjunto de predecessores: ");
        for (int i = 0; i < predecessores.size(); i++) {
            System.out.print(predecessores.get(i).getVertice() + " ");
        }
        System.out.println("\n");
    }

    public static int[] processLine(RandomAccessFile rf) {
        String aux = null;
        int num1 = -1;
        int num2 = -1;
        int cont = 0;
        try {
            String linha = rf.readLine();
            linha = linha + " ";
            while(linha.charAt(cont) == ' '){
                cont++;
            }
            for (int i = cont; i < linha.length(); i++) {
                if(linha.charAt(i) != ' ') {
                    aux = linha.substring(cont, i+1);
                } else {
                    if (num1 == -1) {
                        num1 = Integer.parseInt(aux);
                        aux = null;
                        cont = i+1;
                        while(linha.charAt(cont) == ' '){
                            cont++;
                        }
                        i = cont-1;
                    } else {
                        num2 = Integer.parseInt(aux);
                        aux = null;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler a linha: " + e);
        }
        return new int[]{num1, num2};
    }
}

