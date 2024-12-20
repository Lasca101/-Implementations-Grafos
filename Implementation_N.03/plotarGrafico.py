import pandas as pd
import matplotlib.pyplot as plt

# Lendo os dados do arquivo CSV
df = pd.read_csv("resultadosBipartido.csv", names=["vertices", "tempo_execucao_ms", "consumo_memoria_kb"])

# Gráfico de Tempo de Execução
plt.figure(figsize=(10, 5))
plt.plot(df["vertices"], df["tempo_execucao_ms"], marker="o", linestyle="-", label="Tempo de Execução (ms)")
plt.xlabel("Número de Vértices")
plt.ylabel("Tempo de Execução (ms)")
plt.title("Tempo de Execução em Função do Número de Vértices")
plt.legend()
plt.grid()
plt.show()

# Gráfico de Consumo de Memória
plt.figure(figsize=(10, 5))
plt.plot(df["vertices"], df["consumo_memoria_kb"], marker="o", color="red", linestyle="-", label="Consumo de Memória (KB)")
plt.xlabel("Número de Vértices")
plt.ylabel("Consumo de Memória (KB)")
plt.title("Consumo de Memória em Função do Número de Vértices")
plt.legend()
plt.grid()
plt.show()