import geradores
import mysql.connector

# Este script simula a ação do App interagindo com a base de dados, um processo em duas partes:
# O App insere texto na base de dados SQL
# O script "main.py", que continuamente busca textos na base de dados, converte o texto para um arquivo .mp3.
# Por fim, o script adiciona o mp3 à base de dados em formato BLOB.
# Por fim, "simulador.py" baixa o arquivo mp3 e remove-o da base de dados, simulando a ação do App.

Info_De_Conexao = geradores.Info_De_Conexao
conexao = mysql.connector.connect(host=Info_De_Conexao["host"], user=Info_De_Conexao["user"],
                                  password=Info_De_Conexao["password"], database=Info_De_Conexao["database"])
cursor = conexao.cursor()

# Limpando o campo de audio e definindo o  texto
cursor.execute("UPDATE livros SET AUDIO = NULL, link = \'{}\' WHERE id = 10".format(input("Digite o texto gerador:\n")))
conexao.commit()

# Aguardando o mp3 ser gerado

mp3 = None
while mp3 is None:
    conexao = mysql.connector.connect(host=Info_De_Conexao["host"], user=Info_De_Conexao["user"],
                                      password=Info_De_Conexao["password"], database=Info_De_Conexao["database"])
    cursor = conexao.cursor()
    cursor.execute("SELECT AUDIO FROM livros WHERE id = 10")
    mp3 = cursor.fetchone()[0]
    cursor.fetchall()   # É necessário esvaziar o buffer antes de executar novos comandos

# Baixando o mp3 para arquivo local
geradores.binario_para_arquivo(mp3, "Exemplos\\realtimeTTS\\output_de_mp3.mp3")

# Esvaziando o campo de AUDIO
cursor.execute("UPDATE livros SET AUDIO = NULL WHERE id = 10")
conexao.commit()

input("Pressione enter para finalizar o script.\n")
