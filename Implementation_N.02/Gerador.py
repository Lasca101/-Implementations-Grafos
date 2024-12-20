import networkx as nx

# Metodo gerador de grafos conexo utilizando Erdős-Rényi gerando arquivos no formato lista de arestas
def Gerador(n, p):
    while True:
        grafo = nx.erdos_renyi_graph(n, p)
        if nx.is_connected(grafo):  # Verifica se o grafo é conexo
            return grafo

def salvarArquivo(grafo, nome_arquivo):
    with open(nome_arquivo, 'w') as f:
        # Escreve o número de vértices na primeira linha
        f.write(f"{grafo.number_of_nodes()}\n")
        
        # Escreve a lista de arestas, com vértices começando em 1
        for u, v in grafo.edges():
            f.write(f"{u + 1} {v + 1}\n")  

# N = num de vertices / P = probabilidade Erdős-Rényi
n = 100
p = 0.15
grafo_conexo = Gerador(n, p) 

# Salvar o grafo em arquivo
salvarArquivo(grafo_conexo, "grafo100.txt")

