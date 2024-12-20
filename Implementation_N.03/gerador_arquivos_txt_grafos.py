import os
import random

def gerar_grafo_aleatorio(n, p, nome_arquivo):
    arestas = []
    for u in range(1, n + 1):
        for v in range(u + 1, n + 1):
            if random.random() < p:
                if random.choice([True, False]):
                    arestas.append((u, v))
                else:
                    arestas.append((v, u))
    m = len(arestas)
    with open(nome_arquivo, 'w') as f:
        f.write(f"{n} {m}\n")
        for u, v in arestas:
            f.write(f"{u} {v}\n")

def gerar_grafo_bipartido(n1, n2, nome_arquivo):
    arestas = []
    origem = 1 
    destino = n1 + n2  

    for u in range(2, n1 + 1):
        arestas.append((origem, u))

    for u in range(2, n1 + 1):
        num_arestas = random.randint(1, max(1, n2 // 2))
        vertices_v = random.sample(range(n1 + 1, n1 + n2), num_arestas)
        for v in vertices_v:
            arestas.append((u, v))

    for v in range(n1 + 1, n1 + n2):
        arestas.append((v, destino))

    n = n1 + n2
    m = len(arestas)
    with open(nome_arquivo, 'w') as f:
        f.write(f"{n} {m}\n")
        for u, v in arestas:
            f.write(f"{u} {v}\n")

def main():
    tamanhos = [10, 100, 200, 500, 1000, 2000]

    p_aleatorio = 0.3

    pasta_aleatorio = 'aleatorio'
    if not os.path.exists(pasta_aleatorio):
        os.makedirs(pasta_aleatorio)

    for n in tamanhos:
        nome_arquivo = os.path.join(pasta_aleatorio, f"aleatorio_{n}.txt")
        gerar_grafo_aleatorio(n, p_aleatorio, nome_arquivo)
        print(f"Grafo aleatório com {n} vértices gerado: {nome_arquivo}")

    pasta_bipartido = 'bipartido'
    if not os.path.exists(pasta_bipartido):
        os.makedirs(pasta_bipartido)

    for n in tamanhos:
        n1 = n // 2  
        n2 = n - n1
        nome_arquivo = os.path.join(pasta_bipartido, f"bipartido_{n1}_{n2}.txt")
        gerar_grafo_bipartido(n1, n2, nome_arquivo)
        print(f"Grafo bipartido com {n} vértices gerado: {nome_arquivo}")

if __name__ == "__main__":
    main()
